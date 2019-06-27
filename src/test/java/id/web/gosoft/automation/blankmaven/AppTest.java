package id.web.gosoft.automation.blankmaven;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Every.everyItem;
import static org.junit.Assert.assertTrue;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
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
        assertThat("Argo is not found " , textSearchSuggestion ,everyItem(containsString("argo")));
        webDriver.close();
    }
}
