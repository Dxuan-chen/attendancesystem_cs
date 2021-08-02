package com.attendance.xuanf.dao.impl;

import com.attendance.xuanf.dao.IWorkDateDao;
import com.attendance.xuanf.domain.ClockInfo;
import com.attendance.xuanf.utils.DbUtils;

import javax.sql.DataSource;
import java.sql.*;

/**
 * 功能描述：
 *
 * @author 陈铉锋
 * @date 2021/7/31
 */
public class WorkDateDaoImpl implements IWorkDateDao {

    static DataSource ds = DbUtils.ds;

    @Override
    public Object findWorkDate(Date date) {
        Connection con = null;
        PreparedStatement pre = null;
        ResultSet rs = null;
        ClockInfo clockInfo2 = new ClockInfo();

        try {
            con = ds.getConnection();

            String sql = "select * from t_work_date where work_date=?;";
            pre = con.prepareStatement(sql);
            pre.setDate(1, date);
            rs = pre.executeQuery();
            if(rs.next() == false){
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                rs.close();
                pre.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return true;
    }
}
