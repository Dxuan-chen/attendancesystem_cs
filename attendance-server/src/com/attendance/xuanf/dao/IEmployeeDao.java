package com.attendance.xuanf.dao;

import com.attendance.xuanf.domain.ClockInfo;
import com.attendance.xuanf.domain.Employee;

import java.util.List;

public interface IEmployeeDao {

    //根据员工的登录名及密码获取员工数据信息
    public Employee findEmployeeByLoginAndPassword(Employee employee);


    Object insertEmployeeByEntity(Employee employee);

    List<Employee> findAllEmployee();
}
