package com.attendance.xuanf.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 功能描述：
 * 工作日类
 */
public class WorkDate implements Serializable {
    private int employeeId;
    private Date workDate;

    public WorkDate() {
    }

    public WorkDate(int employeeId, Date workDate) {
        this.employeeId = employeeId;
        this.workDate = workDate;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public Date getWorkDate() {
        return workDate;
    }

    public void setWorkDate(Date workDate) {
        this.workDate = workDate;
    }

    @Override
    public String toString() {
        return "WorkDate{" +
                "employeeId=" + employeeId +
                ", workDate=" + workDate +
                '}';
    }
}
