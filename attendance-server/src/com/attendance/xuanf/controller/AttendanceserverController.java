package com.attendance.xuanf.controller;

import com.attendance.xuanf.common.CommandType;
import com.attendance.xuanf.common.TimeConsts;
import com.attendance.xuanf.domain.AttendanceStatusDto;
import com.attendance.xuanf.domain.Employee;
import com.attendance.xuanf.listener.IReceivedataCallBackListener;
import com.attendance.xuanf.service.IEmployeeService;
import com.attendance.xuanf.service.impl.EmployeeServiceImpl;
import com.attendance.xuanf.utils.SocketUtils;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Time;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 功能描述：
 * 1、初始化ServerSocket对象，绑定port
 * 2、实现回调接口
 * 3、根据接受客户端发送过来的请求指令，委派业务层处理
 * 4、处理完成请求指令之后，对客户端作出响应处理
 */
public class AttendanceserverController implements IReceivedataCallBackListener {

    private SocketUtils socketUtils;
    private static IEmployeeService employeeService;
    static ServerSocket serverSocket = null;

    public AttendanceserverController(){
        employeeService = new EmployeeServiceImpl();

        new Timer().schedule(new TimerTask(){

            @Override
            public void run() {
                try {
                    employeeService.addAttendance();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        }, TimeConsts.getFirstTime(),TimeConsts.getDelay());
    }

    public void doAction(){
        initSocketConnection();
    }

    private void initSocketConnection() {

        try{
            System.out.println("建立服务器端口6666");
            serverSocket = new ServerSocket(6666);
            //此方法是阻塞方法，一直监听客户端的连接
            Socket socket = serverSocket.accept();
            socketUtils = new SocketUtils(socket,this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void obtainConnection(){
        Socket socket = null;
        try {
            socket = serverSocket.accept();
            socketUtils = new SocketUtils(socket,this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //获取客户端发送过来的数据
    @Override
    public void receviceCallBackListener(Object result) {

        System.out.println(new Time(new java.util.Date().getTime())+"读取客户端的数据="+result);
        HashMap<String,Object> resultMap = (HashMap<String,Object>)result;
        HashMap<String,Object> responseData = new HashMap<>();
        resultMap.forEach((k,v) -> {
            if(CommandType.EMP_LOGIN.equals(k)){//登录
                //客户端发送过来的数据部分进行解释
                Employee employee = (Employee) v;
                Employee outEmployee = login(employee);

                //针对客户端作出响应结果
                responseData.put(CommandType.EMP_LOGIN_RESPONSE,outEmployee);
            }else if(CommandType.CLOCK_IN_TIME.equals(k)){//上班打卡
                Employee employee = (Employee) v;
                Object resultClockIn = clockIn(employee);

                responseData.put(CommandType.CLOCK_IN_TIME_RESPONSE,resultClockIn);
            }else if(CommandType.CLOCK_OFF_TIME.equals(k)){//下班打卡
                Employee employee = (Employee) v;
                Object resultClockOff = clockOff(employee);

                responseData.put(CommandType.CLOCK_OFF_TIME_RESPONSE,resultClockOff);

            }else if(CommandType.REGISTER_EMP_REQUEST.equals(k)){//注册员工
                Employee employee = (Employee) v;
                Object resultRegisterEmpl = registerEmpl(employee);

                responseData.put(CommandType.REGISTER_EMP_RESPONSE,resultRegisterEmpl);

            }else if(CommandType.ATTENDANCE_REQUEST.equals(k)){//查询考勤信息
                Employee employee = (Employee) v;
                List<?> resultAttendanceList = findAttendanceStatus(employee);

                responseData.put(CommandType.ATTENDANCE_RESPONSE,resultAttendanceList);
            }else if(CommandType.SYSTEMEXIT_REQUEST.equals(k)) {
                socketUtils.connectionClose();
            }

        });


        socketUtils.writeObject(responseData);
    }


    private Employee login(Employee employee) {//登录

        return employeeService.login(employee);
    }

    private Object clockIn(Employee employee) {//上班打卡

        return employeeService.clockIn(employee);
    }

    private Object clockOff(Employee employee) {//下班打卡

        return employeeService.clockOff(employee);
    }

    private Object registerEmpl(Employee employee){//注册员工

        return employeeService.registerEmpl(employee);
    }

    private List<?> findAttendanceStatus(Employee employee) {//查询考勤信息

        return employeeService.findAttendacnceStatus(employee);
    }


}
