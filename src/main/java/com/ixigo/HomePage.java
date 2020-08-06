package com.ixigo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage{

    /**
     * from - source location
     * to   - destincation location
     * departureDate -  date of the departure
     * returnDate - date of  return.
     * travellers - number of travellers.
     */
    @FindBy(how = How.XPATH, using = "//div[text()='From']/following-sibling::input[1]")
    private WebElement from;
    @FindBy(how = How.XPATH, using = "//div[text()='To']/following-sibling::input[1]")
    private WebElement to;
    @FindBy(how = How.XPATH, using = "//div[text()='Departure']/following-sibling::input[1]")
    private WebElement departureDate;
    @FindBy(how = How.XPATH, using = "//div[text()='Return']/following-sibling::input[1]")
    private WebElement returnDate;
    @FindBy(how = How.XPATH, using = "//*[@id=\"content\"]/div/div[1]/div[6]/div/div/div[5]/div/div[1]/input")
    private WebElement travellers;
    @FindBy(how = How.XPATH, using =  "//button[contains(text(),'Search')]")
    private WebElement search;

    private WebDriver driver;
    private final WebDriverWait wait;

    public HomePage(WebDriver driver){
        this.driver = driver;
        wait = new WebDriverWait(driver, 5);
        PageFactory.initElements(driver, this);
    }


    public void setFrom(String from){
        this.from.click();
        this.from.sendKeys(from);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(),'"+from+", India')]")));
        driver.findElement(By.xpath("//div[contains(text(),'"+from+", India')]")).click();
    }

    public void setTo(String to){
        this.to.click();
        this.to.sendKeys(to);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(),'"+to+", India')]")));
        driver.findElement(By.xpath("//div[contains(text(),'"+to+", India')]")).click();
    }

    public void setDepartureDate(String date){
        String dates[] = date.split(" ");
        selectDate(departureDate, date);
    }

    public void setReturnDate(String date){
        String dates[] = date.split(" ");
        selectDate(returnDate, date);
    }

    public void setTravellers(String travellers){
        this.travellers.click();
        driver.findElement(By.xpath(("//div[@class='passanger-class-input u-pos-rel']//div[1]//div[2]//span["+travellers+"]"))).click();
    }
    public void clickOnSearch(){
        search.click();
    }

    private void selectDate(WebElement element, String date){
        String dates[] = date.split(" ");
        String dd = dates[0];
        String mon = dates[1];
        String year = dates[2];
        String monthXpath = "//div[contains(@class, 'rd-container') and contains(@style, 'display: inline')]//button/following-sibling::div[contains(text(),'"+mon+"')]";

        element.click();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while(driver.findElements(By.xpath(monthXpath)).size() == 0 ||  !driver.findElement(By.xpath(monthXpath)).getText().endsWith(year)){
            if(driver.findElement(By.xpath("//button[@class='ixi-icon-arrow rd-next']")).isDisplayed()){
                System.out.println("Can't select date due to the disabled.");
            }
//            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@style, 'display: inline-block')]//button[@class='ixi-icon-arrow rd-next']")));
            driver.findElement(By.xpath("//div[contains(@style, 'display: inline-block')]//button[@class='ixi-icon-arrow rd-next']")).click();
        }
        driver.findElement(
                By.xpath(monthXpath +
                        "/following-sibling::table/child::tbody/child::tr/child::td/child::div[text()='"+dd+"']"))
                .click();
    }


}
