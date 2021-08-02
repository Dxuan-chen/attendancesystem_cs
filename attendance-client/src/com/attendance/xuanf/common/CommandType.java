package com.attendance.xuanf.common;

public class CommandType {

    //注册员工的指令类型
    public final static String REGISTER_EMP_REQUEST="1";
    //注册员工的响应指令类型
    public final static String REGISTER_EMP_RESPONSE="2";
    //登录的指令类型
    public final static String EMP_LOGIN="3";
    //登录的响应指令类型
    public final static String EMP_LOGIN_RESPONSE="4";
    //上班打卡的指令类型
    public final static String CLOCK_IN_TIME="5";
    //上班打卡的响应指令类型
    public final static String CLOCK_IN_TIME_RESPONSE="7";
    //下班打卡的指令类型
    public final static String CLOCK_OFF_TIME="6";
    //下班打卡的响应指令类型
    public final static String CLOCK_OFF_TIME_RESPONSE="8";
    //查询考勤信息的指令类型
    public final static String ATTENDANCE_REQUEST="9";
    //查询考勤信息的响应指令类型
    public final static String ATTENDANCE_RESPONSE="10";
    //退出系统
    public final static String SYSTEMEXIT_REQUEST="11";

}
