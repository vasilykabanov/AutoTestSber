package SecondTest;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Created by yasup on 13.11.2019.
 */

public class SecondTest {

    private WebDriver driver;
    private String URL;

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "drv/chromedriver78.exe");
        System.setProperty("webdriver.gecko.driver", "drv/geckodriver.exe");

        driver = new ChromeDriver();
        URL = "https://www.sberbank.ru/ru/person";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @Test
    public void secondTest() throws InterruptedException {

        Wait<WebDriver> driverWait = new WebDriverWait(driver, 5, 1000);

        driver.get(URL);
        driver.findElement(By.xpath("//SPAN[@class='lg-menu__text'][text()='Страхование']")).click();
        driver.findElement(By.xpath("(//A[@href='/ru/person/bank_inshure/insuranceprogram/life/travel'][text()='Страхование путешественников'][text()='Страхование путешественников'])[1]")).click();

        Assert.assertEquals("Страхование путешественников",
                driver.findElement(By.xpath("//h1[text()='Страхование путешественников']")).getText());

        driver.findElement(By.xpath("//SPAN[@class=''][text()='Оформить онлайн']")).click();
        driver.findElement(By.xpath("//IMG[@src='/portalserver/content/atom/contentRepository/content/person/travel/banner-zashita-traveler.jpg?id=f6c836e1-5c5c-4367-b0d0-bbfb96be9c53']")).click();

        // Учитывая, что в этой точке открыта только одна вкладка
        String oldTab = driver.getWindowHandle();
        ArrayList<String> newTab = new ArrayList<String>(driver.getWindowHandles());
        newTab.remove(oldTab);
        driver.switchTo().window(newTab.get(0)); // Изменить фокус на новую вкладку

        driverWait.until(ExpectedConditions.visibilityOf
                (driver.findElement(By.xpath("//DIV[@class='b-form-box-title ng-binding'][text()='Минимальная']"))));

        scrollIntoView(By.xpath("//DIV[@class='b-form-box-title ng-binding'][text()='Минимальная']")); // Сделать скролл
        driver.findElement(By.xpath("//DIV[@class='b-form-box-title ng-binding'][text()='Минимальная']")).click();
        scrollIntoView(By.xpath("//SPAN[@ng-click='save()'][text()='Оформить']")); // Сделать скролл

        driver.findElement(By.xpath("//SPAN[@ng-click='save()'][text()='Оформить']")).click();

        //Застрахованные
        clickAndSendKeys(By.xpath("//INPUT[@name='insured0_surname']"), "Petrov"); //Фамилия
        clickAndSendKeys(By.xpath("//INPUT[@name='insured0_name']"), "Petr"); //Имя
        clickAndSendKeys(By.xpath("//input[@name='insured0_birthDate']"), "19061991"); //Дата рождения

        scrollIntoView(By.xpath("//H3[@class='b-form-section-title'][text()='Страхователь']")); // Сделать скролл

        //Страхователь
        clickAndSendKeys(By.xpath("//INPUT[@name='surname']"), "Иванов"); //Фамилия
        clickAndSendKeys(By.xpath("//INPUT[@name='name']"), "Иван"); //Имя
        clickAndSendKeys(By.xpath("//INPUT[@name='middlename']"), "Иванович");  //Отчество
        clickAndSendKeys(By.xpath("//INPUT[@name='birthDate']"), "26041990"); //Дата рождения

        scrollIntoView(By.xpath("//H3[@class='b-form-section-title ng-binding'][text()='Данные паспорта РФ']")); // Сделать скролл

        driver.findElement(By.xpath("//DIV[@class='pageContainer']")).click();
        driverWait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//INPUT[@name='passport_series']"))));

        //Паспортные данные
        clickAndSendKeys(By.xpath("//INPUT[@name='passport_series']"),"4567"); //Серия
        clickAndSendKeys(By.xpath("//INPUT[@name='passport_number']"),"965185"); //Номер паспорта
        clickAndSendKeys(By.xpath("//INPUT[@name='issueDate']"),"05102017"); //Дата выдачи
        clickAndSendKeys(By.xpath("//TEXTAREA[@name='issuePlace']"),"УФМС России"); //Кем выдан

        driver.findElement(By.xpath("//SPAN[@ng-click='save()'][text()='Продолжить']")).click();
        Thread.sleep(3000);

        Assert.assertEquals("Заполнены не все обязательные поля",
                driver.findElement(By.xpath("//DIV[@ng-show='tryNext && myForm.$invalid'][text()='Заполнены не все обязательные поля']"))
                        .getText());

        System.out.println("тест пройден!");
    }

    public void scrollIntoView(By locator) {
        WebElement webElement = driver.findElement(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", webElement);
    }

    public void clickAndSendKeys(By locator, String value) {
        driver.findElement(locator).click();
        driver.findElement(locator).sendKeys(value);
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
