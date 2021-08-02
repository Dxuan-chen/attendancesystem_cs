package com.attendance.xuanf.service.impl;

import com.attendance.xuanf.common.TimeConsts;
import com.attendance.xuanf.common.ResponseStatus;
import com.attendance.xuanf.dao.DaoFactory;
import com.attendance.xuanf.dao.IClockInfoDao;
import com.attendance.xuanf.dao.IEmployeeDao;
import com.attendance.xuanf.dao.IWorkDateDao;
import com.attendance.xuanf.dao.impl.IAttendanceStatusDao;
import com.attendance.xuanf.domain.AttendanceStatusDto;
import com.attendance.xuanf.domain.AttendanceStatusVo;
import com.attendance.xuanf.domain.ClockInfo;
import com.attendance.xuanf.domain.Employee;
import com.attendance.xuanf.service.IEmployeeService;

import java.text.ParseException;
import java.util.*;

/**
 * 功能描述：
 *
 * @author 陈铉锋
 * @date 2021/7/29
 */
public class EmployeeServiceImpl implements IEmployeeService {

    IEmployeeDao employeeDao = DaoFactory.createEmplyeeDao();
    IClockInfoDao clockInfoDao = DaoFactory.createClockInfoDao();
    IWorkDateDao workDateDao = DaoFactory.createWorkDateDao();
    IAttendanceStatusDao attendanceStatusDao = DaoFactory.createAttendanceStatusDao();

    @Override
    public Employee login(Employee employee) {

        return employeeDao.findEmployeeByLoginAndPassword(employee);
    }

    @Override
    public Object clockIn(Employee employee) {//上班打卡
        Date clockInTime = new Date();

        boolean flag = (boolean)workDateDao.findWorkDate(new java.sql.Date(clockInTime.getTime()));//查询工作日
        if(flag == false){//不是工作日

            return ResponseStatus.CLOCK_IN_NOWORKDAY;
        }else {

            ClockInfo clockInfo = new ClockInfo();
            clockInfo.setEmployeeNo(employee.getEmployeeNo());
            clockInfo.setClockInTime(clockInTime);
            clockInfo.setClockDate(new java.sql.Date(clockInTime.getTime()));
            Object findClock = clockInfoDao.findClockInfoByClock(clockInfo);
            if (findClock != null) {//重复上班打卡
//            return findClock;
                return ResponseStatus.CLOCK_IN_REPEAT;
            }
            int count = (int)clockInfoDao.updateClockInfoIn(clockInfo);
            if(count == 0){
                return ResponseStatus.CLOCK_IN_FAIL;
            }else {
                return ResponseStatus.CLOCK_IN_SUCCESS;
            }

        }
    }

    @Override
    public Object clockOff(Employee employee) {//下班打卡
        Date clockInTime = new Date();

        boolean flag = (boolean)workDateDao.findWorkDate(new java.sql.Date(clockInTime.getTime()));//查询工作日
        if(flag == false){//不是工作日
            return ResponseStatus.CLOCK_OFF_NOWORKDAY;
        }else {

            ClockInfo clockInfo = new ClockInfo();
            clockInfo.setEmployeeNo(employee.getEmployeeNo());
            clockInfo.setClockOffTime(clockInTime);
            clockInfo.setClockDate(new java.sql.Date(clockInTime.getTime()));
            Object findClock = clockInfoDao.findClockInfoByClock(clockInfo);
            if (findClock == null) {//没有上班打卡
                return ResponseStatus.CLOCK_OFF_NOCLOCKIN;
            } else {//有上班打卡
                if (((ClockInfo) findClock).getClockOffTime() == null) {//还没有下班打卡
                    clockInfo.setClockInTime(((ClockInfo) findClock).getClockInTime());

                    if((int)clockInfoDao.updateClockInfoOff(clockInfo) == 1){//下班打卡成功
                        return ResponseStatus.CLOCK_OFF_SUCCESS;
                    }else {//失败
                        return ResponseStatus.CLOCK_OFF_FAIL;
                    }
                } else {//已经下班打卡

//                return findClock;//返回已打卡信息
                    return ResponseStatus.CLOCK_OFF_REPEAT;
                }
            }
        }
    }

    @Override
    public Object registerEmpl(Employee employee) {//注册员工
        if(1 == (int)employeeDao.insertEmployeeByEntity(employee)){
            return ResponseStatus.REGISTER_SUCCESS;
        }else {
            return ResponseStatus.REGISTER_FAIL;
        }

    }

    /**
    *  0：正常
     * 1：迟到
     * 2：早退
     * 3、旷工
    */
    @Override
    public Object addAttendance() throws ParseException {//记录考勤状态
        List<Employee> employeeList = employeeDao.findAllEmployee();
//        Date date = new Date();
        //获取昨天日期
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(cal.DATE,-1);
        Date date = cal.getTime();
        if(employeeList != null){
            for (Employee employee : employeeList) {
                ClockInfo clockInfo = clockInfoDao.findByEmployeeNoAndDate(employee.getEmployeeNo(), new java.sql.Date(date.getTime()));

                if (clockInfo == null) {//旷工
                    int count = attendanceStatusDao.insert(employee.getEmployeeNo(), new java.sql.Date(date.getTime()), 3);
                    if (count == 1) {
                        System.out.println(new java.sql.Date(date.getTime()) + ":" + employee.getEmployeeNo() + "记录成功");
                    } else {
                        System.out.println(new java.sql.Date(date.getTime()) + ":" + employee.getEmployeeNo() + "记录失败");
                    }
                } else {
                    Date clockInTime = clockInfo.getClockInTime();
                    Date clockOffTime = clockInfo.getClockOffTime();

                    if(clockOffTime == null){
                        clockOffTime = clockInTime;
                    }
//                    if(cal.getTime().equals(clockInfo.getClockDate())) {
                        if (clockInTime.getTime() < TimeConsts.getClockInTime().getTime() && clockOffTime.getTime() > TimeConsts.getClockInOffTime().getTime()) {//正常
                            int count = attendanceStatusDao.insert(employee.getEmployeeNo(), new java.sql.Date(date.getTime()), 0);
                            if (count == 1) {
                                System.out.println(new java.sql.Date(date.getTime()) + ":" + employee.getEmployeeNo() + "记录成功");
                            } else {
                                System.out.println(new java.sql.Date(date.getTime()) + ":" + employee.getEmployeeNo() + "记录失败");
                            }

                        } else if (clockInTime.getTime() > TimeConsts.getClockInTime().getTime() && clockOffTime.getTime() > TimeConsts.getClockInOffTime().getTime()) {//迟到
                            int count = attendanceStatusDao.insert(employee.getEmployeeNo(), new java.sql.Date(date.getTime()), 1);
                            if (count == 1) {
                                System.out.println(new java.sql.Date(date.getTime()) + ":" + employee.getEmployeeNo() + "记录成功");
                            } else {
                                System.out.println(new java.sql.Date(date.getTime()) + ":" + employee.getEmployeeNo() + "记录失败");
                            }
                        } else if (clockInTime.getTime() < TimeConsts.getClockInTime().getTime() && clockOffTime.getTime() < TimeConsts.getClockInOffTime().getTime()) {//早退
                            int count = attendanceStatusDao.insert(employee.getEmployeeNo(), new java.sql.Date(date.getTime()), 2);
                            if (count == 1) {
                                System.out.println(new java.sql.Date(date.getTime()) + ":" + employee.getEmployeeNo() + "记录成功");
                            } else {
                                System.out.println(new java.sql.Date(date.getTime()) + ":" + employee.getEmployeeNo() + "记录失败");
                            }
                        } else {//旷工
                            int count = attendanceStatusDao.insert(employee.getEmployeeNo(), new java.sql.Date(date.getTime()), 3);
                            if (count == 1) {
                                System.out.println(new java.sql.Date(date.getTime()) + ":" + employee.getEmployeeNo() + "记录成功");
                            } else {
                                System.out.println(new java.sql.Date(date.getTime()) + ":" + employee.getEmployeeNo() + "记录失败");
                            }
                        }
//                    }
                }

            }

            return true;
        }
        return false;
    }

    @Override
    public List<?> findAttendacnceStatus(Employee employee) {//查看考勤信息
        Map<String,AttendanceStatusVo> asVoList = new HashMap<>();
        if(employee.getRole() == 1){//管理员查看全部员工考勤信息
            List<AttendanceStatusDto> asDtoList = attendanceStatusDao.findAllStatus();

            for (AttendanceStatusDto asDto : asDtoList) {
                if(!asVoList.containsKey(asDto.getEmployeeNo())){
                    AttendanceStatusVo asVo = new AttendanceStatusVo();
                    asVo.setEmployeeNo(asDto.getEmployeeNo());
                    asVo.setEmployeeName(asDto.getEmployeeName());
                    asVo.setWorkDate(asDto.getWorkDate());
                    if(asDto.getStatus() == 0){//正常
                        asVo.setNormalCount(1);
                    }else if(asDto.getStatus() == 1){//迟到
                        asVo.setLateCount(1);
                    }else if(asDto.getStatus() == 2){//早退
                        asVo.setEarlyCount(1);
                    }else if(asDto.getStatus() == 3){//旷工
                        asVo.setMinerCount(1);
                    }
                    asVoList.put(asDto.getEmployeeNo(),asVo);

                }else {
                    AttendanceStatusVo asVo = asVoList.get(asDto.getEmployeeNo());

                    if(asDto.getStatus() == 0){//正常
                        asVo.setNormalCount(asVo.getNormalCount()+1);
                    }else if(asDto.getStatus() == 1){//迟到
                        asVo.setLateCount(asVo.getLateCount()+1);
                    }else if(asDto.getStatus() == 2){//早退
                        asVo.setEarlyCount(asVo.getEarlyCount()+1);
                    }else if(asDto.getStatus() == 3){//旷工
                        asVo.setMinerCount(asVo.getMinerCount()+1);
                    }

                    asVoList.put(asDto.getEmployeeNo(),asVo);
                }
            }


            return new ArrayList<>(asVoList.values());
        }else {
            return attendanceStatusDao.findByEmployeeNo(employee);
        }

    }


}
