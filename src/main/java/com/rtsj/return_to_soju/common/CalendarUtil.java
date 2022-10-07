package com.rtsj.return_to_soju.common;

import com.rtsj.return_to_soju.exception.InvalidWeekException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;

@Component
public class CalendarUtil {

    /**
     * 해당 년, 월의 마지막 날 반환
     */
    public int getMonthEndDay(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);

        return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    }


    /**
     * 년, 월, 찾고자 하는 주(target)을 입력받아 그 주의 시작일과 끝나는 일을 반환한다.
     * ret[0] : 시작일
     * ret[1] : 끝나는 일
     */
    public int[] getWeekStartEndDay(int year, int month, int target) {
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

    public static LocalDate[] findWeekWithYearAndMonth(int year, int month, int target) {
        Calendar cal = Calendar.getInstance();

        LocalDate[] dates = new LocalDate[2];

        int overWeek = 0;
        for (int week = 1; week < cal.getMaximum(Calendar.WEEK_OF_MONTH); week++) {
            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.MONTH, month - 1);
            cal.set(Calendar.WEEK_OF_MONTH, week);

            cal.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);

            int thursDayMonth = cal.get(Calendar.MONTH) + 1;
            if (thursDayMonth < month) {
                overWeek++;
                continue;
            } else if (thursDayMonth > month) {
                continue;
            }

            if ((week - overWeek) == target) {
                cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                LocalDate startDate = LocalDate.ofInstant(cal.toInstant(), ZoneId.systemDefault());

                cal.add(Calendar.WEEK_OF_MONTH, 1);
                cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
                LocalDate endDate = LocalDate.ofInstant(cal.toInstant(), ZoneId.systemDefault());

                dates[0] = startDate;
                dates[1] = endDate;

                return dates;
            }
        }

        throw new InvalidWeekException("적절하지 않은 주가 입력되었습니다. 최승용에게 연락 바랍니다");
    }

//    public boolean isCorrectWeek(int year, int month, int target) {
//
//    }


}
