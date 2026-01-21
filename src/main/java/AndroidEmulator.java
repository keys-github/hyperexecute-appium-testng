import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.MobileBy;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class AndroidEmulator {
    String username = System.getenv("LT_USERNAME") == null ? "LT_USERNAME" //Enter the Username here
            : System.getenv("LT_USERNAME");
    String accessKey = System.getenv("LT_ACCESS_KEY") == null ? "LT_ACCESS_KEY"  //Enter the Access key here
            : System.getenv("LT_ACCESS_KEY");
    public static RemoteWebDriver driver = null;
    public String gridURL = "@mobile-hub.lambdatest.com/wd/hub";
    public String status = "passed";
    @BeforeMethod
    @Parameters(value = { "deviceName", "platformVersion", "os" })
    public void setUp(String deviceName, String platformVersion, String os) throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("build", "HYP JUNIT Native App automation");
        capabilities.setCapability("name", "HYP Java JUnit Android Test");
        capabilities.setCapability("platformName", os);
        capabilities.setCapability("deviceName", deviceName); //Enter the name of the device here
        capabilities.setCapability("isRealMobile", false);
        capabilities.setCapability("platformVersion",platformVersion);
        capabilities.setCapability("app","lt://proverbial-android"); //Enter the App ID here
        capabilities.setCapability("deviceOrientation", "PORTRAIT");
        capabilities.setCapability("console",true);
        capabilities.setCapability("network",false);
        capabilities.setCapability("visual",true);
        capabilities.setCapability("autoGrantPermissions", true);
        capabilities.setCapability("appiumVersion", "1.21.0");
        capabilities.setCapability("devicelog", true);
        try
        {
            driver = new RemoteWebDriver(new URL("http://" + username + ":" + accessKey + gridURL), capabilities);
        }
        catch (MalformedURLException e)
        {
            System.out.println("Invalid grid URL");
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testSimple() throws Exception
    {
        try
        {
            WebDriverWait wait = new WebDriverWait(driver, 30);
            wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.id("color"))).click();

            wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.id("geoLocation"))).click();
            Thread.sleep(5000);
            driver.navigate().back();

            wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.id("Text"))).click();

            wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.id("notification"))).click();

            wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.id("toast"))).click();

            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("webview"))).click();
            Thread.sleep(10000);

            wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.id("url"))).sendKeys("https://www.lambdatest.com/");

            wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.id("find"))).click();
            Thread.sleep(5000);
            driver.navigate().back();

            status="passed"; 
        }
            catch (Exception e)
             {
                System.out.println(e.getMessage());
                status="failed";
             }
    }
    @AfterMethod
    public void tearDown() throws Exception
    {
        if (driver != null)
        {
            driver.executeScript("lambda-status=" + status);
            driver.quit();
        }
    }
}