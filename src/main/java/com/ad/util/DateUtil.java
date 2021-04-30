package com.ad.util;

//import com.shl.exam.models.TimeDetails;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@Component
public class DateUtil {
    public Date getExamDate(String sessionDetail) {
        DateFormat formatterIST = new SimpleDateFormat(PATTERN_1);
        formatterIST.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata")); // better than using IST
        System.out.println(sessionDetail);
        Date date = null;
        try {
            date = formatterIST.parse(sessionDetail);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(date);
        return date;
    }

    public Date getCurrentIndiaTime(){
        SimpleDateFormat formatterIST = new SimpleDateFormat(PATTERN_1);
        formatterIST.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata")); // better than using IST
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

    public long getTimeLeft(long examDurationinMinute, Date date) {
        long t1=date.getTime();
        long t2=getCurrentIndiaTime().getTime();
        long timeDiff=(t1-t2);
        long timeDiffM=timeDiff / (60 * 1000);
        return (timeDiffM+examDurationinMinute);
    }

    /*public TimeDetails getTimeDetails(String strTime){
        String sessionDetails[] = strTime.split("-");
        long examDurationinMinute=Integer.valueOf(sessionDetails[1]);

        Date date = getExamDate(sessionDetails[0]);
        long timeLeft = getTimeLeft(examDurationinMinute, date);

        TimeDetails timeDetails=new TimeDetails();
        timeDetails.setStartTime(date.toString());
        timeDetails.setDuration(sessionDetails[1]+"min");
        timeDetails.setLeftTime(timeLeft+"");
        timeDetails.setStatus(getStauts(timeLeft,examDurationinMinute));
        return timeDetails;
    }*/

    public boolean isCurrentTime(String time,long examDurationinMinute){
        Date date = getExamDate(time);
        long timeLeft = getTimeLeft(examDurationinMinute, date);
        return (examDurationinMinute>=timeLeft && timeLeft>2);
    }

    public int getStauts(long timeLeft,long examDurationinMinute){
        if(timeLeft>examDurationinMinute){
            return 1;
        }else if(examDurationinMinute>=timeLeft && timeLeft>2){
            return 0;
        }else{
            return  -1;
        }
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
