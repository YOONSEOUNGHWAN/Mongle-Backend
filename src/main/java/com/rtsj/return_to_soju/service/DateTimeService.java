package com.rtsj.return_to_soju.service;


import com.rtsj.return_to_soju.exception.InvalidWeekException;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

@Service
public class DateTimeService {

    public LocalDate convertStringToLocalDateWithDay(String stringDate) throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy년 MM월 dd일 a HH:mm", Locale.KOREAN);
        Date parse = df.parse(stringDate);
        LocalDate localDate = new java.sql.Date(parse.getTime()).toLocalDate();
        return localDate;
    }

    public LocalDate convertKoreanStringToLocalDate(String stringDate) throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy년 MM월 dd일", Locale.KOREAN);
        Date parse = df.parse(stringDate);
        LocalDate localDate = new java.sql.Date(parse.getTime()).toLocalDate();
        return localDate;
    }


    public LocalDate createLocalDateWithString(String year, String month, String day) {
        String date = year + "-" + month + "-" + day;
        return LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
    }

    public String convertStringToLocalDateStringKorean(String data) throws ParseException {
        SimpleDateFormat dtFormat = new SimpleDateFormat("yyyyMMdd", Locale.KOREAN);
        SimpleDateFormat newDtFormat = new SimpleDateFormat("yyyy년 M월 d일", Locale.KOREAN);
        Date parse = dtFormat.parse(data);
        String format = newDtFormat.format(parse);
        return format;
    }

    public LocalDate convertStringToLocalDate(String data) throws ParseException {
        SimpleDateFormat dtFormat = new SimpleDateFormat("yyyyMMdd", Locale.KOREAN);
        Date parse = dtFormat.parse(data);
        LocalDate localDate = new java.sql.Date(parse.getTime()).toLocalDate();
        return localDate;
    }

    public int[] getStartEndDay(int year, int month, int target) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);

        int[] ret = new int[2];

        for (int week = 1; week < cal.getMaximum(Calendar.WEEK_OF_MONTH); week++) {

            cal.set(Calendar.WEEK_OF_MONTH, week);
            cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);

            int startDay = cal.get(Calendar.DAY_OF_MONTH);
            cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
            int endDay = cal.get(Calendar.DAY_OF_MONTH);

            if (week == 1 && startDay >= 7) {
                startDay = 1;
            }


            if (week == cal.getMaximum(Calendar.WEEK_OF_MONTH) - 1 && endDay <= 7) {
                cal.add(Calendar.MONTH,-1);
                endDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
            }

            if (target == week) {
                ret[0] = startDay;
                ret[1] = endDay;
                return ret;
            }

        }

        throw new InvalidWeekException("적절하지 않은 주가 입력되었습니다. 최승용에게 연락 바랍니다");
    }
}
