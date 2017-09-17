package pages;

import common.Messages;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * @author by dvasilev on 13-Sep-17.
 */
public class MainPage {

    private static final String PAGE_URL = "http://borisborisov.bg/user-accounts/";
    private RemoteWebDriver pageDriver;

    // id identifiers
    private String idFirstNameInputField = "first-name";
    private String idLastNameInputField = "last-name";
    private String idEmailInputField = "email";
    private String idDateOfBirthInputField = "date-of-birth";

    // class identifiers
    private String classBlockingOverlay = ".blockUI.blockOverlay";
    private String classNoResults = "dataTables_empty";

    // css identifiers
    private String cssAddAccount = "#add-account-form > button";
    private String cssFirstResultFirstName = "#accounts-table > tbody > tr:nth-child(1) > td:nth-child(3)";
    private String cssSearchForm = "#accounts-table_filter > label > input";


    public MainPage(RemoteWebDriver driver) {
        pageDriver = driver;
    }

    public MainPage getMainPage() {
        pageDriver.get(PAGE_URL);
        WebDriverWait wait = new WebDriverWait(pageDriver, 2);
        wait.until(ExpectedConditions.visibilityOf(pageDriver.findElement(By.cssSelector(classBlockingOverlay))));
        wait.until(ExpectedConditions.invisibilityOf(pageDriver.findElement(By.cssSelector(classBlockingOverlay))));
        return this;
    }

    public MainPage inputFirstName(String userInput) {
        pageDriver.findElement(By.id(idFirstNameInputField)).sendKeys(userInput);
        return this;
    }

    public MainPage inputLastName(String userInput) {
        pageDriver.findElement(By.id(idLastNameInputField)).sendKeys(userInput);
        return this;
    }

    public MainPage inputEmail(String userInput) {
        pageDriver.findElement(By.id(idEmailInputField)).sendKeys(userInput);
        return this;
    }

    public MainPage inputDobNoDatepicker(String userInput) {
        pageDriver.findElement(By.id(idDateOfBirthInputField)).sendKeys(userInput);
        return this;
    }

    public MainPage addAccount() {
        pageDriver.findElementByCssSelector(cssAddAccount).click();
        return this;
    }

    public MainPage search(String userInput) {
        pageDriver.findElementByCssSelector(cssSearchForm).sendKeys(userInput);
        return this;
    }

    public String getEmailOfFirstGridItem() {
        return pageDriver.findElementByCssSelector(cssFirstResultFirstName).getText();
    }

    public boolean tableHasResults() {
        try {
            WebDriverWait wait = new WebDriverWait(pageDriver, 2);
            wait.until(ExpectedConditions.presenceOfElementLocated(By.className(classNoResults)));
            return false;
        } catch (org.openqa.selenium.TimeoutException e) {
            return true;
        }
    }

    public MainPage changeEntriesPerPage() {

        return this;
    }

    public MainPage sortByFieldName() {

        return this;
    }

    public MainPage getNextPage() {

        return this;
    }

    public MainPage getPreviousPage() {

        return this;
    }

    public MainPage getSpecificPage() {

        return this;
    }

    public MainPage deleteAccountByRowPosition() {

        return this;
    }

    public MainPage updateFirstName() {

        return this;
    }

    public MainPage updateLastName() {

        return this;
    }

    public MainPage updateEmail() {

        return this;
    }

    public MainPage updateDateOfBirth() {

        return this;
    }

    public String waitForAlertAndGetText() {
        WebDriverWait wait = new WebDriverWait(pageDriver, 2);
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = pageDriver.switchTo().alert();
        String text = alert.getText();
        alert.dismiss();
        return text;
    }
}
