package com.attendance.xuanf.dao.impl;

import com.attendance.xuanf.common.ResponseStatus;
import com.attendance.xuanf.dao.IClockInfoDao;
import com.attendance.xuanf.domain.ClockInfo;
import com.attendance.xuanf.domain.Employee;
import com.attendance.xuanf.utils.DbUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

/**
 * 功能描述：
 *
 * @author 陈铉锋
 * @date 2021/7/31
 */
public class ClockInfoDaoImpl implements IClockInfoDao {

    static DataSource ds = DbUtils.ds;

    @Override
    public Object updateClockInfoIn(ClockInfo clockInfo) {
        Connection con = null;
        PreparedStatement pre = null;
        int count = 0;
        try {
            con = ds.getConnection();

            String sql ="insert into t_clock_info(employee_no,clock_in_time,clock_date) values(?,?,?);";
            pre = con.prepareStatement(sql);
            pre.setString(1,clockInfo.getEmployeeNo());
            pre.setTimestamp(2,new Timestamp(clockInfo.getClockInTime().getTime()));
            pre.setDate(3,clockInfo.getClockDate());
            count = pre.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                pre.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return count;
    }

    @Override
    public Object findClockInfoByClock(ClockInfo clockInfo) {
        Connection con = null;
        PreparedStatement pre = null;
        ResultSet rs = null;

        ClockInfo clockInfo2 = new ClockInfo();

        try {
            con = ds.getConnection();

            String sql = "select * from t_clock_info where employee_no=? and clock_date=?;";
            pre = con.prepareStatement(sql);
            pre.setString(1, clockInfo.getEmployeeNo());
            pre.setDate(2, clockInfo.getClockDate());
            rs = pre.executeQuery();
            if(rs.next() == false){
                return null;
            }

            clockInfo2.setEmployeeNo(rs.getString("employee_no"));
            clockInfo2.setClockInTime(rs.getTimestamp("clock_in_time"));
            clockInfo2.setClockOffTime(rs.getTimestamp("clock_off_time"));
            clockInfo2.setClockDate(rs.getDate("clock_date"));


        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                rs.close();
                pre.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        return clockInfo2;
    }


    @Override
    public Object updateClockInfoOff(ClockInfo clockInfo) {
        Connection con = null;
        PreparedStatement pre = null;
        int count = 0;
        try {
            con = ds.getConnection();

            String sql = "update t_clock_info set clock_off_time=? where employee_no=? and clock_date=?;";
            pre = con.prepareStatement(sql);
            pre.setTimestamp(1,new Timestamp(clockInfo.getClockOffTime().getTime()));
            pre.setString(2,clockInfo.getEmployeeNo());
            pre.setDate(3,new java.sql.Date(clockInfo.getClockDate().getTime()));
            count = pre.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                pre.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return count;
    }

    @Override
    public ClockInfo findByEmployeeNoAndDate(String employeeNo ,java.sql.Date date) {
        Connection con = null;
        PreparedStatement pre = null;
        ResultSet rs = null;

        ClockInfo clockInfo2 = new ClockInfo();

        try {
            con = ds.getConnection();

            String sql = "select * from t_clock_info where employee_no=? and clock_date=?;";
            pre = con.prepareStatement(sql);
            pre.setString(1, employeeNo);
            pre.setDate(2, date);
            rs = pre.executeQuery();
            if(rs.next() == false){
                return null;
            }

            clockInfo2.setEmployeeNo(rs.getString("employee_no"));
            clockInfo2.setClockInTime(rs.getTimestamp("clock_in_time"));
            clockInfo2.setClockOffTime(rs.getTimestamp("clock_off_time"));
            clockInfo2.setClockDate(rs.getDate("clock_date"));


        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                rs.close();
                pre.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        return clockInfo2;



    }

}
