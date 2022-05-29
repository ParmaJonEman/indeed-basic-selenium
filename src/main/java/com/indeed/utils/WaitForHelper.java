package com.indeed.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class WaitForHelper {

    private WebDriver driver;
    private WebDriverWait wait;
    public WaitForHelper(WebDriver driver) {
        this.wait = new WebDriverWait(driver, Duration.ofMillis(2000L));
        this.driver = driver;
    }

    public void implicitWait()
    {
        driver.manage().timeouts().implicitlyWait(Integer.parseInt(PropertyReader.readItem("elementLoadTimeout")),TimeUnit.MILLISECONDS);
    }

    public void clickabilityOfElement(final By elementIdentifier) {
        wait.until(ExpectedConditions.elementToBeClickable(elementIdentifier));
    }

    public void visibilityOfElement(final By elementIdentifier) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(elementIdentifier));
    }

    public void presenceOfAlert() {
        wait.until(ExpectedConditions.alertIsPresent());
    }
}