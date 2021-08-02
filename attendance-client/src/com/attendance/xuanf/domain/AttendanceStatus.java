package com.attendance.xuanf.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 功能描述：
 *
 * @author 陈铉锋
 * @date 2021/7/30
 */
public class AttendanceStatus implements Serializable {
    private int attendanceStatusId;
    private String employeeNo;
    private Date attendanceDate;
    private int status;

    public AttendanceStatus() {
    }

    public AttendanceStatus(int attendanceStatusId, String employeeNo, Date attendanceDate, int status) {
        this.attendanceStatusId = attendanceStatusId;
        this.employeeNo = employeeNo;
        this.attendanceDate = attendanceDate;
        this.status = status;
    }

    public int getAttendanceStatusId() {
        return attendanceStatusId;
    }

    public void setAttendanceStatusId(int attendanceStatusId) {
        this.attendanceStatusId = attendanceStatusId;
    }

    public String getEmployeeNo() {
        return employeeNo;
    }

    public void setEmployeeNo(String employeeNo) {
        this.employeeNo = employeeNo;
    }

    public Date getAttendanceDate() {
        return attendanceDate;
    }

    public void setAttendanceDate(Date attendanceDate) {
        this.attendanceDate = attendanceDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "AttendanceStatus{" +
                "attendanceStatusId=" + attendanceStatusId +
                ", employeeNo='" + employeeNo + '\'' +
                ", attendanceDate=" + attendanceDate +
                ", status=" + status +
                '}';
    }
}
