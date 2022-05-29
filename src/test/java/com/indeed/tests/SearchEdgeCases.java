package com.indeed.tests;

import com.indeed.base.TestBase;
import com.indeed.pages.FrontPage;
import com.indeed.pages.ResultsPage;
import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class SearchEdgeCases extends TestBase {
    FrontPage frontPage;
    ResultsPage resultsPage;

    final int pageSize = 15;
    final String locationSearch = "63130";
    final String locationTabTitle = "St. Louis, MO 63130";
    final String jobSearch = "QA";
    final String specialChar = "!@#$%^&*()_+=-{}\\[]|;':\"<>?,./";
    final String emojis= "\uD83D\uDE3A\uD83D\uDE3A\uD83D\uDE3A\uD83D\uDE3A";
    final String searchLarge = "Aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                                  "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                                  "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                                  "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                                  "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                                  "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                                  "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                                  "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                                  "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                                  "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                                  "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                                  "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                                  "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                                  "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                                  "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                                  "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                                  "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                                  "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                                  "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                                  "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";

    final String locationTabTitleEnding = " | Indeed.com";
    final String locationTabTitleGenericEnding = " Jobs, Employment | Indeed.com";
    final String locationTabTitleBeginning = " Jobs, Employment in ";

    @DataProvider(name = "search-data-provider")
    public Object[][] searchDataProvider() {
        return new Object[][]{
                {"Both fields empty", "", "", 0, "Job Search | Indeed"},
                {"Blank job field", "", locationSearch, pageSize, locationTabTitleBeginning.replaceFirst("^\\s*", "") + locationTabTitle + locationTabTitleEnding},
                {"Blank location field", jobSearch, "", pageSize, jobSearch + locationTabTitleGenericEnding},
                {"Special characters job field", specialChar, locationSearch, 0, specialChar + locationTabTitleBeginning + locationTabTitle + locationTabTitleEnding},
                {"Special characters location field", jobSearch, specialChar, 0, jobSearch + locationTabTitleGenericEnding},
                {"Emojis job field",emojis, locationSearch, pageSize, emojis + locationTabTitleBeginning + locationTabTitle + locationTabTitleEnding},
                {"Emojis location field", jobSearch, emojis, 0, jobSearch + locationTabTitleGenericEnding},
                {"Absurdly long job field", searchLarge, locationSearch, 0, searchLarge + locationTabTitleBeginning + locationTabTitle + locationTabTitleEnding},
                {"Absurdly long location field", jobSearch, searchLarge, 0, jobSearch + locationTabTitleGenericEnding}};
    }

    @BeforeClass
    public void setup(){
        this.frontPage = new FrontPage(driver);
        this.frontPage.waitForPageReadyState();
        this.resultsPage = this.frontPage.afterSearch();
    }

    @Description("Verify all edge cases load the proper results page")
    @Test (dataProvider = "search-data-provider")
    public void searchEdgeCases(String test, String job, String location, int jobResults, String title){
        this.frontPage.searchForJob(job, location);
        Assert.assertEquals(resultsPage.getTabTitle(),title, "Tab title returned an unexpected value for test: " + test + " ,search What: " + job + " Where:" + location);
        Assert.assertEquals(resultsPage.countJobTitles(), jobResults, "Results page returned an unexpected number of results for test: " + test + " ,search What: " + job + " Where: " + location);
        Assert.assertTrue(resultsPage.getPageHeader().contains(job) || resultsPage.getPageHeader().contains("could not be found"), "Results page had an unexpected header for search What: " + job + " Where: " + location);
    }
}
