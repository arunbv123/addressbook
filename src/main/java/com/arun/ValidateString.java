package com.arun;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Arun on 3/3/2017.
 */
public class ValidateString {

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private static final String PHONE_PATTERN = "^(?:(?:\\+?1\\s*(?:[.-]\\s*)?)?(?:\\(\\s*([2-9]1[02-9]|[2-9][02-8]1|[2-9][02-8][02-9])\\s*\\)|([2-9]1[02-9]|[2-9][02-8]1|[2-9][02-8][02-9]))\\s*(?:[.-]\\s*)?)?([2-9]1[02-9]|[2-9][02-9]1|[2-9][02-9]{2})\\s*(?:[.-]\\s*)?([0-9]{4})(?:\\s*(?:#|x\\.?|ext\\.?|extension)\\s*(\\d+))?$";

    private static Pattern emailPattern;
    private static Matcher emailMatcher;

    private static Pattern phonePattern;
    private static Matcher phoneMatcher;

    static {
        emailPattern = Pattern.compile(EMAIL_PATTERN);
        phonePattern = Pattern.compile(PHONE_PATTERN);
    }


    /**
     * Function that validates email address
     * @param email -  Email string that has to be validated
     * @return - True if valid email id False if invalid
     */
    public static boolean emailValidate(final String email) {

        emailMatcher = emailPattern.matcher(email);
        return emailMatcher.matches();

    }

    /**
     *Function that validates phone number
     * @param phone - Phone string that has be validated
     * @return - True if valid phone number id False if invalid
     */
    public static boolean phoneValidate(final String phone) {

        phoneMatcher = phonePattern.matcher(phone);
        return phoneMatcher.matches();

    }
}
