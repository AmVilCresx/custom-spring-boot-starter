package pers.under2hump.springboot.configure.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @description 日期处理类
 *
 * @author AmVilCresx
 */
public class DateUtils {

    public final static String DEFAULT_PATTERN = "yyyy-MM-dd hh:mm:ss";

    public final static String YYYY_MM_DD = "yyyy-MM-dd";

    public static final ThreadLocal<SimpleDateFormat> DATE_FORMAT = ThreadLocal.withInitial(() -> new SimpleDateFormat(DEFAULT_PATTERN, Locale.CHINA));


    public static Date parseWithPattern(String strDate, String pattern) throws ParseException {
        SimpleDateFormat dateFormat = DATE_FORMAT.get();
        dateFormat.applyPattern(pattern);
        return dateFormat.parse(strDate);
    }
}
