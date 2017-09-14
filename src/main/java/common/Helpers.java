package common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author by dvasilev on 14-Sep-17.
 */
public class Helpers {

    public static String randomInput() {
        return UUID.randomUUID().toString().substring(0, 6);
    }

    public static String createFutureDate() {
        Date today = new Date();
        Date tomorrow = new Date(today.getTime() + (1000 * 60 * 60 * 24));
        return new SimpleDateFormat(Config.DEFAULT_DATE_FORMAT).format(tomorrow);
    }

}
