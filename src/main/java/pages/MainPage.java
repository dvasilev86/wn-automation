package pages;

import com.sun.jna.platform.win32.WinBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

/**
 * @author by dvasilev on 13-Sep-17.
 */
public class MainPage {

    private static final String PAGE_URL = "http://borisborisov.bg/user-accounts/";
    private RemoteWebDriver pageDriver;

//    @FindBy(how = How.CSS, using = "#accounts-table > thead > tr:nth-child(2) > th:nth-child(1)")
//    private WebElement cssFirstNameColumn;
    private String cssFirstNameColumn = "#firstName_1490";

    public MainPage(RemoteWebDriver driver) {
        pageDriver = driver;
    }

    public void GetMainPage() {
        pageDriver.get(PAGE_URL);
//        WebDriverWait wait = new WebDriverWait(pageDriver, 1);
//        Wait<WebDriver> wait2 = new FluentWait<WebDriver>(pageDriver);
//
//        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(cssFirstNameColumn)));
    }

    public void InputFirstName() {

    }

    public void InputLastName() {

    }

    public void InputEmail() {

    }

    public void AddAccount() {

    }

    public void ChangeEntriesPerPage () {

    }

    public void Search() {

    }

    public void SortByFieldName() {

    }

    public void GetNextPage() {

    }

    public void GetPreviousPage() {

    }

    public void GetSpecificPage() {

    }

    public void DeleteAccountByRowPosition() {

    }

    public void UpdateFirstName() {

    }

    public void UpdateLastName() {

    }

    public void UpdateEmail() {

    }

    public void UpdateDateOfBirth() {

    }
}
