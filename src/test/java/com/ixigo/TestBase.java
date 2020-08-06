package com.ixigo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class TestBase {

    public static WebDriver driver = null;
    protected WebDriverWait wait;
    @BeforeClass
    public void init() throws IOException{
        System.setProperty("webdriver.chrome.driver",
                System.getProperty("user.dir")+"\\src\\test\\java\\drivers\\chromedriver.exe");
        driver  = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.ixigo.com/");
//        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 30);;
    }

    @AfterClass
    public void tearDownTest(){
        driver.quit();
    }

}
