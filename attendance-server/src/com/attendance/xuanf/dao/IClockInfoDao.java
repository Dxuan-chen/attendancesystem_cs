package com.attendance.xuanf.dao;

import com.attendance.xuanf.domain.ClockInfo;
import com.attendance.xuanf.domain.Employee;

import java.util.List;

public interface IClockInfoDao {

    Object updateClockInfoIn(ClockInfo clockInfo);

    Object findClockInfoByClock(ClockInfo clockInfo);

    Object updateClockInfoOff(ClockInfo clockInfo);


    ClockInfo findByEmployeeNoAndDate(String employeeNo ,java.sql.Date date);
}
