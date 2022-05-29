# indeed-basic-selenium

## WARNING - RUNNING THIS AUTOMATION TOO OFTEN WILL LIKELY GET YOUR IP ADDRESS FLAGGED AS A BOT BY INDEED
## THIS WILL CAUSE CAPTCHAS TO SHOW UP AT INCREASING FREQUENCY


#### Installation
1. [Install Java SDK (at least v8)](https://www.wikihow.com/Install-the-Java-Software-Development-Kit)
2. [Install Maven](https://maven.apache.org/install.html)
3. Run `mvn clean install` in the project directory, using your favorite terminal
4. Enter "chrome" or "firefox" in the src/main/resources/config.properties file. (Default is firefox)
5. Then run mvn `clean test` in order to run the suites


#### Overview
This is a simple demonstration of Selenium, TestNG, and Allure. 

###### Test Suites
There are three test files: 
1. SearchSmoketest
    - Searches for QA jobs with various acceptable formats for the location, and then checks to make sure that 15 results show up, the page header is correct, and the tab title is correct.
3. SearchSecurity
    - Tests basic Cross Site Scripting and SQL Injection attacks, and verifies that no error messages pop up, and that the standard site heading displays. Note: it is not good practice to check for server response codes in your UI tests, so the best course of action is to check that the page displays correctly.
5. SearchEdgeCases
    - Tests blank fields, very long values, special character values, and emoji values, and verifies the number of results, the page header, and the tab title.

I put them all in one TestNG suite, because they all test the same general functionality, just in different ways. The whole TestNG suite takes about 2 minutes to run, and includes 23 tests.

###### TestNG
My TestNG setup is pretty standard. I used a data provider with each of my suites, but I could have used a data class or a file feeder of some sort (Excel and flat files are great choices).

###### Allure
Allure is a fantastic tool that I like to use to produce reports. I set it up in the basic way that their documentation recommends, and then added the `screenShotIfFail()` method in the TestBase. This attaches a screenshot at the moment of failure, so that the failed tests are easier to troubleshoot.

Allure results are automatically generated with this repository, but if you'd like to see the report, just run `allure generate ./allure-results --clean` in the project directory, and a report will be generated. You won't be able to simple open the index.html it produces, though, you'll have to host it somewhere. These reports are generally used in conjunction with a CI tool like Jenkins.

##### LogUtil

This is a basic implementation of a log4j logger. I used it in a couple of cases where I wanted to inform the user of an action that was happening (for instance, handling the subscription popover). I added methods to add more readable logging around the test runs, but did not implement them before Indeed started (accurately) flagging my IP as a bot, but they'd be very simple to implement in the TestBase.

##### WaitForHelper

This isn't strictly necessary when you're creating a Selenium project, but I like to create a wait util, to keep things simple. It's basically syntactic sugar for the standard types of waits that Selenium offers.


#### Reflections
I stopped adding to this project when my IP started getting flagged (which isn't impossible to overcome with cookie loading, manual intervention, or image recognition, but I didn't want to push my luck), but if I were to make enhancements to this project, I would probably:
1. Test more search Strings. The [big list of naughty strings](https://github.com/minimaxir/big-list-of-naughty-strings) always gives me plenty of ideas of problematic search queries. I didn't even touch international characters, for instance.
2. Test international locations and locations with similar/identical names. I focused only on United States locations, specifically St Louis. Proper testing would include cities that exist in more than one state or country, international locations, and edge case locations with non-standard zip codes like military bases and embassies.
3. Come up with a data driven testing structure that works better for the project. As mentioned above, I used a quick and dirty data provider. I would rather have something more sharable and more easily extendable, but hadn't come up with the perfect solution by the time my IP was beginning to get flagged.
4. Query the REST API for the results that I expect to see when I execute searches. I was only testing the page header, tab title, and number of results. I could have compared against a REST call, or even better: implemented a fuzzy search checker that validates that each job listing is an appropriate result for the search term.
5. Used the LogUtil in BeforeMethod and AfterMethod in the TestBase, so that the output could be more readable.
6. Validate more page elements. As mentioned, I didn't do much in the way of validating that the common page elements appeared each search. I was mainly focused on whether or not the searches I was executing broke the site entirely. A more robust project would include more element validation in general.
7. Validate filter and recent search functionality. Even though this project was mainly focused on search functionality, the filtering and recent search functionality is tightly paired with general search functionality, and should probably be in some of the same smoketests. 
