#!/bin/bash

set -e

if [[ -z "$LT_USERNAME" ]]; then
  echo "ERROR: LT_USERNAME not set"
  exit 1
fi

if [[ -z "$LT_ACCESS_KEY" ]]; then
  echo "ERROR: LT_ACCESS_KEY not set"
  exit 1
fi

APP_FILE="$1"

if [[ -z "$APP_FILE" ]]; then
  echo "Usage: ./upload_app.sh path/to/app.zip"
  exit 1
fi

if [[ ! -f "$APP_FILE" ]]; then
  echo "File not found: $APP_FILE"
  exit 1
fi

echo "Uploading: $APP_FILE"

response=$(curl -s -u "$LT_USERNAME:$LT_ACCESS_KEY" \
  -X POST "https://manual-api.lambdatest.com/app/upload/virtualDevice" \
  -F "name=Your_App_Name" \
  -F "appFile=@${APP_FILE}")

echo ""
echo "Raw Response:"
echo "$response"
echo ""

APP_ID=$(echo "$response" | grep -o '"app_id":"[^"]*"' | cut -d '"' -f 4)

if [[ -z "$APP_ID" ]]; then
  echo "ERROR: Could not extract app_id"
  exit 1
fi

echo "Extracted APP_ID: $APP_ID"

export LT_APP_ID="$APP_ID"
echo "export LT_APP_ID=\"$APP_ID\"" >> ~/.zshrc

echo "Done."
