package com.attendance.xuanf.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 功能描述：
 *
 * @author 陈铉锋
 * @date 2021/7/30
 */
public class TimeConsts {
    private static Date clockInTime;
    private static Date clockInOffTime;
    private static Date firstTime;
    private static long delay;
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static Date getFirstTime() {
        return firstTime;
    }

    public static long getDelay() {
        return delay;
    }

    static {

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);

        cal.set(Calendar.HOUR_OF_DAY, 9);
        clockInTime = cal.getTime();

        cal.set(Calendar.HOUR_OF_DAY, 18);
        clockInOffTime = cal.getTime();

        Calendar cal2 = Calendar.getInstance();
        cal2.set(Calendar.HOUR_OF_DAY, 03);
        cal2.set(Calendar.MINUTE, 13);
        cal2.set(Calendar.SECOND, 40);
        firstTime = cal2.getTime();

        Calendar cal3 = Calendar.getInstance();
//            cal.set(Calendar.HOUR_OF_DAY,24);
        cal3.set(Calendar.HOUR_OF_DAY, 24);
        delay = cal3.getTime().getTime();

    }

    public static Date getClockInTime(){
        return clockInTime;
    }

    public static Date getClockInOffTime() throws ParseException {
        return clockInOffTime;
    }
}
