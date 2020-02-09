package com.stepanov.democonverter.util;

import java.time.LocalDate;

public class Utils {

    public static boolean afterToday(LocalDate loadDate) {
        if (loadDate == null)
            return false;

        LocalDate newDate = LocalDate.now();

        return newDate.isAfter(loadDate);
    }
}