package com.attendance.xuanf.domain;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * 功能描述：
 *
 * @author 陈铉锋
 * @date 2021/8/2
 */
public class AttendanceStatusDto {
    private String employeeNo;
    private String employeeName;
    private String workDate;
    private int status ;
    private int count;

    public AttendanceStatusDto() {
    }

    public AttendanceStatusDto(String employeeNo, String employeeName, String workDate, int status, int count) {
        this.employeeNo = employeeNo;
        this.employeeName = employeeName;
        this.workDate = workDate;
        this.status = status;
        this.count = count;
    }

    public String getEmployeeNo() {
        return employeeNo;
    }

    public void setEmployeeNo(String employeeNo) {
        this.employeeNo = employeeNo;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getWorkDate() {
        return workDate;
    }

    public void setWorkDate(String workDate) throws ParseException {
        this.workDate = workDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "AttendanceStatusDto{" +
                "employeeNo='" + employeeNo + '\'' +
                ", employeeName='" + employeeName + '\'' +
                ", workDate=" + workDate +
                ", status=" + status +
                ", count=" + count +
                '}';
    }
}
