package com.attendance.xuanf.dao.impl;

import com.attendance.xuanf.domain.AttendanceStatusDto;
import com.attendance.xuanf.domain.AttendanceStatus;
import com.attendance.xuanf.domain.Employee;

import java.sql.Date;
import java.util.List;

public interface IAttendanceStatusDao {
    int insert(String employeeNo, Date date, int status);

    List<AttendanceStatus> findAll();

    List<AttendanceStatus> findByEmployeeNo(Employee employee);

    List<AttendanceStatusDto> findAllStatus();
}
