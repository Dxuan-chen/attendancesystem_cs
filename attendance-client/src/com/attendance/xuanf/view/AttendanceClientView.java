package com.attendance.xuanf.view;

import com.attendance.xuanf.common.CommandType;
import com.attendance.xuanf.common.ResponseStatus;

import com.attendance.xuanf.domain.AttendanceStatus;

import com.attendance.xuanf.domain.AttendanceStatusVo;
import com.attendance.xuanf.domain.Employee;
import com.attendance.xuanf.listener.IReceivedataCallBackListener;
import com.attendance.xuanf.utils.SocketUtils;



import java.io.IOException;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/*
* 1、连接服务器的ServerSocket
* 2、实现回调接口，获取服务端的响应数据
* 3、根据用户选择的菜单内容，将请求指令及数据发送到服务端处理
* */
public class AttendanceClientView implements IReceivedataCallBackListener {

    //记录登录成功之后的员工信息
    private Employee employee;
    private SocketUtils socketUtils;
    private Scanner sc=new Scanner(System.in);
    private Socket socket = null;

    public static void main(String[] args) {

        AttendanceClientView client=new AttendanceClientView();
        client.doAction();

    }

    private void connectionSocketServer(){
        try {
            socket=new Socket("127.0.0.1",6666);
            if(socket!=null && socket.isConnected())
            {
                System.out.println("连接服务器成功");
                socketUtils=new SocketUtils(socket,this);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    * 启动客户端
    * */
    public void doAction(){
        System.out.println("欢迎进入考勤系统");
        connectionSocketServer();
        inputLoginInfo();
    }


    /*
    * 实现登录信息输入菜单内容
    * */
    public void inputLoginInfo(){
        //获取键盘内容
        System.out.println("请输入登录名：");
        String loginName = sc.nextLine();
        System.out.println("请输入密码：");
        String password = sc.nextLine();

        //登录数据封装Employee对象
        Employee loginEmp=new Employee();
        loginEmp.setLoginName(loginName);
        loginEmp.setPassword(password);

        //将登录员工信息封装成HashMap对象
        HashMap<String,Object> data=new HashMap<>();
        data.put(CommandType.EMP_LOGIN,loginEmp);
        socketUtils.writeObject(data);

    }

    /*
    * 接受服务端的响应数据
    * */
    @Override
    public void receviceCallBackListener(Object result) {

        HashMap<String, Object> resultMap = (HashMap<String, Object>) result;

        resultMap.forEach((k, v) -> {

            if (CommandType.EMP_LOGIN_RESPONSE.equals(k)) {//登录
                Object responseResult = resultMap.get(CommandType.EMP_LOGIN_RESPONSE);

                if (responseResult == null) {
                    //登录失败
                    System.out.println("登录失败，请重新登录！");
                    //返回录入登录名及密码页面
                    inputLoginInfo();
                } else {
                    System.out.println("登录成功");
                    Employee employee = (Employee) responseResult;
                    this.employee = employee;
                    //进入主菜单
                    System.out.println("进入主菜单");
                    menuView(employee);

                }
            } else if (CommandType.CLOCK_IN_TIME_RESPONSE.equals(k)) {//上班打卡
                Object responseResult = resultMap.get(CommandType.CLOCK_IN_TIME_RESPONSE);

                if(ResponseStatus.CLOCK_IN_SUCCESS.equals((String)responseResult)){
                    System.out.println("上班打卡成功");
                }else if(ResponseStatus.CLOCK_IN_REPEAT.equals((String)responseResult)){
                    System.out.println("上班打卡失败，请勿重复打卡");
                }else if (ResponseStatus.CLOCK_IN_NOWORKDAY.equals((String)responseResult)){
                    System.out.println("打卡失败,非工作日");
                }else if(ResponseStatus.CLOCK_IN_FAIL.equals((String)responseResult)){
                    System.out.println("打卡失败...");
                }else {
                    System.out.println("(String)responseResult:"+(String)responseResult);
                }


            } else if (CommandType.CLOCK_OFF_TIME_RESPONSE.equals(k)) {//下班打卡
                Object responseResult = resultMap.get(CommandType.CLOCK_OFF_TIME_RESPONSE);

                if(ResponseStatus.CLOCK_OFF_SUCCESS.equals((String)responseResult)){
                    System.out.println("下班打卡成功");
                }else if(ResponseStatus.CLOCK_OFF_REPEAT.equals((String)responseResult)){
                    System.out.println("下班打卡失败，请勿重复打卡");
                }else if(ResponseStatus.CLOCK_OFF_NOCLOCKIN.equals((String)responseResult)){
                    System.out.println("下班打卡失败，没有上班打卡");
                }else if (ResponseStatus.CLOCK_OFF_NOWORKDAY.equals((String)responseResult)){
                    System.out.println("打卡失败,非工作日");
                }else if(ResponseStatus.CLOCK_OFF_FAIL.equals((String)responseResult)){
                    System.out.println("打卡失败...");
                }

            } else if (CommandType.REGISTER_EMP_RESPONSE.equals(k)) {//注册员工
                Object responseResult = resultMap.get(CommandType.REGISTER_EMP_RESPONSE);

                if(ResponseStatus.REGISTER_SUCCESS.equals((String)responseResult)){
                    System.out.println("注册成功");
                }else if(ResponseStatus.REGISTER_FAIL.equals((String)responseResult)){
                    System.out.println("注册失败");
                }

            } else if (CommandType.ATTENDANCE_RESPONSE.equals(k)) {//查询员工考勤信息

                List<?> valuesList = (List)v;

                if(valuesList.size()!=0&&valuesList!=null&&valuesList.get(0) instanceof AttendanceStatus) {
                    List<AttendanceStatus> attendanceStatusList = (List<AttendanceStatus>) v;
                    if (attendanceStatusList != null && attendanceStatusList.size() != 0) {
                        for (AttendanceStatus attendanceStatus : attendanceStatusList) {
                            System.out.println("-------------------");
                            System.out.println("工号" + attendanceStatus.getEmployeeNo());
                            System.out.println("日期:" + attendanceStatus.getAttendanceDate());
                            System.out.print("状态：");
                            if (attendanceStatus.getStatus() == 0) {
                                System.out.println("正常");
                            } else if (attendanceStatus.getStatus() == 1) {
                                System.out.println("迟到");
                            } else if (attendanceStatus.getStatus() == 2) {
                                System.out.println("早退");
                            } else if (attendanceStatus.getStatus() == 3) {
                                System.out.println("旷工");
                            }
                        }
                        System.out.println("-------------------");
                    } else {
                        System.out.println("无考勤信息");
                    }
                }else if(valuesList.size()!=0&&valuesList!=null&&valuesList.get(0) instanceof AttendanceStatusVo){
                    List<AttendanceStatusVo> asVoList = (List<AttendanceStatusVo>) v;
                    if (asVoList != null && asVoList.size() != 0) {
                        System.out.println("员工编号  员工名  考勤年月  迟到  早退  正常  旷工");
                        for (AttendanceStatusVo asVo : asVoList) {
                            System.out.println("-----------------------------------------------");
                            System.out.print(asVo.getEmployeeNo()+'\t');
                            System.out.print(asVo.getEmployeeName()+'\t');
                            System.out.print(asVo.getWorkDate()+"    ");
                            System.out.print(asVo.getLateCount()+"    ");
                            System.out.print(asVo.getEarlyCount()+"    ");
                            System.out.print(asVo.getNormalCount()+"    ");
                            System.out.print(asVo.getMinerCount()+"    ");
                            System.out.println();

                        }
                        System.out.println("-----------------------------------------------");
                    } else {
                        System.out.println("无考勤信息");
                    }
                }

            }

            if (!CommandType.EMP_LOGIN_RESPONSE.equals(k)) {
                String input = null;
                while (true) {
                    System.out.println("0.返回主菜单");
                    if ("0".equals(input = sc.nextLine())) {
                        menuView(this.employee);
                        break;
                    }
                    System.out.println("操作错误，请重新输入！");
                }
            }
        });

    }

    //主菜单
    private void menuView(Employee employee) {
        HashMap<String, Object> requestData = new HashMap<>();
        String requestType = null;
        int role = employee.getRole();

        if(role == 0){//0为普通员工
            String input = null;
            boolean flag = true;
            System.out.println("1.上班打卡");
            System.out.println("2.下班打卡");
            System.out.println("3.查看考勤信息");
            System.out.println("4.退出系统");

            while (flag) {
                flag = false;
                input = sc.nextLine();
                switch (input){
                    case "1" :{//上班打卡
                        requestType = CommandType.CLOCK_IN_TIME;
                        System.out.println("打卡中...");
                        requestData.put(requestType,employee);
                        break;
                    }
                    case "2" :{//下班打卡
                        requestType = CommandType.CLOCK_OFF_TIME;
                        System.out.println("打卡中...");
                        requestData.put(requestType,employee);
                        break;
                    }
                    case "3" :{//查看考勤信息
                        requestType = CommandType.ATTENDANCE_REQUEST;
                        System.out.println("加载信息中...");
                        requestData.put(requestType, this.employee);

                        break;
                    }
                    case "4" : {//退出系统

                        requestType = CommandType.SYSTEMEXIT_REQUEST;
                        System.out.println("退出中...");
                        requestData.put(requestType, this.employee);
                        socketUtils.connectionClose();
                        System.out.println("已退出");

                        break;
                    }
                    default:{
                        System.out.println("操作错误，请重新输入");
                        flag = true;
                        break;
                    }
                }


            }

        }else if (role == 1) {//1为管理员
            System.out.println("1.注册员工信息");
            System.out.println("2.查询统计员工考勤信息");
            System.out.println("3.退出系统");
            boolean flag = true;
            String input = null;

            while (flag) {
                flag = false;
                input = sc.nextLine();
                switch (input) {
                    case "1": {//注册员工信息
                        requestType = CommandType.REGISTER_EMP_REQUEST;

                        Employee employee2 = new Employee();
                        System.out.println("请输入员工信息：");
                        System.out.println("工号：");
                        employee2.setEmployeeNo(sc.nextLine());
                        System.out.println("姓名：");
                        employee2.setEmployeeName(sc.nextLine());
                        System.out.println("用户名：");
                        employee2.setLoginName(sc.nextLine());
                        System.out.println("密码：");
                        employee2.setPassword(sc.nextLine());
                        System.out.println("岗位：");
                        employee2.setJob(sc.nextLine());
                        System.out.println("入职时间(yyyy-MM-dd)：");
                        try {
                            employee2.setHiredate(new java.sql.Date(new SimpleDateFormat("yyyy-MM-dd").parse(sc.nextLine()).getTime()));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        System.out.println("薪资：");
                        employee2.setSal(sc.nextDouble());
                        sc.nextLine();//读取多余的换行符
                        employee2.setRole(0);
                        requestData.put(requestType, employee2);

                        break;
                    }

                    case "2": {//查询统计员工考勤信息
                        requestType = CommandType.ATTENDANCE_REQUEST;
                        System.out.println("加载信息中...");
                        requestData.put(requestType, this.employee);

                        break;
                    }
                    case "3": {//退出系统
                        requestType = CommandType.SYSTEMEXIT_REQUEST;
                        System.out.println("退出中...");
                        requestData.put(requestType, this.employee);
                        socketUtils.connectionClose();
                        System.out.println("已退出");

                        break;
                    }
                    default: {
                        System.out.println("操作错误，请重新输入");
                        flag = true;
                        break;
                    }

                }


            }
        }
        socketUtils.writeObject(requestData);

    }
}
