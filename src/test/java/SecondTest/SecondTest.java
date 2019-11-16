package SecondTest;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Created by yasup on 13.11.2019.
 */

@RunWith(Parameterized.class)
public class SecondTest {

    private WebDriver driver;
    private String URL;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"Ivanov", "Ivan", "01.01.1991", "Иванов", "Иван", "Иванович", "01.01.1991", "1111", "111111", "01.01.2011", "УФМС г. Пермь"},
                {"Petrov", "Petr", "02.02.1992", "Петров", "Петр", "Петрович", "02.02.1992", "2222", "222222", "02.02.2012", "УФМС г. Москва"},
                {"Sidorov", "Sidor", "03.03.1993", "Сидоров", "Сидор", "Сидорович", "03.03.1993", "3333", "333333", "03.03.2013", "УФМС г. Тула"}});
    }

    @Parameterized.Parameter
    public String insured0_surname;
    @Parameterized.Parameter(1)
    public String insured0_name;
    @Parameterized.Parameter(2)
    public String insured0_birthDate;
    @Parameterized.Parameter(3)
    public String surname;
    @Parameterized.Parameter(4)
    public String name;
    @Parameterized.Parameter(5)
    public String middleName;
    @Parameterized.Parameter(6)
    public String birthDate;
    @Parameterized.Parameter(7)
    public String passport_series;
    @Parameterized.Parameter(8)
    public String passport_number;
    @Parameterized.Parameter(9)
    public String issueDate;
    @Parameterized.Parameter(10)
    public String issuePlace;

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "drv/chromedriver78.exe");
        driver = new ChromeDriver();
//        System.setProperty("webdriver.ie.driver", "drv/IEDriverServer.exe");
//        driver = new InternetExplorerDriver();
//        System.setProperty("webdriver.gecko.driver", "drv/geckodriver.exe");
//        driver = new FirefoxDriver();
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
        clickAndSendKeys(By.xpath("//INPUT[@name='insured0_surname']"), insured0_surname); //Фамилия
        clickAndSendKeys(By.xpath("//INPUT[@name='insured0_name']"), insured0_name); //Имя
        clickAndSendKeys(By.xpath("//input[@name='insured0_birthDate']"), insured0_birthDate); //Дата рождения

        scrollIntoView(By.xpath("//H3[@class='b-form-section-title'][text()='Страхователь']")); // Сделать скролл

        //Страхователь
        clickAndSendKeys(By.xpath("//INPUT[@name='surname']"), surname); //Фамилия
        clickAndSendKeys(By.xpath("//INPUT[@name='name']"), name); //Имя
        clickAndSendKeys(By.xpath("//INPUT[@name='middlename']"), middleName);  //Отчество
        clickAndSendKeys(By.xpath("//INPUT[@name='birthDate']"), birthDate); //Дата рождения

        scrollIntoView(By.xpath("//H3[@class='b-form-section-title ng-binding'][text()='Данные паспорта РФ']")); // Сделать скролл

        driver.findElement(By.xpath("//DIV[@class='pageContainer']")).click();
        driverWait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//INPUT[@name='passport_series']"))));

        //Паспортные данные
        clickAndSendKeys(By.xpath("//INPUT[@name='passport_series']"), passport_series); //Серия
        clickAndSendKeys(By.xpath("//INPUT[@name='passport_number']"), passport_number); //Номер паспорта
        clickAndSendKeys(By.xpath("//INPUT[@name='issueDate']"), issueDate); //Дата выдачи
        clickAndSendKeys(By.xpath("//TEXTAREA[@name='issuePlace']"), issuePlace); //Кем выдан

        assertThat(driver.findElement(By.xpath("//INPUT[@name='insured0_surname']")).getAttribute("value"), equalTo(insured0_surname));
        assertThat(driver.findElement(By.xpath("//INPUT[@name='insured0_name']")).getAttribute("value"), equalTo(insured0_name));
        assertThat(driver.findElement(By.xpath("//input[@name='insured0_birthDate']")).getAttribute("value"), equalTo(insured0_birthDate));
        assertThat(driver.findElement(By.xpath("//INPUT[@name='surname']")).getAttribute("value"), equalTo(surname));
        assertThat(driver.findElement(By.xpath("//INPUT[@name='name']")).getAttribute("value"), equalTo(name));
        assertThat(driver.findElement(By.xpath("//INPUT[@name='middlename']")).getAttribute("value"), equalTo(middleName));
        assertThat(driver.findElement(By.xpath("//INPUT[@name='birthDate']")).getAttribute("value"), equalTo(birthDate));
        assertThat(driver.findElement(By.xpath("//INPUT[@name='passport_series']")).getAttribute("value"), equalTo(passport_series));
        assertThat(driver.findElement(By.xpath("//INPUT[@name='passport_number']")).getAttribute("value"), equalTo(passport_number));
        assertThat(driver.findElement(By.xpath("//INPUT[@name='issueDate']")).getAttribute("value"), equalTo(issueDate));
        assertThat(driver.findElement(By.xpath("//TEXTAREA[@name='issuePlace']")).getAttribute("value"), equalTo(issuePlace));
        driver.findElement(By.xpath("//SPAN[@ng-click='save()'][text()='Продолжить']")).click();
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
