package cn.com.jnpc.meeting.dao.util;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;

public class DbHelper {
    
    public static QueryRunner getJNPCQueryRunner() {
        return getQueryRunner("jnpc");
    }
    
    public static QueryRunner getIntrawebQueryRunner() {
        return getQueryRunner("intraweb");
    }
    
    public static QueryRunner getQueryRunner(String dsName) {
        return new QueryRunner(getDataSource(dsName));
    }
    
    public static DataSource getDataSource(String dsName) {
        try {
            Context context = new InitialContext();
            return (DataSource)context.lookup("java:/comp/env/jdbc/" + dsName);
        } catch (Exception e) {
            System.out.println("get dataSource failed:" + e);
            return null;
        }
    }
    
    public static Connection getConnection(String dsName) {
        try {
            return getDataSource(dsName).getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
}
