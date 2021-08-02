package com.attendance.xuanf.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.attendance.xuanf.dao.impl.EmployeeDaoImpl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 功能描述：
 *
 * @author 陈铉锋
 * @date 2021/7/31
 */
public class DbUtils {
    public static javax.sql.DataSource ds = null;

    static {
        Properties pro = new Properties();
        InputStream is = EmployeeDaoImpl.class.getClassLoader().getResourceAsStream("druid.properties");
        try {
            pro.load(is);
            ds = DruidDataSourceFactory.createDataSource(pro);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
