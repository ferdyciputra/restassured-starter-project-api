package api.Helpers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class TimeHelper {
    public static String getDateTimeNow() {
        SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyy_HHmmss");
        formatter.setTimeZone(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
        Date date = new Date();

        return formatter.format(date);
    }

    public static String getDateNow() {
        SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyy");
        formatter.setTimeZone(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
        Date date = new Date();

        return formatter.format(date);
    }

    public static String getDateNow(String pattern) {
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        formatter.setTimeZone(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
        Date date = new Date();

        return formatter.format(date);
    }
}
