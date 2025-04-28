package functionalTests;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class TasksTest {
	
	public WebDriver acessarAplicacao() {
		WebDriver driver = new ChromeDriver();
		driver.navigate().to("http://localhost:8080/tasks");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		return driver;
	}
	
	@Test
	public void deveSalvarTarefaComSucesso() {
		WebDriver driver = acessarAplicacao();
		try {
		driver.findElement(By.id("addTodo")).click();
		driver.findElement(By.id("task")).sendKeys("Teste via Selenium");
		driver.findElement(By.id("dueDate")).sendKeys("30/08/2030");
		driver.findElement(By.id("saveButton")).click();
		String message = driver.findElement(By.id("message")).getText();
		Assert.assertEquals("Success!", message);
		} finally {
		driver.quit();
		}
	}
	
	@Test
	public void deveApresentarMensagemDeErroInserindoDataPassada() {
		WebDriver driver = acessarAplicacao();
		try {
		driver.findElement(By.id("addTodo")).click();
		driver.findElement(By.id("task")).sendKeys("Teste via Selenium");
		driver.findElement(By.id("dueDate")).sendKeys("01/01/2000");
		driver.findElement(By.id("saveButton")).click();
		String message = driver.findElement(By.id("message")).getText();
		Assert.assertEquals("Due date must not be in past", message);
		} finally {
		driver.quit();
		}
	}
	
	@Test
	public void deveApresentarMensagemDeErroAoSalvarSemDescricao() {
		WebDriver driver = acessarAplicacao();
		try {
		driver.findElement(By.id("addTodo")).click();
		driver.findElement(By.id("task")).sendKeys("Teste via Selenium");
		driver.findElement(By.id("saveButton")).click();
		String message = driver.findElement(By.id("message")).getText();
		Assert.assertEquals("Fill the due date", message);
		} finally {
		driver.quit();
		}
	}
	
	@Test
	public void deveApresentarMensagemDeErroAoSalvarSemData() {
		WebDriver driver = acessarAplicacao();
		try {
		driver.findElement(By.id("addTodo")).click();
		driver.findElement(By.id("dueDate")).sendKeys("01/01/2000");
		driver.findElement(By.id("saveButton")).click();
		String message = driver.findElement(By.id("message")).getText();
		Assert.assertEquals("Fill the task description", message);
		} finally {
		driver.quit();
		}
	}
}
