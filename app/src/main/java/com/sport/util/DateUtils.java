package com.sport.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static String getDate(){
        return dateFormat.format(new Date());
    }
}
