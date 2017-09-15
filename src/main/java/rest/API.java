package rest;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.http.*;

/**
 * @author by dvasilev on 15-Sep-17.
 */
public interface API {

    @GET("user-accounts/accounts")
    Call<JsonArray> getAllAccounts();

    @GET("user-accounts/accounts/{id}")
    Call<JsonObject> getSingleAccount(@Path("id") String id);

    @POST("user-accounts/accounts")
    Call<JsonObject> addAccount(@Body AddAccountRequest body);

    @FormUrlEncoded
    @POST("user-accounts/accounts/update")
    Call<String> updateField(@Field("id") String id,
                             @Field("value") String value);

    @DELETE("user-accounts/accounts/{id}")
    Call<JsonObject> deleteSingleAccount(@Path("id") String id);
}
