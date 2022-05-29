package com.indeed.tests;

import com.indeed.base.TestBase;
import com.indeed.pages.FrontPage;
import com.indeed.pages.ResultsPage;
import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class SearchSmoketest extends TestBase {
    FrontPage frontPage;
    ResultsPage resultsPage;

    final int pageSize = 15;
    final String jobSearch = "QA";

    final String locationSearchZip = "63130";
    final String locationSearchCityState = "Saint Louis, Missouri";
    final String locationSearchCityStateZip = "Saint Louis, Missouri, 63130";
    final String locationSearchCityStateAbbreviated = "St. Louis, MO";
    final String locationSearchState = "Missouri";
    final String locationSearchCountryAbbreviated = "USA";
    final String locationSearchCountryPartial = "United States";
    final String locationSearchCountryFull = "United States of America";

    final String locationTabTitleFull = "St. Louis, MO 63130";
    final String locationTabTitleNoZip = "St. Louis, MO";
    final String locationTabTitleEnding = " | Indeed.com";
    final String locationTabTitleBeginning = " Jobs, Employment in ";

    @DataProvider(name = "search-data-provider")
    public Object[][] searchDataProvider() {
        return new Object[][]{
                {locationSearchZip, locationTabTitleFull},
                {locationSearchCityState, locationTabTitleNoZip},
                {locationSearchCityStateZip, locationTabTitleFull},
                {locationSearchCityStateAbbreviated, locationTabTitleNoZip},
                {locationSearchState, locationSearchState},
                {locationSearchCountryAbbreviated, locationSearchCountryPartial},
                {locationSearchCountryPartial, locationSearchCountryPartial},
                {locationSearchCountryFull, locationSearchCountryPartial}};
    }

    @BeforeClass
    public void setup(){
        this.frontPage = new FrontPage(driver);
        this.frontPage.waitForPageReadyState();
        this.resultsPage = this.frontPage.afterSearch();
    }

    @Description("Verify all common happy path searches load a a page titled correctly with a full page of results")
    @Test (dataProvider = "search-data-provider")
    public void searchSmoketests(String location, String title){
        this.frontPage.searchForJob(jobSearch, location);
        Assert.assertEquals(resultsPage.getTabTitle(),jobSearch + locationTabTitleBeginning + title + locationTabTitleEnding, "Tab title returned an unexpected value for search What: " + jobSearch + " Where: " + location);
        Assert.assertEquals(resultsPage.countJobTitles(), pageSize, "Results page returned an unexpected number of results for search What: " + jobSearch + " Where: " + location);
        Assert.assertEquals(resultsPage.getPageHeader(), jobSearch + " jobs in " + title, "Results page had an unexpected header for search What: " + jobSearch + " Where: " + location);
    }
}
