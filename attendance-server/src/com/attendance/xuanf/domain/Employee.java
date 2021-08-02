package com.attendance.xuanf.domain;

import java.io.Serializable;
import java.sql.Date;

/**
 * 功能描述：
 * 员工信息类
 */
public class Employee implements Serializable {
    static final long serialVersionUID = 66666;
    private int employeeId;
    private String employeeNo;//工号
    private String employeeName;//姓名
    private String loginName;//用户名
    private String password;//密码
    private String job;//岗位
    private Date hiredate;//入职时间
    private double sal;//薪资
    private int role;//0为普通员工 1为管理员

    public Employee() {
    }

    public Employee(int employeeId, String employeeNo, String employeeName, String loginName, String password, String job, Date hiredate, double sal, int role) {
        this.employeeId = employeeId;
        this.employeeNo = employeeNo;
        this.employeeName = employeeName;
        this.loginName = loginName;
        this.password = password;
        this.job = job;
        this.hiredate = hiredate;
        this.sal = sal;
        this.role = role;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
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

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public Date getHiredate() {
        return hiredate;
    }

    public void setHiredate(Date hiredate) {
        this.hiredate = hiredate;
    }

    public double getSal() {
        return sal;
    }

    public void setSal(double sal) {
        this.sal = sal;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeId=" + employeeId +
                ", employeeNo='" + employeeNo + '\'' +
                ", employeeName='" + employeeName + '\'' +
                ", loginName='" + loginName + '\'' +
                ", password='" + password + '\'' +
                ", job='" + job + '\'' +
                ", hiredate=" + hiredate +
                ", sal=" + sal +
                ", role=" + role +
                '}';
    }
}
