package tasksProd;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

public class HealthCheckIT {
	
	@Test
	public void healthCheck() throws MalformedURLException {
		ChromeOptions cap = new ChromeOptions();
		WebDriver driver = new RemoteWebDriver(new URL("http://192.168.1.6:4444/wd/hub"), cap);
		try {
		driver.navigate().to("http://192.168.1.6:9999/tasks");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		String version = driver.findElement(By.id("version")).getText();
		Assert.assertTrue(version.startsWith("build"));
		} finally {
		driver.quit();
			}
		}

}
