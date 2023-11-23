package Tests;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;


import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
//import org.testng.annotations.Test;

public class LoginTest {
	WebDriver driver = null;
	String username = "standard_user";
	String password = "secret_sauce";
	String submit = "//input[@type='submit']";
	String addcartbutton = "//button[text()='Add to cart']";
	String shoppingcartbutton = "//div[@id='shopping_cart_container']//a[1]";
	String item = "//a[@href='#']//div[1]";
	String checkoutbutton = "//button[text()='Checkout']";
	String firstnamefield = "//input[@data-test='firstName']";
	String menubutton = "//button[text()='Open Menu']";
	String logoutbutton = "(//a[@href='#'])[2]";

	@BeforeTest
	public void setUptest() {

		driver = new ChromeDriver();
		driver.get("https://www.saucedemo.com/");
		driver.manage().window().maximize();

	}

	@Test
	public void ValidatedProductinCart() throws InterruptedException {

		driver.findElement(By.xpath("//input[@placeholder='Username']")).sendKeys(username);
		driver.findElement(By.xpath("//input[@placeholder='Password']")).sendKeys(password);
		driver.findElement(By.xpath(submit)).click();

		
		String itemsNeeded = "Sauce Labs Bike Light";

		int j = 0;

		List<WebElement> products = driver.findElements(By.xpath("//div[@class=\"inventory_item_name \"]"));
		System.out.println(products.size());

		for (int i = 0; i < products.size(); i++)

		{

			String name = products.get(i).getText().toString();
			System.out.println(name);
			List itemsNeededList = Arrays.asList(itemsNeeded);
			// System.out.println(itemsNeededList);

			if (itemsNeededList.contains(name)) {
				j++;
				driver.findElements(By.xpath(addcartbutton)).get(i).click();
				Thread.sleep(3000);
				if (j == itemsNeeded.length()) {
					break;
				}
			}
		}
		
		driver.findElement(By.xpath(shoppingcartbutton)).click();
		String Item = driver.findElement(By.xpath(item)).getText();
		System.out.println("item added is"+Item);
		System.out.println("item added is"+itemsNeeded);
		assertEquals(Item, itemsNeeded,"Item is added");
		
		driver.findElement(By.xpath(checkoutbutton)).click();
		 WebElement firstNameField = driver.findElement(By.xpath(firstnamefield));
	     assertTrue(firstNameField.isDisplayed(), "First Name field is not displayed on the checkout page");
		
		 WebElement FirstName = driver.findElement(By.xpath(firstnamefield));
		
		
	}
	
	@AfterTest
	public void LogOut () throws InterruptedException {
		
		driver.findElement(By.xpath(menubutton)).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath(logoutbutton)).click();
		driver.quit();
	}
}