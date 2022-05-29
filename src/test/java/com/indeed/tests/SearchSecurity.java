package com.indeed.tests;

import com.indeed.base.TestBase;
import com.indeed.pages.FrontPage;
import com.indeed.pages.ResultsPage;
import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class SearchSecurity extends TestBase {
    FrontPage frontPage;
    ResultsPage resultsPage;

    final String XSSSearchTerm = "<script>alert(\"1\");</script>";
    final String SQLInjectionSearchTermSingle = "‘ or 1=1;–";
    final String SQLInjectionSearchTermDouble = "“ or 1=1;–";
    final String zipcode = "63130";
    final String jobSearch = "QA";

    @DataProvider(name = "search-data-provider")
    public Object[][] searchDataProvider() {
        return new Object[][]{
                {"Cross Site Scripting - Job Field", XSSSearchTerm, zipcode},
                {"Cross Site Scripting - Location Field", jobSearch, XSSSearchTerm},
                {"SQL Injection Single Quote - Job Field", SQLInjectionSearchTermSingle, zipcode},
                {"SQL Injection Double Quote - Job Field", SQLInjectionSearchTermDouble, zipcode},
                {"SQL Injection Single Quote - Location Field", jobSearch, SQLInjectionSearchTermSingle},
                {"SQL Injection Double Quote - Location Field", jobSearch, SQLInjectionSearchTermDouble}
        };
    }

    @BeforeClass
    public void setup(){
        this.frontPage = new FrontPage(driver);
        this.resultsPage = this.frontPage.afterSearch();
        this.frontPage.waitForPageReadyState();
    }

    @Description("Verify all common malicious searches do not cause alert messages or server error.")
    @Test (dataProvider = "search-data-provider")
    public void searchSecurityTests(String test, String job, String location){
        this.frontPage.searchForJob(job, location);
        Assert.assertFalse(resultsPage.isAlertDisplayed(), "Alert displayed for test: " + test);
        System.out.println(resultsPage.getPageHeader());
        Assert.assertTrue(resultsPage.getPageHeader().contains("did not match any jobs") || resultsPage.getPageHeader().contains("could not be found") || resultsPage.getPageHeader().contains(job.replace(" or", "") + " jobs in") );
    }
}
