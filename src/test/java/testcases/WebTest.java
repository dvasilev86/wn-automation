package testcases;

import base.BaseWebTest;
import common.Config;
import common.Helpers;
import common.InputConstants;
import common.Messages;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author by dvasilev on 13-Sep-17.
 */
public class WebTest extends BaseWebTest {

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
    @Ignore
    public void updateAccFirstNameSuccess() {
        mainPage.getMainPage();
    }

    @Test
    @Ignore
    public void updateAccDobSuccess() {
        mainPage.getMainPage();
    }

    @Test
    @Ignore
    public void filterResultsSuccess() {

    }

    @Test
    @Ignore
    public void deleteAccSuccess() {
        mainPage.getMainPage();
    }


}
