package com.indeed.base;

import com.indeed.utils.DriverManager;
import com.indeed.utils.LogUtil;
import com.indeed.utils.PropertyReader;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class TestBase extends DriverManager{

    public WebDriver driver;
    PropertyReader pr = new PropertyReader();

    public TestBase() {
        this.driver = super.getDriver();
        LogUtil.info("Getting WebDriver Settings");
    }

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        try {
            if (PropertyReader.readItem("browser").equals("firefox")) {
                LogUtil.info("Found firefox as Browser");
                System.setProperty("webdriver.gecko.driver", "src/main/resources/WebDriver/geckodriver.exe");
                driver = new FirefoxDriver();
                driver.manage().window().maximize();
                driver.get(PropertyReader.readItem("url"));
            }
            else if(PropertyReader.readItem("browser").equals("chrome")){
                LogUtil.info("Found chrome as Browser");
                System.setProperty("webdriver.chrome.driver", "src/main/resources/WebDriver/chromedriver.exe");
                driver = new ChromeDriver();
                driver.manage().window().maximize();
                driver.get(PropertyReader.readItem("url"));
            }
            else {
                try {
                    throw new Exception("Browser or Browser Driver is not supported yet.");
                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtil.error("Browser Launch Failure: ", e);
                }
            }
        } catch (Exception e) {
            LogUtil.error("Browser Launch Failure: ", e);
        }
    }

    @AfterMethod()
    protected void screenShotIfFail(ITestResult result) throws IOException {
        if (!result.isSuccess()) {
            takeScreenShot(result.getMethod().toString());
        }
    }

    @AfterClass(alwaysRun = true)
    public void teardown() {
        LogUtil.info("Closing Webdriver Windows");
        driver.quit();
    }

    public void takeScreenShot(String name){
        Allure.addAttachment(name, new ByteArrayInputStream(((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES)));
    }
}
