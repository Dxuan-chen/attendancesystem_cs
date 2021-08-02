package com.attendance.xuanf.dao.impl;

import com.attendance.xuanf.domain.AttendanceStatus;
import com.attendance.xuanf.domain.AttendanceStatusDto;
import com.attendance.xuanf.domain.ClockInfo;
import com.attendance.xuanf.domain.Employee;
import com.attendance.xuanf.utils.DbUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 功能描述：
 *
 * @author 陈铉锋
 * @date 2021/7/31
 */
public class AttendanceStatusDaoImpl implements IAttendanceStatusDao {
    static DataSource ds = DbUtils.ds;

    @Override
    public int insert(String employeeNo, Date date, int status) {

        Connection con = null;
        PreparedStatement pre = null;
        int count = 0;
        try {
            con = ds.getConnection();

            String sql ="insert into t_attendance_status(employee_no,attendance_date,status) values(?,?,?);";
            pre = con.prepareStatement(sql);
            pre.setString(1,employeeNo);
            pre.setDate(2,date);
            pre.setInt(3,status);
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
    public List<AttendanceStatus> findAll() {
        Connection con = null;
        Statement stmt = null;
        List<AttendanceStatus> attendanceStatusList = new ArrayList<>();

        try {

            con = ds.getConnection();
            String sql = "select * from t_attendance_status;";
            stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next())
            {
                AttendanceStatus attendanceStatus = new AttendanceStatus();
                attendanceStatus.setAttendanceStatusId(rs.getInt(1));
                attendanceStatus.setEmployeeNo(rs.getString(2));
                attendanceStatus.setAttendanceDate(rs.getDate(3));
                attendanceStatus.setStatus(rs.getInt(4));

                attendanceStatusList.add(attendanceStatus);
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
                return attendanceStatusList;
            }
        }
    }

    @Override
    public List<AttendanceStatus> findByEmployeeNo(Employee employee) {
        Connection con = null;
        PreparedStatement pre = null;
        List<AttendanceStatus> attendanceStatusList = new ArrayList<>();
        try {
            con = ds.getConnection();
            String sql = "select * from t_attendance_status where employee_no=?;";
            pre = con.prepareStatement(sql);
            pre.setString(1,employee.getEmployeeNo());
            ResultSet rs = pre.executeQuery();
            while (rs.next())
            {
                AttendanceStatus attendanceStatus = new AttendanceStatus();

                attendanceStatus.setAttendanceStatusId(rs.getInt(1));
                attendanceStatus.setEmployeeNo(rs.getString(2));
                attendanceStatus.setAttendanceDate(rs.getDate(3));
                attendanceStatus.setStatus(rs.getInt(4));
                attendanceStatusList.add(attendanceStatus);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                pre.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                return attendanceStatusList;
            }
        }
    }

    @Override
    public List<AttendanceStatusDto> findAllStatus() {
        Connection con = null;
        Statement stmt = null;
        List<AttendanceStatusDto> attendanceStatusDtoList = new ArrayList<>();

        try {

            con = ds.getConnection();
            String sql = "select employee_no,employee_name,date_format(t.work_date, '%Y-%m') as work_date,status,count(*) as status_count from \n" +
                    "\t\t\n" +
                    "\t(select * from(\n" +
                    "\tselect a.employee_no,a.employee_name,b.work_date,ifnull(c.status,3) status from t_employee a left join t_work_date b on hiredate<=b.work_date and b.work_date<DATE_SUB(curdate(),interval 0 day) \n" +
                    "left join t_attendance_status c on a.employee_no=c.employee_no and b.work_date=c.attendance_date\n" +
                    "\t)\tv) t group by t.employee_no,t.employee_name,date_format(t.work_date, '%Y-%m'),status;";
            stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next())
            {
                AttendanceStatusDto attendanceStatusDto = new AttendanceStatusDto();

                attendanceStatusDto.setEmployeeNo(rs.getString(1));
                attendanceStatusDto.setEmployeeName(rs.getString(2));
//                System.out.println(rs.getString(3));
                attendanceStatusDto.setWorkDate(rs.getString(3));
                attendanceStatusDto.setStatus(rs.getInt(4));
                attendanceStatusDto.setCount(rs.getInt(5));

                attendanceStatusDtoList.add(attendanceStatusDto);
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
                return attendanceStatusDtoList;
            }
        }
    }
}
