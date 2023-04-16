package com.sport.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static DateFormat timeFormat = new SimpleDateFormat("MM-dd HH:mm:ss");

    public static String getDate(){
        return dateFormat.format(new Date());
    }

    public static String format(Long timestamp){
        return dateFormat.format(new Date(timestamp));
    }

    public static String format(Long start, Long end){
        return timeFormat.format(new Date(start)) + "    " + timeFormat.format(new Date(end));
    }
}
