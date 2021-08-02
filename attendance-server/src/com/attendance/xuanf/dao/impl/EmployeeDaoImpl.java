package com.attendance.xuanf.dao.impl;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.attendance.xuanf.dao.DaoFactory;
import com.attendance.xuanf.dao.IEmployeeDao;
import com.attendance.xuanf.domain.ClockInfo;
import com.attendance.xuanf.domain.Employee;
import com.attendance.xuanf.utils.DbUtils;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.*;
import java.util.Date;

/**
 * 功能描述：
 *
 * @author 陈铉锋
 * @date 2021/7/29
 */
public class EmployeeDaoImpl implements IEmployeeDao {
    static DataSource ds = DbUtils.ds;

    @Override
    public Employee findEmployeeByLoginAndPassword(Employee employee) {
        Connection con = null;
        PreparedStatement pre = null;
        try {
            con = ds.getConnection();
            String sql = "select * from t_employee where login_name=? and pass_word=?;";
            pre = con.prepareStatement(sql);
            pre.setString(1,employee.getLoginName());
            pre.setString(2,employee.getPassword());
            ResultSet rs = pre.executeQuery();
            while (rs.next())
            {
                Employee outEmployee=new Employee();

                outEmployee.setEmployeeId(rs.getInt(1));
                outEmployee.setEmployeeNo(rs.getString(2));
                outEmployee.setEmployeeName(rs.getString(3));
                outEmployee.setLoginName(rs.getString(4));
                outEmployee.setPassword(rs.getString(5));
                outEmployee.setJob(rs.getString(6));
                outEmployee.setHiredate(rs.getDate(7));
                outEmployee.setSal(rs.getDouble(8));
                outEmployee.setRole(rs.getInt(9));

                return outEmployee;

            }
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
        return null;
    }

    @Override
    public Object insertEmployeeByEntity(Employee employee) {
        Connection con = null;
        int count = 0;
        PreparedStatement pre = null;
        try {
            con = ds.getConnection();
            String sql = "insert into t_employee (employee_no,employee_name,login_name,pass_word,job,hiredate,sal,role) values(?,?,?,?,?,?,?,?);";
            pre = con.prepareStatement(sql);

            pre.setString(1,employee.getEmployeeNo());
            pre.setString(2,employee.getEmployeeName());
            pre.setString(3,employee.getLoginName());
            pre.setString(4,employee.getPassword());
            pre.setString(5,employee.getJob());
            pre.setDate(6,employee.getHiredate());
            pre.setDouble(7,employee.getSal());
            pre.setInt(8,employee.getRole());

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
    public List<Employee> findAllEmployee() {
        Connection con = null;
        Statement stmt = null;
        List<Employee> employeeList = new ArrayList<>();

        try {

            con = ds.getConnection();
            String sql = "select * from t_employee where role=0;";
            stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next())
            {
                Employee outEmployee=new Employee();

                outEmployee.setEmployeeId(rs.getInt(1));
                outEmployee.setEmployeeNo(rs.getString(2));
                outEmployee.setEmployeeName(rs.getString(3));
                outEmployee.setLoginName(rs.getString(4));
                outEmployee.setPassword(rs.getString(5));
                outEmployee.setJob(rs.getString(6));
                outEmployee.setHiredate(rs.getDate(7));
                outEmployee.setSal(rs.getDouble(8));
                outEmployee.setRole(rs.getInt(9));
                employeeList.add(outEmployee);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                stmt.close();
                con.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                return employeeList;
            }
        }

    }


}
