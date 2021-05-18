package com.ad.util;

import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@Component
public class DateUtil {

    public Date getCurrentIndiaTime(){
        SimpleDateFormat formatterIST = new SimpleDateFormat(PATTERN_1);
        formatterIST.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata")); //better than using IST
        Date indiaTime=null;
        try {
            String newDate=formatterIST.format(new Date());
            System.out.println("Current Time: "+ newDate);
            indiaTime = formatterIST.parse(newDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(indiaTime);
        return indiaTime;
    }

    public String getCurrentIndiaTimeInString() {
        Date currentDate = getCurrentIndiaTime();

        SimpleDateFormat formatterIST = new SimpleDateFormat(PATTERN_1);
        return formatterIST.format(currentDate);
    }

    public Date parseStringToDate(String dateInString) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(PATTERN_2);
            formatter.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata")); // better than using IST
            Date date = null;
            try {
                date = formatter.parse(dateInString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return date;
        } catch (Exception e) {
            return null;
        }
    }

    private static final String PATTERN_1 = "yyyyMMddhhmma";
    private static final String PATTERN_2 = "dd/MM/yyyy hh:mma";
}
