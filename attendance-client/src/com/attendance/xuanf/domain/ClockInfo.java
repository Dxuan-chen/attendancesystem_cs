package com.attendance.xuanf.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 功能描述：
 * 打卡详情类
 */
public class ClockInfo implements Serializable {
    private int clockId;
    private String employeeNo;//工号
    private Date clockInTime;//上班打卡时间
    private Date clockOffTime;//下班打卡时间
    private java.sql.Date clockDate;//打卡日期

    public ClockInfo() {
    }

    public ClockInfo(int clockId, String employeeNo, Date clockInTime, Date clockOffTime, java.sql.Date clockDate) {
        this.clockId = clockId;
        this.employeeNo = employeeNo;
        this.clockInTime = clockInTime;
        this.clockOffTime = clockOffTime;
        this.clockDate = clockDate;
    }

    public int getClockId() {
        return clockId;
    }

    public void setClockId(int clockId) {
        this.clockId = clockId;
    }

    public String getEmployeeNo() {
        return employeeNo;
    }

    public void setEmployeeNo(String employeeNo) {
        this.employeeNo = employeeNo;
    }

    public Date getClockInTime() {
        return clockInTime;
    }

    public void setClockInTime(Date clockInTime) {
        this.clockInTime = clockInTime;
    }

    public Date getClockOffTime() {
        return clockOffTime;
    }

    public void setClockOffTime(Date clockOffTime) {
        this.clockOffTime = clockOffTime;
    }

    public java.sql.Date getClockDate() {
        return clockDate;
    }

    public void setClockDate(java.sql.Date clockDate) {
        this.clockDate = clockDate;
    }

    @Override
    public String toString() {
        return "ClockInfo{" +
                "clockId=" + clockId +
                ", employeeNo='" + employeeNo + '\'' +
                ", clockInTime=" + clockInTime +
                ", clockOffTime=" + clockOffTime +
                ", clockDate=" + clockDate +
                '}';
    }
}