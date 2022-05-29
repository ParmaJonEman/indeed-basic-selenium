package com.indeed.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ResultsPage extends FrontPage {

    private WebDriver driver;

    public ResultsPage(WebDriver driver){
        super(driver);
        this.driver = driver;
    }

    private final By jobTitle = By.className("jcs-JobTitle");
    private final By pageHeader = By.tagName("h1");

    public int countJobTitles(){
        if(driver.findElements(jobTitle).size() > 0) {
            this.waitForElementToBeClickable(jobTitle);
        }
        return driver.findElements(jobTitle).size();
    }

    public String getPageHeader(){
        return driver.findElement(pageHeader).getText();
    }

    public String getTabTitle(){
        return driver.getTitle();
    }
}
