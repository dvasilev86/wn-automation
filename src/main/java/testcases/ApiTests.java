package testcases;

import base.BaseApiTest;
import com.google.gson.JsonObject;
import common.*;
import org.junit.Assert;
import org.junit.Test;
import rest.AddAccountRequest;
import retrofit2.Response;

import java.io.IOException;

/**
 * @author by dvasilev on 15-Sep-17.
 */
public class ApiTests extends BaseApiTest {

    @Test
    public void getAllAccountsSuccess() throws IOException {
        Response response = api.getAllAccounts().execute();
        Assert.assertNotEquals("server error!", Codes.RESPONSE_SERVER_ERROR, response.code());
        Assert.assertEquals("response code is not OK", Codes.RESPONSE_OK, response.code());
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
    public void addAccountSuccess() throws IOException {
        AddAccountRequest addAccountRequest = new AddAccountRequest(
                Helpers.createEmail(),
                InputConstants.DEFAULT_NAME,
                InputConstants.DEFAULT_NAME,
                Helpers.getEpochTimeNow());
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
    public void addAccountDigitsInNameFailure() throws IOException {
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
        AddAccountRequest addAccountRequest = new AddAccountRequest(
                Helpers.createEmail(),
                InputConstants.DEFAULT_NAME,
                InputConstants.DEFAULT_NAME,
                Helpers.getEpochTimeNow());
        Response response = api.addAccount(addAccountRequest).execute();
        Assert.assertEquals(Codes.RESPONSE_CREATED, response.code());
        String id = ((JsonObject) response.body()).get("id").getAsString();
        response = api.deleteSingleAccount(id).execute();
        Assert.assertEquals(Codes.RESPONSE_OK, response.code());
        response = api.getSingleAccount(id).execute();
        Assert.assertEquals(Codes.RESPONSE_NOT_FOUND, response.code());
    }
}
