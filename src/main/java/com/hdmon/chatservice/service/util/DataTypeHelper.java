package com.hdmon.chatservice.service.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by UserName on 6/5/2018.
 */
public class DataTypeHelper {

    /*
    * Create on 2018-06-05
    * Convert DateTime To ReportDay
    */
    public static int ConvertDateTimeToReportDay()
    {
        // Create an instance of SimpleDateFormat used for formatting
        // the string representation of date (month/day/year)
        DateFormat df = new SimpleDateFormat("yyyyMMdd");

        // Get the date today using Calendar object.
        Date today = Calendar.getInstance().getTime();

        // Using DateFormat format method we can create a string
        // representation of a date with the defined format.
        String reportDate = df.format(today);

        return Integer.parseInt(reportDate);
    }

    /*
    * Create on 2018-06-06
    * Sub DateTime And Convert To ReportDay
    */
    public static int SubDateTimeAndConvertToReportDay(int amount, int calendarType)
    {
        // Create an instance of SimpleDateFormat used for formatting
        // the string representation of date (month/day/year)
        DateFormat df = new SimpleDateFormat("yyyyMMdd");

        // Get the date today using Calendar object.
        Calendar calendar = Calendar.getInstance();
        calendar.add(calendarType, -amount);
        Date newDay = calendar.getTime();

        // Using DateFormat format method we can create a string
        // representation of a date with the defined format.
        String reportDate = df.format(newDay);

        return Integer.parseInt(reportDate);
    }
}
