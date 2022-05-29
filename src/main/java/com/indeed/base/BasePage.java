package com.indeed.base;

import com.indeed.utils.WaitForHelper;
import com.indeed.utils.LogUtil;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected WaitForHelper waitForHelper;

    private final By popoverCloseButton = By.className("popover-x-button-close");

    public BasePage(WebDriver driver){
        this.driver = driver;
        waitForHelper = new WaitForHelper(this.driver);
    }

    public void goToUrl(String url){
        driver.get(url);
    }

    public void waitForElementToBeClickable(By elementLocation){
        waitForHelper.clickabilityOfElement(elementLocation);
    }

    public void waitForElementToBeVisible(By elementLocation){
        waitForHelper.visibilityOfElement(elementLocation);
    }

    public void waitForAlertToBePresent(){
        waitForHelper.presenceOfAlert();
    }

    public void waitForLoading(){
        new WaitForHelper(driver).implicitWait();
    }

    public void waitForPageReadyState(){
        JavascriptExecutor j = (JavascriptExecutor)driver;
        j.executeScript("return document.readyState").toString().equals("complete");
    }

    public void click(By elementLocation) {
        driver.findElement(elementLocation).click();
    }

    public void enterText(By elementLocation, String text) {
        driver.findElement(elementLocation).sendKeys(Keys.CONTROL + "a");
        driver.findElement(elementLocation).sendKeys(Keys.DELETE);
        driver.findElement(elementLocation).clear();
        driver.findElement(elementLocation).sendKeys(text);
    }

    public String readText(By elementLocation) {
        return driver.findElement(elementLocation).getText();
    }

    public void moveToElement(By elementLocation) {
        new Actions(driver).moveToElement(driver.findElement(elementLocation)).build().perform();
    }

    public void exitPopover() {
        try {
            this.waitForElementToBeVisible(popoverCloseButton);
            this.click(popoverCloseButton);
            LogUtil.info("Closed out of modal");
        } catch (NoSuchElementException | TimeoutException e) {
            LogUtil.info("No modal found");
        }
    }

    public boolean isAlertDisplayed() {
        boolean foundAlert;
        try {
            this.waitForAlertToBePresent();
            foundAlert = true;
        } catch (TimeoutException e) {
            foundAlert = false;
        }
        return foundAlert;
    }
}
