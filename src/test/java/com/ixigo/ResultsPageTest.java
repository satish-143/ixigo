package com.ixigo;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class ResultsPageTest extends TestBase{

    private HomePage homePage;

    @Test
    @Parameters({"from", "to", "deptDate", "returnDate", "travellers"})
    public void verfiyHomePage(String from, String to, String deptDate, String returnDate, String travellers){
        homePage = new HomePage(driver);
        String actualTitle = driver.getTitle();
        String expectedTitle = "ixigo - Flights, Train Reservation, Hotels, Air Tickets, Bus Booking";
        assertEquals(actualTitle, expectedTitle);

//      set the values in home page
        homePage.setFrom(from);
        homePage.setTo(to);
        homePage.setDepartureDate(deptDate);
        homePage.setReturnDate(returnDate);
        homePage.setTravellers(travellers);
        homePage.clickOnSearch();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='stops']//div[1]//span[1]//span[1]")));

    }

    @Test(dependsOnMethods = "verfiyHomePage")
    @Parameters({"maxDeptFare"})
    public void verifyResultsPage(int maxDeptFare){

//      Verify the results page after the filter applied
        ResultsPage resultsPage = new ResultsPage(driver);
        resultsPage.setNonStop();
        resultsPage.setPriceSlider(maxDeptFare);
        resultsPage.applyFilter();
        resultsPage.printDepartureFlightDetails(maxDeptFare);
        String expectedString = "Pune Hyderabad";
        assertEquals(resultsPage.getReturnTrip(), expectedString);

    }
}
