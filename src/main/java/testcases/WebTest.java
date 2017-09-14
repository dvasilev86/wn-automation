package testcases;

import base.BaseWebTest;
import common.Config;
import common.Helpers;
import common.Messages;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author by dvasilev on 13-Sep-17.
 */
public class WebTest extends BaseWebTest {

    @Test
    public void Test() {
        mainPage.getMainPage();
    }

    @Test
    public void addAccSuccess() {
        String uniqueEmail = Config.DEFAULT_PREFIX + Helpers.randomInput() + Config.DUMMY_EMAIL_PROVIDER;
        mainPage.getMainPage()
                .inputFirstName(Config.DEFAULT_NAME)
                .inputLastName(Config.DEFAULT_NAME)
                .inputEmail(uniqueEmail)
                .inputDobNoDatepicker(Config.DEFAULT_DOB)
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
        String uniqueEmail = Config.DEFAULT_PREFIX + Helpers.randomInput() + Config.DUMMY_EMAIL_PROVIDER;
        mainPage.getMainPage()
                .inputFirstName(Config.DEFAULT_NAME)
                .inputLastName(Config.DEFAULT_NAME)
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
        String uniqueEmail = Config.DEFAULT_PREFIX + Helpers.randomInput() + Config.DUMMY_EMAIL_PROVIDER;
        mainPage.getMainPage()
                .inputFirstName(Config.DEFAULT_NAME)
                .inputLastName(Config.DEFAULT_NAME)
                .inputEmail("this_is_invalid_email@nodomain")
                .inputDobNoDatepicker(Config.DEFAULT_DOB)
                .addAccount();
        String alertMessage = mainPage.waitForAlertAndGetText();
        Assert.assertTrue("alert present but has wrong text", alertMessage.contains(Messages.FAILED_TO_ADD_ACCOUNT));
        mainPage.search(uniqueEmail);
        Assert.assertFalse("entry added, despite the error message", mainPage.tableHasResults());
    }

    @Test
    public void updateAccFirstNameSuccess() {
        mainPage.getMainPage();
    }

    @Test
    public void updateAccDobSuccess() {
        mainPage.getMainPage();
    }

    @Test
    public void filterResultsSuccess() {

    }

    @Test
    public void deleteAccSuccess() {
        mainPage.getMainPage();
    }


}
