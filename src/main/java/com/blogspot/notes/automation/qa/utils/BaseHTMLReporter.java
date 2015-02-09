package com.blogspot.notes.automation.qa.utils;

import org.apache.commons.io.FileUtils;
import org.apache.velocity.VelocityContext;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.*;
import org.uncommons.reportng.HTMLReporter;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Author: Sergey Kuts
 * Date: 9/17/13
 * Time: 6:49 PM
 */
public class BaseHTMLReporter extends HTMLReporter implements ITestListener {
    public static final String DRIVER_ATTRIBUTE = "driver";
    private static final String UTILS_KEY = "utils";

    private static final ReportUtils REPORT_UTILS = new ReportUtils();

    protected VelocityContext createContext() {
        final VelocityContext context = super.createContext();
        context.put(UTILS_KEY, REPORT_UTILS);
        return context;
    }

    private void createScreenshot(final ITestResult result, final WebDriver driver) {
        final DateFormat timeFormat = new SimpleDateFormat("MM.dd.yyyy HH-mm-ss");
        final String fileName = result.getMethod().getMethodName() + "_" + timeFormat.format(new Date());

        try {
            File scrFile;

            if (driver.getClass().equals(RemoteWebDriver.class)) {
                scrFile = ((TakesScreenshot) new Augmenter().augment(driver))
                        .getScreenshotAs(OutputType.FILE);
            } else {
                scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            }

            String outputDir = result.getTestContext().getOutputDirectory();
            outputDir = outputDir.substring(0, outputDir.lastIndexOf('\\')) + "\\html";

            final File saved = new File(outputDir, fileName + ".png");
            FileUtils.copyFile(scrFile, saved);

            result.setAttribute("screenshot", saved.getName());
        } catch (IOException e) {
            result.setAttribute("reportGeneratingException", e);
        }

        result.setAttribute("screenshotURL", driver.getCurrentUrl());
        result.removeAttribute(DRIVER_ATTRIBUTE);
    }

    @Override
    public void onTestFailure(final ITestResult result) {
        final WebDriver driver = (WebDriver) result.getTestContext().getAttribute(DRIVER_ATTRIBUTE);

        if (driver != null) {
            createScreenshot(result, driver);
            driver.quit();
        }
    }

    @Override
    public void onTestSuccess(final ITestResult result) {
        final WebDriver driver = (WebDriver) result.getTestContext().getAttribute(DRIVER_ATTRIBUTE);

        if (driver != null) {
            createScreenshot(result, driver);
            driver.quit();
        }
    }

    @Override
    public void onTestStart(final ITestResult result) {
    }

    @Override
    public void onTestSkipped(final ITestResult result) {
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(final ITestResult result) {
    }

    @Override
    public void onStart(final ITestContext context) {
    }

    @Override
    public void onFinish(final ITestContext context) {
    }
}
