package FirstTest;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class FirstTest {

    WebDriver driver;
    String URL;

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "drv/chromedriver78.exe");

        driver = new ChromeDriver();
        URL = "https://www.sberbank.ru/ru/person";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @Test
    public void test() throws InterruptedException {

        Wait<WebDriver> driverWait = new WebDriverWait(driver, 5, 1000);

        driver.get(URL);
        driver.findElement(By.xpath("//header//div[@class='hd-ft-region__title']")).click();
        driver.findElement(By.xpath("(//INPUT[@class='kit-input__control'])[5]")).click();
        driver.findElement(By.xpath("(//INPUT[@class='kit-input__control'])[5]")).sendKeys("Нижегородская область");
        driver.findElement(By.xpath("//A[@class='kit-link kit-link_m hd-ft-region__city'][text()='Нижегородская область']")).click();
        driverWait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("(//SPAN[text()='Нижегородская область'])[1]"))));
        // Проверить, что на главной странице отображается выбранная область
        Assert.assertEquals("Нижегородская область", driver.findElement(By.xpath("(//SPAN[text()='Нижегородская область'])[1]")).getText());

        driverWait.until(ExpectedConditions.visibilityOf(driver.findElement(By.className("footer__social"))));

        // Сделать скролл до footer объекта на главной странице
        WebElement webElement = driver.findElement(By.className("footer__social"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", webElement);
        Thread.sleep(3000);

        // доступен ли элемент на странице
        Assert.assertTrue(driver.findElement(By.className("footer__social")).isEnabled());
        // является ли элемент видимым
        Assert.assertTrue(driver.findElement(By.className("footer__social")).isDisplayed());

        System.out.println("Тест прошел успешно");
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
