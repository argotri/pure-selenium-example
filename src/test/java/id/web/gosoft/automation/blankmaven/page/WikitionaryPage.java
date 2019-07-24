package id.web.gosoft.automation.blankmaven.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

public class WikitionaryPage extends PageObject {

    @FindBy(name="search")
    private WebElement searchTerms;

    @FindBy(name="go")
    private WebElement lookupButton;

    public WikitionaryPage(WebDriver driver) {
        super(driver);
    }

    public void enter_keywords(String keyword) {
        searchTerms.sendKeys(keyword);
    }

    public void lookup_terms() {
        lookupButton.click();
    }

    public List<String> getDefinitions() {
        WebElement definitionList = driver.findElement(By.tagName("ol"));
        return definitionList.findElements(By.tagName("li")).stream()
                .map( element -> element.getText() )
                .collect(Collectors.toList());
    }

}
