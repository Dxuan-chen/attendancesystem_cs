package com.attendance.xuanf.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 功能描述：
 *
 * @author 陈铉锋
 * @date 2021/7/30
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
