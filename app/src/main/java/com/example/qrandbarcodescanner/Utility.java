package com.example.qrandbarcodescanner;

import java.text.SimpleDateFormat;
import java.util.Calendar;

 public class Utility {
   static String  getMonthFromNumber(int number){
        switch (number) {
            case 1:
                return "Jan";
            case 2:
                return "Feb";
            case 3:
                return "Mar";
            case 4:
                return "Apr";
            case 5:
                return "May";
            case 6:
                return "June";
            case 7:
                return "July";
            case 8:
                return "Aug";
            case 9:
                return "Sep";
            case 10:
                return "Oct";
            case 11:
                return "Nov";
            case 12:
                return "Dec";
            default:
                return "Error";
        }
    }
    public static String getDataTimeFromLong(long timeMilis){
        Calendar calendar=Calendar.getInstance();
        calendar.setTimeInMillis(timeMilis);
        int month=calendar.get(Calendar.MONTH);
        int date=calendar.get(Calendar.DATE);
        String monthStr=getMonthFromNumber(month);
        String dayStr=String.valueOf(date);
        String dateString=monthStr+" "+dayStr;
        String timeString= new SimpleDateFormat("hh.mm aa").format(calendar.getTime());
        return dateString+" , "+timeString;
    }
}
