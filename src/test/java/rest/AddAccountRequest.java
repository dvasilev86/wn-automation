package rest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author by dvasilev on 15-Sep-17.
 */
public class AddAccountRequest {
    @Expose
    @SerializedName("email")
    private Object email;
    @Expose
    @SerializedName("firstName")
    private Object firstName;
    @Expose
    @SerializedName("lastName")
    private Object lastName;
    @Expose
    @SerializedName("dateOfBirth")
    private Object dateOfBirth;

    public AddAccountRequest(Object email, Object firstName, Object lastName, Object dateOfBirth) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
    }
}
