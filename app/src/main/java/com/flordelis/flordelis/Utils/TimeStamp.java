package com.flordelis.flordelis.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Sala on 25/01/2018.
 */

public class TimeStamp {

    public static long getTimestamp(){
        return System.currentTimeMillis()/1000;
    }

    public static String getDateCurrentTimeZone(long timestamp) {
        long tsp = timestamp * 1000;
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String dateString = formatter.format(new Date(tsp));
        return dateString;
    }
}
