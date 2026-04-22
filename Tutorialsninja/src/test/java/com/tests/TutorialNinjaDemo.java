package com.tests;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.utilities.DPExcel;

public class TutorialNinjaDemo {
	
	public static ThreadLocal<WebDriver> driver=new ThreadLocal<>();

	@BeforeMethod
	    public void setup() {
		     ChromeOptions options=new ChromeOptions();
		     options.addArguments("--start-maximized");
		     driver.set(new ChromeDriver(options));
		     driver.get().get("https://tutorialsninja.com/demo/");
	    }
	    
	 @Test(dataProvider = "validData", dataProviderClass =DPExcel.class)
	 public void validLogin(String email,String password) {
		        WebDriverWait wait = new WebDriverWait(driver.get(), Duration.ofSeconds(10));
		        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("My Account"))).click();
		        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Login"))).click();
		        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("email"))).sendKeys(email);
		        driver.get().findElement(By.name("password")).sendKeys(password);
		        driver.get().findElement(By.xpath("//input[@value=\"Login\"]")).click();
		        WebElement accountText = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[text()='My Account']")));
		        Assert.assertTrue(accountText.isDisplayed());
		        System.out.println("VALID LOGIN PASSED for: " + email);
		    }
	 @Test(dataProvider = "invalidData", dataProviderClass = DPExcel.class)
	 public void invalidLogin(String email, String password) {

	     WebDriverWait wait = new WebDriverWait(driver.get(), Duration.ofSeconds(10));
	     wait.until(ExpectedConditions.elementToBeClickable(By.linkText("My Account"))).click();
	     wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Login"))).click();
	     wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("email"))).sendKeys(email);
	     driver.get().findElement(By.name("password")).sendKeys(password);
	     driver.get().findElement(By.xpath("//input[@value='Login']")).click();
	     WebElement warning = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".alert-danger")));
	     Assert.assertTrue(warning.getText().contains("No match"));
	     System.out.println("INVALID LOGIN PASSED for: " + email);
	 }
	 
	 @Test
	 public void searchMacProducts() {

	     WebDriverWait wait = new WebDriverWait(driver.get(), Duration.ofSeconds(10));
	     String searchKey = "mac";
	     wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("search"))).sendKeys(searchKey);
	     driver.get().findElement(By.cssSelector("button.btn-default")).click();
	     WebElement resultHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[contains(text(),'Search')]")));
	     Assert.assertTrue(resultHeader.isDisplayed());
	     List<WebElement> products =driver.get().findElements(By.cssSelector(".caption h4 a"));
	     Assert.assertTrue(products.size() > 0, "No products found!");
	     for (WebElement product : products) {
	         String productName = product.getText().toLowerCase();
	         System.out.println(productName);
	         Assert.assertTrue(productName.contains(searchKey),"Product does not match search: " + productName);
	     }
	     System.out.println("SEARCH VALIDATION PASSED for keyword: " + searchKey);
	 }
	 
	 @AfterMethod
	    public void tearDown() {
	        driver.get().quit();  
	        driver.remove();      
	}
}
		 
	
	
	
	
  