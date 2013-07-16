package cn.com.jnpc.meeting.dao.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DbUtilsTemplate {

    private DataSource       dataSource;
    private QueryRunner      queryRunner;
    private static final Log LOG = LogFactory.getLog(DbUtilsTemplate.class);

    public DbUtilsTemplate(JndiName jndiName) {
        try {
            Context context = new InitialContext();
            dataSource = (DataSource) context.lookup("java:/comp/env/" + jndiName.getJndiName());
        } catch (Exception e) {
            System.out.println("get dataSource failed:" + e);
        }
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    /**
     * 执行sql语句
     * 
     * @param sql sql语句
     * @param params 参数数组
     * @return 受影响的行数
     */
    public int update(String sql, Object... params) {
        queryRunner = new QueryRunner(dataSource);
        int affectedRows = 0;
        try {
            if (params == null) {
                affectedRows = queryRunner.update(sql);
            } else {
                affectedRows = queryRunner.update(sql, params);
            }
        } catch (SQLException e) {
            LOG.error("Error occured while attempting to update data", e);
        }
        return affectedRows;
    }
    
    /**
     * 执行sql语句
     * 
     * @param conn Connection
     * @param sql sql语句
     * @param params 参数数组
     * @return 受影响的行数
     */
    public int update(Connection conn ,String sql, Object... params) {
        queryRunner = new QueryRunner();
        int affectedRows = 0;
        try {
            if (params == null) {
                affectedRows = queryRunner.update(conn,sql);
            } else {
                affectedRows = queryRunner.update(conn,sql, params);
            }
        } catch (SQLException e) {
            LOG.error("Error occured while attempting to update data", e);
        }
        return affectedRows;
    }

    /**
     * 执行批量sql语句
     * 
     * @param sql sql语句
     * @param params 二维参数数组
     * @return 受影响的行数的数组
     */
    public int[] batchUpdate(String sql, Object[][] params) {
        queryRunner = new QueryRunner(dataSource);
        int[] affectedRows = new int[0];
        try {
            affectedRows = queryRunner.batch(sql, params);
        } catch (SQLException e) {
            LOG.error("Error occured while attempting to batch update data", e);
        }
        return affectedRows;
    }
    
    /**
     * 执行批量sql语句
     * 
     * @param sql sql语句
     * @param params 二维参数数组
     * @return 受影响的行数的数组
     */
    public int[] batchUpdate(Connection conn,String sql, Object[][] params) {
        queryRunner = new QueryRunner(dataSource);
        int[] affectedRows = new int[0];
        try {
            affectedRows = queryRunner.batch(conn,sql, params);
        } catch (SQLException e) {
            LOG.error("Error occured while attempting to batch update data", e);
        }
        return affectedRows;
    }

    /**
     * 执行查询，将每行的结果保存到一个Map对象中，然后将所有Map对象保存到List中
     * 
     * @param sql sql语句
     * @param params 参数数组
     * @return 查询结果
     */
    public List<Map<String, Object>> find(String sql, Object... params) {
        queryRunner = new QueryRunner(dataSource);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {
            if (params == null) {
                list = (List<Map<String, Object>>) queryRunner.query(sql, new MapListHandler());
            } else {
                list = (List<Map<String, Object>>) queryRunner.query(sql, new MapListHandler(), params);
            }
        } catch (SQLException e) {
            LOG.error("Error occured while attempting to query data", e);
        }
        return list;
    }
    
    /**
     * 执行查询，将每行的结果保存到Bean中，然后将所有Bean保存到List中
     * 
     * @param entityClass 类名
     * @param sql sql语句
     * @param params 参数数组
     * @return 查询结果
     */
    public <T> List<T> find(Class<T> entityClass, String sql, Object... params) {
        queryRunner = new QueryRunner(dataSource);
        List<T> list = new ArrayList<T>();
        try {
            if (params == null) {
                list = (List<T>) queryRunner.query(sql, new BeanListHandler<T>(entityClass));
            } else {
                list = (List<T>) queryRunner.query(sql, new BeanListHandler<T>(entityClass), params);
            }
        } catch (SQLException e) {
            LOG.error("Error occured while attempting to query data", e);
        }
        return list;
    }

    /**
     * 查询出结果集中的第一条记录，并封装成对象
     * 
     * @param entityClass 类名
     * @param sql sql语句
     * @param params 参数数组
     * @return 对象
     */
    public <T> T findFirst(Class<T> entityClass, String sql, Object... params) {
        queryRunner = new QueryRunner(dataSource);
        T t = null;
        try {
            if (params == null) {
                t = queryRunner.query(sql, new BeanHandler<T>(entityClass));
            } else {
                t = queryRunner.query(sql, new BeanHandler<T>(entityClass), params);
            }
        } catch (SQLException e) {
            LOG.error("Error occured while attempting to query data", e);
        }
        return t;
    }

    /**
     * 查询出结果集中的第一条记录，并封装成Map对象
     * 
     * @param sql sql语句
     * @param params 参数数组
     * @return 封装为Map的对象
     */
    public Map<String, Object> findFirst(String sql, Object... params) {
        queryRunner = new QueryRunner(dataSource);
        Map<String, Object> map = null;
        try {
            if (params == null) {
                map = (Map<String, Object>) queryRunner.query(sql, new MapHandler());
            } else {
                map = (Map<String, Object>) queryRunner.query(sql, new MapHandler(), params);
            }
        } catch (SQLException e) {
            LOG.error("Error occured while attempting to query data", e);
        }
        return map;
    }

    /**
     * 查询某一条记录，并将指定列的数据转换为Object
     * 
     * @param sql sql语句
     * @param columnName 列名
     * @param params 参数数组
     * @return 结果对象
     */
    public Object findBy(String sql, String columnName, Object... params) {
        queryRunner = new QueryRunner(dataSource);
        Object object = null;
        try {
            if (params == null) {
                object = queryRunner.query(sql, new ScalarHandler<Object>(columnName));
            } else {
                object = queryRunner.query(sql, new ScalarHandler<Object>(columnName), params);
            }
        } catch (SQLException e) {
            LOG.error("Error occured while attempting to query data", e);
        }
        return object;
    }

    /**
     * 查询某一条记录，并将指定列的数据转换为Object
     * 
     * @param sql sql语句
     * @param columnIndex 列索引
     * @param params 参数数组
     * @return 结果对象
     */
    public Object findBy(String sql, int columnIndex, Object... params) {
        queryRunner = new QueryRunner(dataSource);
        Object object = null;
        try {
            if (params == null) {
                object = queryRunner.query(sql, new ScalarHandler<Object>(columnIndex));
            } else {
                object = queryRunner.query(sql, new ScalarHandler<Object>(columnIndex), params);
            }
        } catch (SQLException e) {
            LOG.error("Error occured while attempting to query data", e);
        }
        return object;
    }
}
