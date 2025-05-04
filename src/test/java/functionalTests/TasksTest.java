package functionalTests;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class TasksTest {
	
	public WebDriver acessarAplicacao() throws MalformedURLException {
//		WebDriver driver = new ChromeDriver();
		ChromeOptions cap = new ChromeOptions();
		WebDriver driver = new RemoteWebDriver(new URL("http://192.168.1.6:4444/wd/hub"), cap);
		driver.navigate().to("http://192.168.1.6:8080/tasks");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		return driver;
	}
	
	public static String formatDateToISO(String dateBr) {
	    return LocalDate.parse(dateBr, DateTimeFormatter.ofPattern("dd/MM/yyyy"))
	                    .format(DateTimeFormatter.ISO_LOCAL_DATE);
	}
	
	@Test
	public void deveSalvarTarefaComSucesso() throws MalformedURLException {
		WebDriver driver = acessarAplicacao();
		try {
		driver.findElement(By.id("addTodo")).click();
		driver.findElement(By.id("task")).sendKeys("Teste via Selenium");
		driver.findElement(By.id("dueDate")).click();
		driver.findElement(By.id("dueDate")).sendKeys("02/08/2030");
		driver.findElement(By.id("saveButton")).click();
		String message = driver.findElement(By.id("message")).getText();
		Assert.assertEquals("Task saved successfully!", message);
		} finally {
		driver.quit();
		}
	}
	
	@Test
	public void deveApresentarMensagemDeErroInserindoDataPassada() throws MalformedURLException {
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
	public void deveApresentarMensagemDeErroAoSalvarSemDescricao() throws MalformedURLException {
		WebDriver driver = acessarAplicacao();
		try {
		driver.findElement(By.id("addTodo")).click();
		driver.findElement(By.id("dueDate")).click();
		driver.findElement(By.id("dueDate")).sendKeys("02/08/2030");
		driver.findElement(By.id("saveButton")).click();
		String message = driver.findElement(By.id("message")).getText();
		Assert.assertEquals("Fill the task description", message);
		} finally {
		driver.quit();
		}
	}
	
	@Test
	public void deveExcluirTarefaComSucesso() throws MalformedURLException {
	    WebDriver driver = acessarAplicacao();
	    try {
	        driver.findElement(By.id("addTodo")).click();
	        driver.findElement(By.id("task")).sendKeys("Tarefa para excluir");
			driver.findElement(By.id("dueDate")).click();
			driver.findElement(By.id("dueDate")).sendKeys("02/08/2030");
			driver.findElement(By.id("saveButton")).click();
	        String message = driver.findElement(By.id("message")).getText();
	        Assert.assertEquals("Task saved successfully!", message);
	        driver.findElement(By.xpath("//table/tbody/tr[1]/td[3]/a")).click();
	        String deleteMessage = driver.findElement(By.id("message")).getText();
	        Assert.assertEquals("Task deleted successfully!", deleteMessage);

	    } finally {
	        driver.quit();
	    }
	}
	
	@Test
	public void deveApresentarMensagemDeErroAoSalvarSemData() throws MalformedURLException {
		WebDriver driver = acessarAplicacao();
		try {
		driver.findElement(By.id("addTodo")).click();
		driver.findElement(By.id("task")).sendKeys("Tarefa para excluir");
		driver.findElement(By.id("saveButton")).click();
		String message = driver.findElement(By.id("message")).getText();
		Assert.assertEquals("Fill the due date", message);
		} finally {
		driver.quit();
		}
	}
}
