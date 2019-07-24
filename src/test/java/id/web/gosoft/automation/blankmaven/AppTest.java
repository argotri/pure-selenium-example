package id.web.gosoft.automation.blankmaven;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Every.everyItem;
import static org.junit.Assert.assertTrue;

import id.web.gosoft.automation.blankmaven.page.WikitionaryPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("headless");
        options.addArguments("window-size=1200x600");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        WebDriver webDriver = new ChromeDriver(options);
        webDriver.get("https://www.google.co.id/");
        webDriver.findElement(By.name("q")).sendKeys("argo");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<WebElement> searchSuggestion =webDriver.findElements(By.xpath("//li[@class=\"sbct\"]//div[@class=\"sbl1\"]"));
        List<String> textSearchSuggestion = searchSuggestion.stream().map(webElement -> webElement.getText().toLowerCase()).filter(s -> s.length() > 1).collect(Collectors.toList());
        System.out.println(textSearchSuggestion.toString());
        File src= ((TakesScreenshot)webDriver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(src, new File(System.getProperty("user.dir")+"/"+System.currentTimeMillis()+".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertThat("Argo is not found " , textSearchSuggestion ,everyItem(containsString("argo")));
        webDriver.close();
    }


    protected static WebDriver driver;

    @BeforeClass
    public static void setUp(){
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @After
    public void cleanUp(){
        driver.manage().deleteAllCookies();
    }

    @AfterClass
    public static void tearDown(){
        driver.close();
    }

    @Test
    public void searchOnwikitionary(){
        driver.get("http://en.wiktionary.org/wiki/Wiktionary");
        WikitionaryPage wikitionaryPage = new WikitionaryPage(driver);
        wikitionaryPage.enter_keywords("apple");
        wikitionaryPage.lookup_terms();
        List<String> appleDefinition = wikitionaryPage.getDefinitions();
        assertThat(appleDefinition,hasItem(containsString("A common, round fruit produced by the tree Malus domestica")));
    }

}
