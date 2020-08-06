package com.ixigo;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class HomePageTest extends TestBase{

    @Test
    @Parameters({"from", "to", "deptDate", "returnDate", "travellers"})
    public void verfiyHomePage(String from, String to, String deptDate, String returnDate, String travellers){
            HomePage homePage = new HomePage(driver);
            homePage.setFrom(from);
            homePage.setTo(to);
            homePage.setDepartureDate(deptDate);
            homePage.setReturnDate(returnDate);
            homePage.setTravellers(travellers);
            homePage.clickOnSearch();
    }
}
