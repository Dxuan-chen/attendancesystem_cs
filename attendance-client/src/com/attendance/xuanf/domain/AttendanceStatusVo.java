package com.attendance.xuanf.domain;

import java.io.Serializable;
import java.sql.Date;

/**
 * 功能描述：
 *
 * @author 陈铉锋
 * @date 2021/8/2
 */
public class AttendanceStatusVo implements Serializable {
    static final long serialVersionUID = 99999;
    private String employeeNo;
    private String employeeName;
    private String workDate;
    private int normalCount;//正常
    private int lateCount;//迟到
    private int earlyCount;//早退
    private int minerCount;//旷工

    public AttendanceStatusVo() {
    }

    public AttendanceStatusVo(String employeeNo, String employeeName, String workDate, int normalCount, int lateCount, int earlyCount, int minerCount) {
        this.employeeNo = employeeNo;
        this.employeeName = employeeName;
        this.workDate = workDate;
        this.normalCount = normalCount;
        this.lateCount = lateCount;
        this.earlyCount = earlyCount;
        this.minerCount = minerCount;
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

    public void setWorkDate(String workDate) {
        this.workDate = workDate;
    }

    public int getNormalCount() {
        return normalCount;
    }

    public void setNormalCount(int normalCount) {
        this.normalCount = normalCount;
    }

    public int getLateCount() {
        return lateCount;
    }

    public void setLateCount(int lateCount) {
        this.lateCount = lateCount;
    }

    public int getEarlyCount() {
        return earlyCount;
    }

    public void setEarlyCount(int earlyCount) {
        this.earlyCount = earlyCount;
    }

    public int getMinerCount() {
        return minerCount;
    }

    public void setMinerCount(int minerCount) {
        this.minerCount = minerCount;
    }

    @Override
    public String toString() {
        return "AttendanceStatusVo{" +
                "employeeNo='" + employeeNo + '\'' +
                ", employeeName='" + employeeName + '\'' +
                ", workDate=" + workDate +
                ", normalCount=" + normalCount +
                ", lateCount=" + lateCount +
                ", earlyCount=" + earlyCount +
                ", minerCount=" + minerCount +
                '}';
    }
}
