package com.ixigo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class ResultsPage {


    @FindBy(how = How.XPATH, using = "//div[@class='stops']//div[1]//span[1]//span[1]")
    private WebElement nonStop;

    @FindBy(how = How.XPATH, using = "//div[@class='more-fltrs u-link']")
    private WebElement filterButton;

    @FindBy(how = How.XPATH, using = "//div[@class='slider-cntr lower']//div[@class='noUi-handle noUi-handle-lower']")
    private WebElement  priceSlider;

    @FindBy(how = How.XPATH, using = "//div[@class='apply-fltrs']//button[@class='c-btn u-link enabled']")
    private WebElement applyButton;

    @FindBy(how = How.XPATH, using = "//div[@class='slider-cntr lower']//div[@class='noUi-tooltip']")
    private WebElement sliderPriceTooltip;

    @FindBy(how = How.XPATH, using = "//div[@class='u-ib']//span[2]")
    private WebElement minPrice;

    @FindBy(how = How.XPATH, using = "//div[@class='u-ib u-rfloat']//span[2]")
    private WebElement maxPrice;

    private WebDriver driver;
    private final WebDriverWait wait;

    public ResultsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, 5);
    }

    public void setNonStop(){
        nonStop.click();
    }

    public void setPriceSlider(int price){
        filterButton.click();
        if(price > 100 && !String.valueOf(price).endsWith("000")){
            price = price + (100 - Integer.parseInt(String.valueOf(price).substring(String.valueOf(price).length()-3)));
        }

        try {
            Thread.sleep(1000);
            int maxPrice = Integer.parseInt(this.maxPrice.getText());
            int minPrice = Integer.parseInt(this.minPrice.getText());
            int move = 5;

            Actions actions = new Actions(driver);

            if(minPrice > price || maxPrice-price <= 100)
                return;
            do{
                if(maxPrice - price >= 1000){
                    move = -50;
                }else{
                    move = -5;
                }
                actions.dragAndDropBy(priceSlider, move, 0).build().perform();
                maxPrice = sliderPriceTooltip.getText().equals("") ?  maxPrice : Integer.parseInt(sliderPriceTooltip.getText());
            }while(price <= maxPrice && maxPrice-price != 100);

            while(Integer.parseInt(sliderPriceTooltip.getText()) <= price-100){
                actions.dragAndDropBy(priceSlider, 5, 0).build().perform();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    public void applyFilter(){
        applyButton.click();
    }

    public void printDepartureFlightDetails(int maxFare){
        boolean flag = true;

        String airLineXpath = "//div[contains(@class,'result-col outr')]//div//div[1]//div[3]//div[4]//div[1]";
        String deptTimeXpath = "//div[contains(@class,'result-col outr')]//div//div[1]//div[3]//div[3]";
        String fareXpath = "//div[contains(@class,'result-col outr')]//div//div[1]//div[5]//div[1]//div[1]//span[2]";

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='book-cta']//div[@class='u-ripple']")));
        List<WebElement> airLineList = driver.findElements(By.xpath(airLineXpath));
        List<WebElement> deptTimeList = driver.findElements(By.xpath(deptTimeXpath));
        List<WebElement> fareList = driver.findElements(By.xpath(fareXpath));

        String trip = getReturnTrip().replaceAll("[ ]+", " -> ");
        System.out.println("Departure flight details from "+trip);
        for(int i=0; i<fareList.size(); i++){
            if(Integer.parseInt(fareList.get(i).getText()) > maxFare)
                continue;
            System.out.println("AirLine :" +airLineList.get(i).getText().replace("&nbsp;", "")
                    + "; Time : "+ deptTimeList.get(i).getText()
                    + "; Rs:"+ fareList.get(i).getText());
            flag = false;
        }

        if( flag){

            System.out.println("No Departure flight from "+ trip +" available at  fare Rs."+maxFare);
        }

    }

    public String getReturnTrip(){
        return driver.findElement(By.xpath("//*[@id=\"content\"]/div/div[4]/div[1]/div/div[3]/div[1]/div[1]/span[1]"))
                .getText().replaceAll("[ ]+", " ");
    }

}
