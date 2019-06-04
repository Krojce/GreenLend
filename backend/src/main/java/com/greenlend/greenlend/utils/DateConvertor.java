package com.greenlend.greenlend.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

public class DateConvertor {

    public static long daysBetween(Date from, Date to) {
        return ChronoUnit.DAYS.between(toLocalDate(from), toLocalDate(to));
    }

    public static LocalDate toLocalDate(Date d) {
        return Instant.ofEpochMilli(d.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static Date millisToDate(Long millis) {
        return Date.from(Instant.EPOCH.plusMillis(millis));
    }

    public static Date yesterday() {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }

}
