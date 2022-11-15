package io.panshi.discovery.core.imp.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class TimeUtils {

    private final static DateTimeFormatter DEFAULT_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(
            "yyyy-MM-dd HH:mm:ss", Locale.CHINA);


    public static String getNowTime(){
        return LocalDateTime.now().format(DEFAULT_DATE_TIME_FORMATTER);
    }

}
