package testcases;

import base.BaseWebTest;
import common.Config;
import common.Helpers;
import common.InputConstants;
import common.Messages;
import org.junit.After;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author by dvasilev on 13-Sep-17.
 */
public class AccountCrudWebTests extends BaseWebTest {

    @Test
    public void smokeTest() {
        mainPage.getMainPage();
    }

    @Test
    public void addAccSuccess() {
        String uniqueEmail = Helpers.createEmail();
        mainPage.getMainPage()
                .inputFirstName(InputConstants.DEFAULT_NAME)
                .inputLastName(InputConstants.DEFAULT_NAME)
                .inputEmail(uniqueEmail)
                .inputDobNoDatepicker(InputConstants.DEFAULT_DOB)
                .addAccount()
                .search(uniqueEmail);
        Assert.assertTrue("table has no results", mainPage.tableHasResults());
        String actualEmail = mainPage.getEmailOfFirstGridItem();
        Assert.assertEquals("email not found", uniqueEmail, actualEmail);
    }

    @Test
    public void addAccBlankFormFailure() {
        mainPage.getMainPage()
                .addAccount();
        String alertMessage = mainPage.waitForAlertAndGetText();
        Assert.assertTrue("alert present but has wrong text", alertMessage.contains(Messages.FAILED_TO_ADD_ACCOUNT));
    }

    @Test
    public void addAccDobInFutureFailure() throws org.openqa.selenium.TimeoutException {
        String uniqueEmail = Helpers.createEmail();
        mainPage.getMainPage()
                .inputFirstName(InputConstants.DEFAULT_NAME)
                .inputLastName(InputConstants.DEFAULT_NAME)
                .inputEmail(uniqueEmail)
                .inputDobNoDatepicker(Helpers.createFutureDate())
                .addAccount();
        String alertMessage = mainPage.waitForAlertAndGetText();
        Assert.assertTrue("alert present but has wrong text", alertMessage.contains(Messages.DOB_CANNOT_BE_IN_THE_FUTURE));
        mainPage.search(uniqueEmail);
        Assert.assertFalse("entry added, despite the error message", mainPage.tableHasResults());
    }

    @Test
    public void addAccWrongEmailFormatFailure() {
        String uniqueEmail = Helpers.createEmail();
        mainPage.getMainPage()
                .inputFirstName(InputConstants.DEFAULT_NAME)
                .inputLastName(InputConstants.DEFAULT_NAME)
                .inputEmail("this_is_invalid_email@nodomain")
                .inputDobNoDatepicker(InputConstants.DEFAULT_DOB)
                .addAccount();
        String alertMessage = mainPage.waitForAlertAndGetText();
        Assert.assertTrue("alert present but has wrong text", alertMessage.contains(Messages.FAILED_TO_ADD_ACCOUNT));
        mainPage.search(uniqueEmail);
        Assert.assertFalse("entry added, despite the error message", mainPage.tableHasResults());
    }

    @Test
    public void updateAccFirstNameSuccess() {
        String uniqueEmail = Helpers.createEmail();
        mainPage.getMainPage()
                .inputFirstName(InputConstants.DEFAULT_NAME)
                .inputLastName(InputConstants.DEFAULT_NAME)
                .inputEmail(uniqueEmail)
                .inputDobNoDatepicker(InputConstants.DEFAULT_DOB)
                .addAccount()
                .search(uniqueEmail);
        Assert.assertTrue("table has no results", mainPage.tableHasResults());
        String actualEmail = mainPage.getEmailOfFirstGridItem();
        Assert.assertEquals("email not found", uniqueEmail, actualEmail);
        String updatedInput = InputConstants.DEFAULT_NAME + "UPDATED";
        mainPage.updateFirstName(updatedInput)
                .getMainPage()
                .search(uniqueEmail);
        String actualResult = mainPage.getFirstNameOfFirstGridItem();
        Assert.assertEquals("first name was not changed!", updatedInput, actualResult);
    }

    @Test
    public void updateAccFirstNameWordsOnlyFailure() {
        String uniqueEmail = Helpers.createEmail();
        mainPage.getMainPage()
                .inputFirstName(InputConstants.DEFAULT_NAME)
                .inputLastName(InputConstants.DEFAULT_NAME)
                .inputEmail(uniqueEmail)
                .inputDobNoDatepicker(InputConstants.DEFAULT_DOB)
                .addAccount()
                .search(uniqueEmail);
        Assert.assertTrue("table has no results", mainPage.tableHasResults());
        String actualEmail = mainPage.getEmailOfFirstGridItem();
        Assert.assertEquals("email not found", uniqueEmail, actualEmail);
        String updatedInput = InputConstants.DEFAULT_NAME + "123";
        mainPage.updateFirstName(updatedInput);
        String alertMessage = mainPage.waitForAlertAndGetText();
        Assert.assertTrue("alert present but has wrong text", alertMessage.contains(Messages.WORDS_ONLY_ALLOWED_IN_NAME));
        mainPage.getMainPage()
                .search(uniqueEmail);
        String actualResult = mainPage.getFirstNameOfFirstGridItem();
        Assert.assertNotEquals("first name was changed!", updatedInput, actualResult);
    }

    @Test
    public void updateAccFirstNameTooLongFailure() {
        String uniqueEmail = Helpers.createEmail();
        mainPage.getMainPage()
                .inputFirstName(InputConstants.DEFAULT_NAME)
                .inputLastName(InputConstants.DEFAULT_NAME)
                .inputEmail(uniqueEmail)
                .inputDobNoDatepicker(InputConstants.DEFAULT_DOB)
                .addAccount()
                .search(uniqueEmail);
        Assert.assertTrue("table has no results", mainPage.tableHasResults());
        String actualEmail = mainPage.getEmailOfFirstGridItem();
        Assert.assertEquals("email not found", uniqueEmail, actualEmail);
        String updatedInput = InputConstants.LONG_NAME_51_CHARS;
        mainPage.updateFirstName(updatedInput);
        String alertMessage = mainPage.waitForAlertAndGetText();
        Assert.assertTrue("alert present but has wrong text", alertMessage.contains(Messages.MAX_LENGTH_FIRST_NAME));
        mainPage.getMainPage()
                .search(uniqueEmail);
        String actualResult = mainPage.getFirstNameOfFirstGridItem();
        Assert.assertNotEquals("first name was changed!", updatedInput, actualResult);
    }

    @Test
    public void updateAccLastNameSuccess() {
        String uniqueEmail = Helpers.createEmail();
        mainPage.getMainPage()
                .inputFirstName(InputConstants.DEFAULT_NAME)
                .inputLastName(InputConstants.DEFAULT_NAME)
                .inputEmail(uniqueEmail)
                .inputDobNoDatepicker(InputConstants.DEFAULT_DOB)
                .addAccount()
                .search(uniqueEmail);
        Assert.assertTrue("table has no results", mainPage.tableHasResults());
        String actualEmail = mainPage.getEmailOfFirstGridItem();
        Assert.assertEquals("email not found", uniqueEmail, actualEmail);
        String updatedInput = InputConstants.DEFAULT_NAME + "UPDATED";
        mainPage.updateLastName(updatedInput)
                .getMainPage()
                .search(uniqueEmail);
        String actualResult = mainPage.getLastNameOfFirstGridItem();
        Assert.assertEquals("last name was not changed!", updatedInput, actualResult);
    }

    @Test
    public void updateAccLastNameWordsOnlyFailure() {
        String uniqueEmail = Helpers.createEmail();
        mainPage.getMainPage()
                .inputFirstName(InputConstants.DEFAULT_NAME)
                .inputLastName(InputConstants.DEFAULT_NAME)
                .inputEmail(uniqueEmail)
                .inputDobNoDatepicker(InputConstants.DEFAULT_DOB)
                .addAccount()
                .search(uniqueEmail);
        Assert.assertTrue("table has no results", mainPage.tableHasResults());
        String actualEmail = mainPage.getEmailOfFirstGridItem();
        Assert.assertEquals("email not found", uniqueEmail, actualEmail);
        String updatedInput = InputConstants.DEFAULT_NAME + "123";
        mainPage.updateLastName(updatedInput);
        String alertMessage = mainPage.waitForAlertAndGetText();
        Assert.assertTrue("alert present but has wrong text", alertMessage.contains(Messages.WORDS_ONLY_ALLOWED_IN_NAME));
        mainPage.getMainPage()
                .search(uniqueEmail);
        String actualResult = mainPage.getLastNameOfFirstGridItem();
        Assert.assertNotEquals("last name was changed!", updatedInput, actualResult);
    }

    @Test
    public void updateAccLastNameTooLongFailure() {
        String uniqueEmail = Helpers.createEmail();
        mainPage.getMainPage()
                .inputFirstName(InputConstants.DEFAULT_NAME)
                .inputLastName(InputConstants.DEFAULT_NAME)
                .inputEmail(uniqueEmail)
                .inputDobNoDatepicker(InputConstants.DEFAULT_DOB)
                .addAccount()
                .search(uniqueEmail);
        Assert.assertTrue("table has no results", mainPage.tableHasResults());
        String actualEmail = mainPage.getEmailOfFirstGridItem();
        Assert.assertEquals("email not found", uniqueEmail, actualEmail);
        String updatedInput = InputConstants.LONG_NAME_51_CHARS;
        mainPage.updateLastName(updatedInput);
        String alertMessage = mainPage.waitForAlertAndGetText();
        Assert.assertTrue("alert present but has wrong text", alertMessage.contains(Messages.MAX_LENGTH_LAST_NAME));
        mainPage.getMainPage()
                .search(uniqueEmail);
        String actualResult = mainPage.getLastNameOfFirstGridItem();
        Assert.assertNotEquals("last name was changed!", updatedInput, actualResult);
    }

    @Test
    public void updateAccEmailSuccess() {
        String uniqueEmail = Helpers.createEmail();
        mainPage.getMainPage()
                .inputFirstName(InputConstants.DEFAULT_NAME)
                .inputLastName(InputConstants.DEFAULT_NAME)
                .inputEmail(uniqueEmail)
                .inputDobNoDatepicker(InputConstants.DEFAULT_DOB)
                .addAccount()
                .search(uniqueEmail);
        Assert.assertTrue("table has no results", mainPage.tableHasResults());
        String actualEmail = mainPage.getEmailOfFirstGridItem();
        Assert.assertEquals("email not found", uniqueEmail, actualEmail);
        String updatedInput = "UPD" + uniqueEmail;
        mainPage.updateEmail(updatedInput)
                .getMainPage()
                .search(uniqueEmail);
        String actualResult = mainPage.getEmailOfFirstGridItem();
        Assert.assertEquals("email was not changed!", updatedInput, actualResult);
    }

    @Test
    public void updateAccInvalidEmailFailure() {
        String uniqueEmail = Helpers.createEmail();
        mainPage.getMainPage()
                .inputFirstName(InputConstants.DEFAULT_NAME)
                .inputLastName(InputConstants.DEFAULT_NAME)
                .inputEmail(uniqueEmail)
                .inputDobNoDatepicker(InputConstants.DEFAULT_DOB)
                .addAccount()
                .search(uniqueEmail);
        Assert.assertTrue("table has no results", mainPage.tableHasResults());
        String actualEmail = mainPage.getEmailOfFirstGridItem();
        Assert.assertEquals("email not found", uniqueEmail, actualEmail);
        String updatedInput = InputConstants.INVALID_EMAIL;
        mainPage.updateEmail(updatedInput);
        String alertMessage = mainPage.waitForAlertAndGetText();
        Assert.assertTrue("alert present but has wrong text", alertMessage.contains(Messages.INVALID_EMAIL));
        mainPage.getMainPage()
                .search(uniqueEmail);
        String actualResult = mainPage.getEmailOfFirstGridItem();
        Assert.assertNotEquals("email was not changed!", updatedInput, actualResult);
    }

    @Test
    public void updateAccEmailTooLongFailure() {
        String uniqueEmail = Helpers.createEmail();
        mainPage.getMainPage()
                .inputFirstName(InputConstants.DEFAULT_NAME)
                .inputLastName(InputConstants.DEFAULT_NAME)
                .inputEmail(uniqueEmail)
                .inputDobNoDatepicker(InputConstants.DEFAULT_DOB)
                .addAccount()
                .search(uniqueEmail);
        Assert.assertTrue("table has no results", mainPage.tableHasResults());
        String actualEmail = mainPage.getEmailOfFirstGridItem();
        Assert.assertEquals("email not found", uniqueEmail, actualEmail);
        String updatedInput = InputConstants.LONG_EMAIL_256_CHARS;
        mainPage.updateEmail(updatedInput);
        String alertMessage = mainPage.waitForAlertAndGetText();
        Assert.assertTrue("alert present but has wrong text", alertMessage.contains(Messages.MAX_LENGTH_EMAIL));
        mainPage.getMainPage()
                .search(uniqueEmail);
        String actualResult = mainPage.getEmailOfFirstGridItem();
        Assert.assertNotEquals("email was not changed!", updatedInput, actualResult);
    }

    @Test
    public void updateAccDobSuccess() {
        String uniqueEmail = Helpers.createEmail();
        mainPage.getMainPage()
                .inputFirstName(InputConstants.DEFAULT_NAME)
                .inputLastName(InputConstants.DEFAULT_NAME)
                .inputEmail(uniqueEmail)
                .inputDobNoDatepicker(InputConstants.DEFAULT_DOB)
                .addAccount()
                .search(uniqueEmail);
        Assert.assertTrue("table has no results", mainPage.tableHasResults());
        String actualEmail = mainPage.getEmailOfFirstGridItem();
        Assert.assertEquals("email not found", uniqueEmail, actualEmail);
        String updatedInput = "01/01/1970 02:00";
        mainPage.updateDateOfBirth(updatedInput)
                .getMainPage()
                .search(uniqueEmail);
        String actualResult = mainPage.getDobOfFirstGridItem();
        Assert.assertEquals("DoB is incorrect!", updatedInput, actualResult);
    }

    @Test
    public void updateAccEmptyDateFailure() {
        String uniqueEmail = Helpers.createEmail();
        mainPage.getMainPage()
                .inputFirstName(InputConstants.DEFAULT_NAME)
                .inputLastName(InputConstants.DEFAULT_NAME)
                .inputEmail(uniqueEmail)
                .inputDobNoDatepicker(InputConstants.DEFAULT_DOB)
                .addAccount()
                .search(uniqueEmail);
        Assert.assertTrue("table has no results", mainPage.tableHasResults());
        String actualEmail = mainPage.getEmailOfFirstGridItem();
        Assert.assertEquals("email not found", uniqueEmail, actualEmail);
        String updatedInput = "";
        mainPage.updateDateOfBirth(updatedInput);
        String alertMessage = mainPage.waitForAlertAndGetText();

        Assert.assertTrue("alert present but has wrong text", alertMessage.contains(Messages.MISSING_DOB_FRONTEND));
        mainPage.getMainPage()
                .search(uniqueEmail);
        String actualResult = mainPage.getDobOfFirstGridItem();
        Assert.assertNotEquals("DoB is incorrect!", updatedInput, actualResult);
    }

    @Test
    public void updateAccInvalidDateFailure() {
        String uniqueEmail = Helpers.createEmail();
        mainPage.getMainPage()
                .inputFirstName(InputConstants.DEFAULT_NAME)
                .inputLastName(InputConstants.DEFAULT_NAME)
                .inputEmail(uniqueEmail)
                .inputDobNoDatepicker(InputConstants.DEFAULT_DOB)
                .addAccount()
                .search(uniqueEmail);
        Assert.assertTrue("table has no results", mainPage.tableHasResults());
        String actualEmail = mainPage.getEmailOfFirstGridItem();
        Assert.assertEquals("email not found", uniqueEmail, actualEmail);
        String updatedInput = "invalid date";
        String originaDate = mainPage.getDobOfFirstGridItem();
        mainPage.updateDateOfBirth(updatedInput);
        String alertMessage = mainPage.waitForAlertAndGetText();

        Assert.assertTrue("alert present but has wrong text", alertMessage.contains("invalid date")); //bug, needs to be added later
        mainPage.getMainPage()
                .search(uniqueEmail);
        String actualResult = mainPage.getDobOfFirstGridItem();
        Assert.assertEquals("DoB is incorrect!", originaDate, actualResult);
    }

    @Test
    public void deleteAccSuccess() {
        String uniqueEmail = Helpers.createEmail();
        mainPage.getMainPage()
                .inputFirstName(InputConstants.DEFAULT_NAME)
                .inputLastName(InputConstants.DEFAULT_NAME)
                .inputEmail(uniqueEmail)
                .inputDobNoDatepicker(InputConstants.DEFAULT_DOB)
                .addAccount()
                .search(uniqueEmail);
        Assert.assertTrue("table has no results", mainPage.tableHasResults());
        String actualEmail = mainPage.getEmailOfFirstGridItem();
        Assert.assertEquals("email not found", uniqueEmail, actualEmail);
        mainPage.deleteDisplayedAccountByEmail(uniqueEmail)
                .getMainPage()
                .search(uniqueEmail);

        Assert.assertFalse("account was not deleted)", mainPage.tableHasResults());
    }
}
