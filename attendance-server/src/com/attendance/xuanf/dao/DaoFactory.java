package com.attendance.xuanf.dao;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.attendance.xuanf.dao.impl.*;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 功能描述：
 * 定义一个Dao工厂
 * 作用：产生dao对象，实现解耦
 */
public class DaoFactory {

    public static IEmployeeDao createEmplyeeDao(){
        return new EmployeeDaoImpl();
    }

    public static IClockInfoDao createClockInfoDao(){
        return new ClockInfoDaoImpl();
    }

    public static IWorkDateDao createWorkDateDao(){
        return new WorkDateDaoImpl();
    }

    public static IAttendanceStatusDao createAttendanceStatusDao(){
        return new AttendanceStatusDaoImpl();
    }
}
