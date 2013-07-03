/**
 * @Title: DBTools.java 
 * @Package cn.com.jnpc.meeting.dao.util  
 * @author A18ccms A18ccms_gmail_com   
 * @date 2013-4-19 下午2:39:46 
 *@Copyright:Copyright (c) 
 *@Company:whtylinuke
 * @version V1.0 
 */
package cn.com.jnpc.meeting.dao.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

/**
 * 数据库操作基本类
 * 
 * @ClassName: DBTools
 * @Title:
 * @Author:linuke
 * @Since:2013-4-19下午2:39:46
 * @Version:1.0
 */

public class DBTools {
    
    private QueryRunner qr   = null;
    private QueryRunner qr2   = null;
    
    private Connection  conn = null;
    private Context     context = null;
    
    /**
     * <p>
     * Title:
     * </p>
     * <p>
     * Description:
     * </p>
     */
    public DBTools(JndiName dsn) {
        DataSource ds = getDataSource(dsn.getJndiName());
        try {
            conn = ds.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        qr = new QueryRunner();
        qr2 = new QueryRunner(ds);
    }
    private DataSource getDataSource(String dsName) {
        try {
            context = new InitialContext();
            return (DataSource)context.lookup("java:/comp/env/" + dsName);
        } catch (Exception e) {
            System.out.println("get dataSource failed:" + e);
            return null;
        }
    }
    
    public int update(String sql) {
        try {
            if(conn == null || conn.isClosed()){
                return qr2.update(sql);
            }
            return qr.update(conn, sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        } finally {
            closeConn();
        }
    }
    
    public int update(String sql, Object... params) {
        try {
            if(conn == null || conn.isClosed()){
                return qr2.update(sql, params);
            }
            return qr.update(conn, sql, params);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }finally {
            closeConn();
        }
    }
    
    /**
     * 
     *@Title:  query
     *@Author: linuke
     *@Since: 2013-4-23下午2:08:13
     *@param clazz 要封装的对象的class
     *@param sql
     *@return
     */
    public <T> List<T> query(Class<T> clazz, String sql) {
        try {
            if(conn == null || conn.isClosed()){
                return qr2.query( sql, new BeanListHandler<T>(clazz));
            }
            return qr.query(conn, sql, new BeanListHandler<T>(clazz));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            closeConn();
        }
    }
    
    
    public <T> T query(String sql,Class<T> clazz){
        try {
            if(conn == null || conn.isClosed()){
                return qr2.query( sql, new BeanHandler<T>(clazz));
            }
            return qr.query(conn, sql, new BeanHandler<T>(clazz));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            closeConn();
        }
    }
    
    public List<Object[]> query(String sql) {
        try {
            if(conn == null || conn.isClosed()){
                return qr2.query( sql, new ArrayListHandler());
            }
            return qr.query(conn, sql, new ArrayListHandler());
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            closeConn();
        }
    }
    
    /**
     * 返回指定列的数据.
     * 
     * @Title: query
     * @Author: linuke
     * @Since: 2013-4-19下午3:14:25
     * @param qr
     * @param conn
     * @param sql
     * @param columnName
     * @return
     */
    public List<Object> query(String sql, String columnName) {
        try {
            if(conn == null || conn.isClosed()){
                return qr2.query( sql, new ColumnListHandler<Object>(columnName));
            }
            return qr.query(conn, sql, new ColumnListHandler<Object>(columnName));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            closeConn();
        }
    }
    
    /**
     * 返回结果集第一行中的指定字段，如未指定字段，则返回第一个字段
     * 
     * @Title: queryReturnOne
     * @Author: linuke
     * @Since: 2013-4-19下午3:19:11
     * @param qr
     * @param sql
     * @param columnName
     * @return
     */
    public Object queryReturnOne(String sql, String columnName) {
        try {
            if(conn == null || conn.isClosed()){
                if (columnName != null && !"".equals(columnName)) {
                    return qr2.query(sql, new ScalarHandler<Object>(columnName));
                }
                return qr2.query(sql, new ScalarHandler<Object>());
            }
            if (columnName != null && !"".equals(columnName)) {
                return qr.query(conn, sql, new ScalarHandler<Object>(columnName));
            }
            return qr.query(conn, sql, new ScalarHandler<Object>());
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            closeConn();
        }
    }
    
    public void closeConn(){
        DbUtils.closeQuietly(conn);
        try {
            if (context != null) {
                context.close();
            }
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }
    
    public Long getCount(String sql) {
        Object cut = queryReturnOne(sql, "");
        String c = cut == null ? "0":cut.toString();
        return Long.parseLong(c);
    }
    
    
    public <T> int add(T t,String tblName){
    	try {
			String sql = SQLStatementGetter.getInsertStatement(t, tblName);
			if(conn == null || conn.isClosed()){
				return qr2.update(sql);
			}
			return qr.update(conn,sql);
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
        } finally {
            closeConn();
		}
    }
    
    public <T> int update(T t,String tblName,String pk){
    	try {
			String sql = SQLStatementGetter.getUpdateStatement(t, tblName, pk);
			if(conn == null || conn.isClosed()){
				return qr2.update(sql);
			}
			return qr.update(conn,sql);
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
        } finally {
            closeConn();
		}
    }
}
