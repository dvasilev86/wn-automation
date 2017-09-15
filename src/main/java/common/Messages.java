package common;

/**
 * @author by dvasilev on 14-Sep-17.
 */
public class Messages {
    public static final String FAILED_TO_ADD_ACCOUNT = "Failed to add account.";
    public static final String NO_RESULTS_FOUND = "No matching records found";

    //the following message must be changed when the bug is fixed
    public static final String DOB_CANNOT_BE_IN_THE_FUTURE = "dob cannot be in the future";

    public static final String MISSING_FIRST_NAME = "\"Missing property [firstName]\"";
    public static final String MISSING_LAST_NAME = "\"Missing property [lastName]\"";
    public static final String MISSING_EMAIL = "\"Missing property [email]\"";
    public static final String MISSING_DOB = "\"Missing property [dateOfBirth]\"";
    public static final String WORDS_ONLY_ALLOWED_IN_NAME = "\"Words only allowed for property firstName\"";
    public static final String INVALID_EMAIL = "\"Email [invalid@email] is not valid\""; //depends on input constant

}
