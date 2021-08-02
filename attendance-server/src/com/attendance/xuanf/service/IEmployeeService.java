package com.attendance.xuanf.service;

import com.attendance.xuanf.domain.Employee;

import java.text.ParseException;
import java.util.List;

public interface IEmployeeService {
    Employee login(Employee employee);

    Object clockIn(Employee employee);

    Object clockOff(Employee employee);

    Object registerEmpl(Employee employee);

    Object addAttendance() throws ParseException;

    List<?> findAttendacnceStatus(Employee employee);
}
