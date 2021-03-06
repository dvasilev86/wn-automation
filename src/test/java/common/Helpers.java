package common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
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

    public static String createEmail() {
        return Config.DEFAULT_PREFIX + randomInput() + Config.DUMMY_EMAIL_PROVIDER;
    }

    public static long getEpochTimeNow() {
        Instant instant = Instant.now();
        return instant.toEpochMilli();

    }

    public static long dateToEpoch(String desiredValue) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat(Config.DEFAULT_DATE_FORMAT);
        Date date = df.parse(desiredValue);
        return date.getTime();
    }

}
