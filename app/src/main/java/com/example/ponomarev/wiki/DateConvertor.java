package com.example.ponomarev.wiki;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConvertor {
    public static String convert(String input){
        Date date;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.ms'Z'")
                    .parse(input);
            String output = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date);
            return output;
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return "Error";
    }
}
