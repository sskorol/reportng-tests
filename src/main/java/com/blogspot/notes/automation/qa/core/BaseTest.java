package com.blogspot.notes.automation.qa.core;

import com.blogspot.notes.automation.qa.utils.BaseHTMLReporter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

/**
 * Created by Serhii Kuts
 */
public class BaseTest {
    private WebDriver driver;

    @BeforeMethod
    public void setUp(final ITestContext context) {
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        context.setAttribute(BaseHTMLReporter.DRIVER_ATTRIBUTE, driver);
        driver.get("http://qa-automation-notes.blogspot.com/");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
