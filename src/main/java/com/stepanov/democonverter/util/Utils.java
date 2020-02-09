package com.stepanov.democonverter.util;

import java.util.Calendar;
import java.util.Date;

public abstract  class Utils {

    public static boolean beforeToday(Date date) {
        if(date == null)
            return false;

        Calendar givenDate = Calendar.getInstance();
        givenDate.setTime(date);

        Calendar now = Calendar.getInstance();
        now.set(Calendar.HOUR, 0);

        return givenDate.before(now);
    }
}