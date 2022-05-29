package com.indeed.pages;

import com.indeed.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class FrontPage extends BasePage {

    private WebDriver driver;

    public FrontPage(WebDriver driver){
        super(driver );
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(2));
    }

    private final By whatSearch = By.id("text-input-what");
    private final By whereSearch = By.id("text-input-where");
    private final By submitButton = By.className("yosegi-InlineWhatWhere-primaryButton");

    public void searchForJob(String what, String where){
        this.enterText(whatSearch, what);
        this.enterText(whereSearch, where);
        this.click(submitButton);
        this.exitPopover();
        this.waitForLoading();
    }

    public ResultsPage afterSearch(){
        return new ResultsPage(driver);
    }
}
