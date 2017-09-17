package testcases;

import base.BaseApiTest;
import com.google.gson.JsonObject;
import common.*;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import rest.AddAccountRequest;
import retrofit2.Response;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author by dvasilev on 15-Sep-17.
 */
public class ApiTests extends BaseApiTest {

    @Test
    public void getAllAccountsSuccess() throws IOException {
        Response response = api.getAllAccounts().execute();
        Assert.assertEquals(Codes.RESPONSE_OK, response.code());
    }

    @Test
    public void getSingleAccountSuccess() throws IOException {
        String email = Helpers.createEmail();
        AddAccountRequest addAccountRequest = new AddAccountRequest(
                email,
                InputConstants.DEFAULT_NAME,
                InputConstants.DEFAULT_NAME,
                Helpers.getEpochTimeNow());
        Response response = api.addAccount(addAccountRequest).execute();
        Assert.assertEquals(Codes.RESPONSE_CREATED, response.code());
        String id = ((JsonObject) response.body()).get("id").getAsString();
        response = api.getSingleAccount(id).execute();
        Assert.assertEquals(Codes.RESPONSE_OK, response.code());
        String actualEmail = ((JsonObject) response.body()).get("email").getAsString();
        Assert.assertEquals(email, actualEmail);
    }

    @Test
    public void getNonexistentAccountIdFailure() throws IOException {
        String id = "1";
        api.deleteSingleAccount(id).execute();
        Response response = api.getSingleAccount(id).execute();
        Assert.assertEquals(Codes.RESPONSE_NOT_FOUND, response.code());
    }

    @Test
    public void addAccountSuccess() throws IOException {
        AddAccountRequest addAccountRequest = prepareGenericAccountRequest();
        Response response = api.addAccount(addAccountRequest).execute();
        Assert.assertEquals("response code is wrong", Codes.RESPONSE_CREATED, response.code());
    }

    @Test
    public void addAccountBlankFirstNameFailure() throws IOException {
        AddAccountRequest addAccountRequest = new AddAccountRequest(
                Helpers.createEmail(),
                null,
                InputConstants.DEFAULT_NAME,
                Helpers.getEpochTimeNow());
        Response response = api.addAccount(addAccountRequest).execute();
        Assert.assertEquals(Codes.RESPONSE_BAD_REQUEST, response.code());
        Assert.assertEquals(Messages.MISSING_FIRST_NAME, response.errorBody().string());
    }

    @Test
    public void addAccountBlankLastNameFailure() throws IOException {
        AddAccountRequest addAccountRequest = new AddAccountRequest(
                Helpers.createEmail(),
                InputConstants.DEFAULT_NAME,
                null,
                Helpers.getEpochTimeNow());
        Response response = api.addAccount(addAccountRequest).execute();
        Assert.assertEquals(Codes.RESPONSE_BAD_REQUEST, response.code());
        Assert.assertEquals(Messages.MISSING_LAST_NAME, response.errorBody().string());
    }

    @Test
    public void addAccountBlankEmailFailure() throws IOException {
        AddAccountRequest addAccountRequest = new AddAccountRequest(
                null,
                InputConstants.DEFAULT_NAME,
                InputConstants.DEFAULT_NAME,
                Helpers.getEpochTimeNow());
        Response response = api.addAccount(addAccountRequest).execute();
        Assert.assertEquals(Codes.RESPONSE_BAD_REQUEST, response.code());
        Assert.assertEquals(Messages.MISSING_EMAIL, response.errorBody().string());
    }

    @Test
    public void addAccountInvalidDobFailure() throws IOException {
        AddAccountRequest addAccountRequest = new AddAccountRequest(
                Helpers.createEmail(),
                InputConstants.DEFAULT_NAME,
                InputConstants.DEFAULT_NAME,
                null);
        Response response = api.addAccount(addAccountRequest).execute();
        Assert.assertEquals(Codes.RESPONSE_BAD_REQUEST, response.code());
        Assert.assertEquals(Messages.MISSING_DOB, response.errorBody().string());
    }

    @Test
    public void addAccountDobInTheFutureFailure() throws IOException, ParseException {
        String desiredValue = Helpers.createFutureDate();
        AddAccountRequest addAccountRequest = new AddAccountRequest(
                Helpers.createEmail(),
                InputConstants.DEFAULT_NAME,
                InputConstants.DEFAULT_NAME,
                Helpers.dateToEpoch(desiredValue));
        Response response = api.addAccount(addAccountRequest).execute();
        Assert.assertEquals(Codes.RESPONSE_BAD_REQUEST, response.code());
        Assert.assertEquals(Messages.DOB_CANNOT_BE_IN_THE_FUTURE, response.errorBody().string());
    }

    @Test
    public void addAccountWordsOnlyFailure() throws IOException {
        AddAccountRequest addAccountRequest = new AddAccountRequest(
                Helpers.createEmail(),
                "test123",
                InputConstants.DEFAULT_NAME,
                Helpers.getEpochTimeNow());
        Response response = api.addAccount(addAccountRequest).execute();
        Assert.assertEquals(Codes.RESPONSE_BAD_REQUEST, response.code());
        Assert.assertEquals(Messages.WORDS_ONLY_ALLOWED_IN_NAME, response.errorBody().string());
    }

    @Test
    public void addAccountFirstNameTooLongFailure() throws IOException {
        AddAccountRequest addAccountRequest = new AddAccountRequest(
                Helpers.createEmail(),
                InputConstants.LONG_NAME_51_CHARS,
                InputConstants.DEFAULT_NAME,
                Helpers.getEpochTimeNow());
        Response response = api.addAccount(addAccountRequest).execute();
        Assert.assertEquals(Codes.RESPONSE_BAD_REQUEST, response.code());
        Assert.assertTrue(response.errorBody().string().contains(Messages.MAX_LENGTH_FIRST_NAME));
    }

    @Test
    public void addAccountLastNameTooLongFailure() throws IOException {
        AddAccountRequest addAccountRequest = new AddAccountRequest(
                Helpers.createEmail(),
                InputConstants.DEFAULT_NAME,
                InputConstants.LONG_NAME_51_CHARS,
                Helpers.getEpochTimeNow());
        Response response = api.addAccount(addAccountRequest).execute();
        Assert.assertEquals(Codes.RESPONSE_BAD_REQUEST, response.code());
        Assert.assertTrue(response.errorBody().string().contains(Messages.MAX_LENGTH_LAST_NAME));
    }

    @Test
    public void addAccountEmailTooLongFailure() throws IOException {
        AddAccountRequest addAccountRequest = new AddAccountRequest(
                InputConstants.LONG_EMAIL_256_CHARS,
                InputConstants.DEFAULT_NAME, //51 chars long
                InputConstants.DEFAULT_NAME,
                Helpers.getEpochTimeNow());
        Response response = api.addAccount(addAccountRequest).execute();
        Assert.assertEquals(Codes.RESPONSE_BAD_REQUEST, response.code());
        Assert.assertTrue(response.errorBody().string().contains(Messages.MAX_LENGTH_EMAIL));
    }

    @Test
    public void addAccountInvalidEmailFailure() throws IOException {
        AddAccountRequest addAccountRequest = new AddAccountRequest(
                InputConstants.INVALID_EMAIL,
                InputConstants.DEFAULT_NAME,
                InputConstants.DEFAULT_NAME,
                Helpers.getEpochTimeNow());
        Response response = api.addAccount(addAccountRequest).execute();
        Assert.assertEquals(Codes.RESPONSE_BAD_REQUEST, response.code());
        Assert.assertEquals(Messages.INVALID_EMAIL, response.errorBody().string());
    }

    @Test
    public void deleteAccountSuccess() throws IOException {
        AddAccountRequest addAccountRequest = prepareGenericAccountRequest();
        Response response = api.addAccount(addAccountRequest).execute();
        Assert.assertEquals(Codes.RESPONSE_CREATED, response.code());
        String id = ((JsonObject) response.body()).get("id").getAsString();
        response = api.deleteSingleAccount(id).execute();
        Assert.assertEquals(Codes.RESPONSE_OK, response.code());
        response = api.getSingleAccount(id).execute();
        Assert.assertEquals(Codes.RESPONSE_NOT_FOUND, response.code());
    }

    @Test
    public void deleteAccountNotFoundFailure() throws IOException {
        AddAccountRequest addAccountRequest = prepareGenericAccountRequest();
        Response response = api.addAccount(addAccountRequest).execute();
        Assert.assertEquals(Codes.RESPONSE_CREATED, response.code());
        String id = ((JsonObject) response.body()).get("id").getAsString();
        response = api.deleteSingleAccount(id).execute();
        Assert.assertEquals(Codes.RESPONSE_OK, response.code());
        response = api.getSingleAccount(id).execute();
        Assert.assertEquals(Codes.RESPONSE_NOT_FOUND, response.code());
        response = api.deleteSingleAccount(id).execute();
        Assert.assertEquals(Codes.RESPONSE_NOT_FOUND, response.code());
    }

    @Test
    public void updateFirstNameSuccess() throws IOException {
        AddAccountRequest addAccountRequest = prepareGenericAccountRequest();
        Response response = api.addAccount(addAccountRequest).execute();
        Assert.assertEquals(Codes.RESPONSE_CREATED, response.code());

        String id = ((JsonObject) response.body()).get("id").getAsString();
        String desiredField = "firstName_" + id;
        String desiredValue = "newValue";
        response = api.updateField(desiredField, desiredValue).execute();
        Assert.assertEquals(Codes.RESPONSE_OK, response.code());
        response = api.getSingleAccount(id).execute();
        Assert.assertEquals(Codes.RESPONSE_OK, response.code());

        String actualValue = ((JsonObject) response.body()).get("firstName").getAsString();
        Assert.assertEquals(desiredValue, actualValue);
    }

    @Test
    public void updateLastNameSuccess() throws IOException {
        AddAccountRequest addAccountRequest = prepareGenericAccountRequest();
        Response response = api.addAccount(addAccountRequest).execute();
        Assert.assertEquals(Codes.RESPONSE_CREATED, response.code());

        String id = ((JsonObject) response.body()).get("id").getAsString();
        String desiredField = "lastName_" + id;
        String desiredValue = "newValue";
        response = api.updateField(desiredField, desiredValue).execute();
        Assert.assertEquals(Codes.RESPONSE_OK, response.code());
        response = api.getSingleAccount(id).execute();
        Assert.assertEquals(Codes.RESPONSE_OK, response.code());

        String actualValue = ((JsonObject) response.body()).get("lastName").getAsString();
        Assert.assertEquals(desiredValue, actualValue);
    }

    @Test
    public void updateEmailSuccess() throws IOException {
        AddAccountRequest addAccountRequest = prepareGenericAccountRequest();
        Response response = api.addAccount(addAccountRequest).execute();
        Assert.assertEquals(Codes.RESPONSE_CREATED, response.code());

        String id = ((JsonObject) response.body()).get("id").getAsString();
        String desiredField = "email_" + id;
        String desiredValue = Helpers.createEmail();
        response = api.updateField(desiredField, desiredValue).execute();
        Assert.assertEquals(Codes.RESPONSE_OK, response.code());
        response = api.getSingleAccount(id).execute();
        Assert.assertEquals(Codes.RESPONSE_OK, response.code());

        String actualValue = ((JsonObject) response.body()).get("email").getAsString();
        Assert.assertEquals(desiredValue, actualValue);
    }

    @Test
    public void updateDobSuccess() throws IOException {
        AddAccountRequest addAccountRequest = prepareGenericAccountRequest();
        Response response = api.addAccount(addAccountRequest).execute();
        Assert.assertEquals(Codes.RESPONSE_CREATED, response.code());

        String id = ((JsonObject) response.body()).get("id").getAsString();
        String desiredField = "dateOfBirth_" + id;
        String desiredValue = InputConstants.DEFAULT_DOB;
        response = api.updateField(desiredField, desiredValue).execute();
        Assert.assertEquals(Codes.RESPONSE_OK, response.code());
        response = api.getSingleAccount(id).execute();
        Assert.assertEquals(Codes.RESPONSE_OK, response.code());

        String unformattedDate = ((JsonObject) response.body()).get("dateOfBirth").getAsString();
        Date date = new Date(Long.parseLong(unformattedDate));
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        format.setTimeZone(TimeZone.getTimeZone(""));

        String actualValue = format.format(date);
        Assert.assertEquals(desiredValue, actualValue);
    }

    @Test
    public void updateFirstNameWordsOnlyFailure() throws IOException {
        AddAccountRequest addAccountRequest = prepareGenericAccountRequest();
        Response response = api.addAccount(addAccountRequest).execute();
        Assert.assertEquals(Codes.RESPONSE_CREATED, response.code());

        String id = ((JsonObject) response.body()).get("id").getAsString();
        String desiredField = "firstName_" + id;
        String desiredValue = "newValue123";
        response = api.updateField(desiredField, desiredValue).execute();
        Assert.assertEquals(Codes.RESPONSE_BAD_REQUEST, response.code());
        response = api.getSingleAccount(id).execute();
        Assert.assertEquals(Codes.RESPONSE_OK, response.code());

        String actualValue = ((JsonObject) response.body()).get("firstName").getAsString();
        Assert.assertNotEquals(desiredValue, actualValue);
    }

    @Test
    public void updateLastNameWordsOnlyFailure() throws IOException {
        AddAccountRequest addAccountRequest = prepareGenericAccountRequest();
        Response response = api.addAccount(addAccountRequest).execute();
        Assert.assertEquals(Codes.RESPONSE_CREATED, response.code());

        String id = ((JsonObject) response.body()).get("id").getAsString();
        String desiredField = "lastName_" + id;
        String desiredValue = "newValue123";
        response = api.updateField(desiredField, desiredValue).execute();
        Assert.assertEquals(Codes.RESPONSE_BAD_REQUEST, response.code());
        response = api.getSingleAccount(id).execute();
        Assert.assertEquals(Codes.RESPONSE_OK, response.code());

        String actualValue = ((JsonObject) response.body()).get("lastName").getAsString();
        Assert.assertNotEquals(desiredValue, actualValue);
    }

    @Test
    public void updateInvalidEmailFailure() throws IOException {
        AddAccountRequest addAccountRequest = prepareGenericAccountRequest();
        Response response = api.addAccount(addAccountRequest).execute();
        Assert.assertEquals(Codes.RESPONSE_CREATED, response.code());

        String id = ((JsonObject) response.body()).get("id").getAsString();
        String desiredField = "email_" + id;
        String desiredValue = InputConstants.INVALID_EMAIL;
        response = api.updateField(desiredField, desiredValue).execute();
        Assert.assertEquals(Codes.RESPONSE_BAD_REQUEST, response.code());
        response = api.getSingleAccount(id).execute();
        Assert.assertEquals(Codes.RESPONSE_OK, response.code());

        String actualValue = ((JsonObject) response.body()).get("email").getAsString();
        Assert.assertNotEquals(desiredValue, actualValue);
    }

    @Test
    public void updateInvalidDateOfBirthFailure() throws IOException {
        AddAccountRequest addAccountRequest = prepareGenericAccountRequest();
        Response response = api.addAccount(addAccountRequest).execute();
        Assert.assertEquals(Codes.RESPONSE_CREATED, response.code());

        String id = ((JsonObject) response.body()).get("id").getAsString();
        String desiredField = "dateOfBirth_" + id;
        String desiredValue = "something wrong";
        response = api.updateField(desiredField, desiredValue).execute();
        Assert.assertEquals(Codes.RESPONSE_BAD_REQUEST, response.code());
        response = api.getSingleAccount(id).execute();
        Assert.assertEquals(Codes.RESPONSE_OK, response.code());

        String unformattedDate = ((JsonObject) response.body()).get("dateOfBirth").getAsString();
        Date date = new Date(Long.parseLong(unformattedDate));
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        format.setTimeZone(TimeZone.getTimeZone(""));

        String actualValue = format.format(date);
        Assert.assertNotEquals(desiredValue, actualValue);
    }

    @Test
    public void updateFirstNameTooLongFailure() throws IOException {
        AddAccountRequest addAccountRequest = prepareGenericAccountRequest();
        Response response = api.addAccount(addAccountRequest).execute();
        Assert.assertEquals(Codes.RESPONSE_CREATED, response.code());

        String id = ((JsonObject) response.body()).get("id").getAsString();
        String desiredField = "firstName_" + id;
        String desiredValue = InputConstants.LONG_NAME_51_CHARS;
        response = api.updateField(desiredField, desiredValue).execute();
        Assert.assertEquals(Codes.RESPONSE_BAD_REQUEST, response.code());
        Assert.assertEquals(Messages.MAX_LENGTH_FIRST_NAME, response.errorBody().string());
        response = api.getSingleAccount(id).execute();
        Assert.assertEquals(Codes.RESPONSE_OK, response.code());

        String actualValue = ((JsonObject) response.body()).get("firstName").getAsString();
        Assert.assertNotEquals(desiredValue, actualValue);
    }

    @Test
    public void updateLastNameTooLongFailure() throws IOException {
        AddAccountRequest addAccountRequest = prepareGenericAccountRequest();
        Response response = api.addAccount(addAccountRequest).execute();
        Assert.assertEquals(Codes.RESPONSE_CREATED, response.code());

        String id = ((JsonObject) response.body()).get("id").getAsString();
        String desiredField = "lastName_" + id;
        String desiredValue = InputConstants.LONG_NAME_51_CHARS;
        response = api.updateField(desiredField, desiredValue).execute();
        Assert.assertEquals(Codes.RESPONSE_BAD_REQUEST, response.code());
        Assert.assertEquals(Messages.MAX_LENGTH_LAST_NAME, response.errorBody().string());
        response = api.getSingleAccount(id).execute();
        Assert.assertEquals(Codes.RESPONSE_OK, response.code());

        String actualValue = ((JsonObject) response.body()).get("lastName").getAsString();
        Assert.assertNotEquals(desiredValue, actualValue);
    }

    @Test
    public void updateEmailTooLongFailure() throws IOException {
        AddAccountRequest addAccountRequest = prepareGenericAccountRequest();
        Response response = api.addAccount(addAccountRequest).execute();
        Assert.assertEquals(Codes.RESPONSE_CREATED, response.code());

        String id = ((JsonObject) response.body()).get("id").getAsString();
        String desiredField = "email_" + id;

        // desiredValue is 256 chars, which raises DatabaseException and shows it to the user
        String desiredValue = InputConstants.LONG_EMAIL_256_CHARS;
        response = api.updateField(desiredField, desiredValue).execute();
        Assert.assertEquals(Codes.RESPONSE_BAD_REQUEST, response.code());
        Assert.assertEquals(Messages.MAX_LENGTH_EMAIL, response.errorBody().string());
        response = api.getSingleAccount(id).execute();
        Assert.assertEquals(Codes.RESPONSE_OK, response.code());

        String actualValue = ((JsonObject) response.body()).get("email").getAsString();
        Assert.assertNotEquals(desiredValue, actualValue);
    }

    @Test
    public void updateDobInTheFutureFailure() throws IOException {
        AddAccountRequest addAccountRequest = prepareGenericAccountRequest();
        Response response = api.addAccount(addAccountRequest).execute();
        Assert.assertEquals(Codes.RESPONSE_CREATED, response.code());

        String id = ((JsonObject) response.body()).get("id").getAsString();
        String desiredField = "dateOfBirth_" + id;
        String desiredValue = Helpers.createFutureDate();
        response = api.updateField(desiredField, desiredValue).execute();
        Assert.assertEquals(Codes.RESPONSE_BAD_REQUEST, response.code());
        Assert.assertEquals(Messages.DOB_CANNOT_BE_IN_THE_FUTURE, response.errorBody().string());
        response = api.getSingleAccount(id).execute();
        Assert.assertEquals(Codes.RESPONSE_OK, response.code());

        String unformattedDate = ((JsonObject) response.body()).get("dateOfBirth").getAsString();
        Date date = new Date(Long.parseLong(unformattedDate));
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        format.setTimeZone(TimeZone.getTimeZone(""));

        String actualValue = format.format(date);
        Assert.assertEquals(desiredValue, actualValue);
    }

    private AddAccountRequest prepareGenericAccountRequest() {
        return new AddAccountRequest(
                Helpers.createEmail(),
                InputConstants.DEFAULT_NAME,
                InputConstants.DEFAULT_NAME,
                Helpers.getEpochTimeNow());
    }
}
