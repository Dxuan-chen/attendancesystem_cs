package com.attendance.xuanf.app;

import com.attendance.xuanf.controller.AttendanceserverController;

import java.util.Scanner;

/**
 * 功能描述：
 *
 * @author 陈铉锋
 * @date 2021/7/29
 */
public class AttenanceServerMain {

    public static void main(String[] args) {
        AttendanceserverController attserverController = new AttendanceserverController();
        attserverController.doAction();
        new Scanner(System.in).next();
    }
}
