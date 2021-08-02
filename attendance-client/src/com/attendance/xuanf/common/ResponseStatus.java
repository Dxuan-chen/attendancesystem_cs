package com.attendance.xuanf.common;

/**
 * 功能描述：
 *
 * @author 陈铉锋
 * @date 2021/7/31
 */
public class ResponseStatus {
    //上班打卡成功
    public final static String CLOCK_IN_SUCCESS="1";
    //上班打卡重复
    public final static String CLOCK_IN_REPEAT="2";
    //上班打卡非工作日
    public final static String CLOCK_IN_NOWORKDAY="3";
    //上班打卡失败
    public final static String CLOCK_IN_FAIL="4";
    //下班打卡成功
    public final static String CLOCK_OFF_SUCCESS="5";
    //下班打卡重复
    public final static String CLOCK_OFF_REPEAT="6";
    //下班打卡非工作日
    public final static String CLOCK_OFF_NOWORKDAY="7";
    //下班失败，没有上班打卡
    public final static String CLOCK_OFF_NOCLOCKIN="8";
    //下班打卡失败
    public final static String CLOCK_OFF_FAIL="9";
    //注册员工成功
    public final static String REGISTER_SUCCESS = "10";
    //注册员工失败
    public final static String REGISTER_FAIL = "11";
//    public final static String
//    public final static String
}
