package com.vcentry.testng;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class SuiteFileParameterization {
	WebDriver driver;
	WebDriverWait wait;

	@Test(priority = 0)
	@Parameters({ "browser", "uri" })
	public void launch(String browser, String url) {
		if (browser.trim().equalsIgnoreCase("Chrome")) {
			driver = new ChromeDriver();
		} else if (browser.trim().equalsIgnoreCase("Firefox")) {
			driver = new FirefoxDriver();
		}
		Dimension dim = new Dimension(1800, 1000);
		driver.manage().window().setSize(dim);
		wait = new WebDriverWait(driver, 10);
		driver.get(url);

	}

	@Test(priority = 1)
	@Parameters({ "keyWord" })
	public void inputNoiseWord(String noiseWord) {
		WebElement frame;
		frame = driver.findElement(By.className("demo-frame"));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frame));
		driver.findElement(By.id("tags")).sendKeys(noiseWord);
	}

	@Test(priority = 2)
	@Parameters({ "srchword" })
	public void chooseSuggestion(String srchWord) {
		wait.until(ExpectedConditions
				.presenceOfAllElementsLocatedBy(By.xpath("//li/div[contains(@class,'ui-menu-item')]")));
		List<WebElement> options = driver.findElements(By.xpath("//li/div[contains(@class,'ui-menu-item')]"));
		for (WebElement opt : options) {
			if (opt.getText().trim().equalsIgnoreCase(srchWord)) {
				opt.click();
				break;
			}
		}
	}

	@AfterTest
	public void tearDown() throws InterruptedException {
		Thread.sleep(2000);
		driver.quit();
	}
}
