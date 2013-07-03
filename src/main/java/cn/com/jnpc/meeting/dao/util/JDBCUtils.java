package cn.com.jnpc.meeting.dao.util;

import java.sql.Connection;
import java.sql.SQLException;

public class JDBCUtils {

    private static ThreadLocal<Connection> container = new ThreadLocal<Connection>();

    public static ThreadLocal<Connection> getContainer() {
        return container;
    }

    public static void startTransaction() {
        Connection conn = container.get();
        try {
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static void commit() {
        Connection conn = container.get();
        if (conn != null) {
            try {
                conn.commit();
            } catch (SQLException e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        }
    }

    public static void rollback() {
        Connection conn = container.get();
        if (conn != null) {
            try {
                conn.rollback();
            } catch (SQLException e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        }
    }

    public static void close() {
        Connection conn = container.get();
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        }
    }


}
