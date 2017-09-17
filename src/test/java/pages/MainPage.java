package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import sun.applet.Main;

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
    private String cssSearchForm = "#accounts-table_filter > label > input";
    private String cssDeleteFirstAccountButton = "#accounts-table > tbody > tr:nth-child(1) > td.delete > a";

    private String cssFirstResultFirstName = "#accounts-table > tbody > tr:nth-child(1) > td:nth-child(1)";
    private String cssFirstResultLastName = "#accounts-table > tbody > tr:nth-child(1) > td:nth-child(2)";
    private String cssFirstResultEmail = "#accounts-table > tbody > tr:nth-child(1) > td:nth-child(3)";
    private String cssFirstResultDob = "#accounts-table > tbody > tr:nth-child(1) > td:nth-child(4)";
    private String cssRows = "#accounts-table > tbody > tr";

    private String cssEntriesPerPageSelector = "#accounts-table_length > label > select";

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

    public String getFirstNameOfFirstGridItem() {
        return pageDriver.findElementByCssSelector(cssFirstResultFirstName).getText();
    }
    public String getLastNameOfFirstGridItem() {
        return pageDriver.findElementByCssSelector(cssFirstResultLastName).getText();
    }

    public String getEmailOfFirstGridItem() {
        return pageDriver.findElementByCssSelector(cssFirstResultEmail).getText();
    }
    public String getDobOfFirstGridItem() {
        return pageDriver.findElementByCssSelector(cssFirstResultDob).getText();
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

    public MainPage deleteDisplayedAccountByEmail(String email) {
        WebDriverWait wait = new WebDriverWait(pageDriver, 2);
        wait.until(ExpectedConditions.textToBe(By.cssSelector(cssFirstResultEmail), email));
        pageDriver.findElementByCssSelector(cssDeleteFirstAccountButton).click();
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = pageDriver.switchTo().alert();
        alert.accept();
        return this;
    }

    public MainPage changeEntriesPerPage(String desiredEntries) {
        Select selector = new Select(pageDriver.findElementByCssSelector(cssEntriesPerPageSelector));
        selector.selectByValue(desiredEntries);
        return this;
    }

    public int getRowsCount() {
        return pageDriver.findElementsByCssSelector(cssRows).size();
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

    public MainPage updateFirstName(String updatedString) {
        WebElement ele = pageDriver.findElementByCssSelector(cssFirstResultFirstName);
        ele.click();
        ele = pageDriver.findElementByCssSelector(cssFirstResultFirstName).findElement(new By.ByTagName("input"));
        ele.clear();
        ele.sendKeys(updatedString);
        ele.sendKeys(Keys.RETURN);

        return this;
    }

    public MainPage updateLastName(String updatedString) {
        WebElement ele = pageDriver.findElementByCssSelector(cssFirstResultLastName);
        ele.click();
        ele = pageDriver.findElementByCssSelector(cssFirstResultLastName).findElement(new By.ByTagName("input"));
        ele.clear();
        ele.sendKeys(updatedString);
        ele.sendKeys(Keys.RETURN);

        return this;
    }

    public MainPage updateEmail(String updatedString) {
        WebElement ele = pageDriver.findElementByCssSelector(cssFirstResultEmail);
        ele.click();
        ele = pageDriver.findElementByCssSelector(cssFirstResultEmail).findElement(new By.ByTagName("input"));
        ele.clear();
        ele.sendKeys(updatedString);
        ele.sendKeys(Keys.RETURN);

        try {
            WebDriverWait wait = new WebDriverWait(pageDriver, 2);
            wait.until(ExpectedConditions.textToBe(By.cssSelector(cssFirstResultEmail), updatedString));
        }
        catch (TimeoutException e) {
            System.out.println("timeout exception");
        }
        return this;
    }

    public MainPage updateDateOfBirth(String updatedString) {
        WebElement ele = pageDriver.findElementByCssSelector(cssFirstResultDob);
        ele.click();
        ele = pageDriver.findElementByCssSelector(cssFirstResultDob).findElement(new By.ByTagName("input"));
        ele.clear();
        ele.sendKeys(updatedString);
        ele.sendKeys(Keys.RETURN);

        try {
            WebDriverWait wait = new WebDriverWait(pageDriver, 2);
            wait.until(ExpectedConditions.textToBe(By.cssSelector(cssFirstResultDob), updatedString));
        }
        catch (TimeoutException e) {
            System.out.println("timeout exception");
        }

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
