package cn.com.jnpc.meeting.dao.util;
/**
 * <p> </p>
 * <p> </p>
 * <p>Copyright: Copyright: Copyright (c) 2003 Dalian ChaoWei Computer Technology Co.,Ltd</p>
 * <p> All right reserved;</p>
 * @author 孙婷婷
 */
//import com.chaowei.warehouse.bean.util.RecordLog;
import oracle.sql.BLOB;
import java.io.*;
import java.sql.*;
import java.util.*;
import javax.naming.*;
import javax.servlet.*;
import javax.sql.*;

public class OracleConnection extends SqlGenerator {

  /**
   * 操作一个字段类型为clob的方法(插入,更新)
   * @param sqlins 正常的插入语句
   * @param sqlsel 查询插入的字段id号
   * @param colname clob字段的名称
   * @param colvalue clob字段的内容
   * @param tablename 表名
   * @param PKColumn 表的主键
   * @param flag 用于区别是插入还是更新
   * @return
   */
  public int insClob(String sqlins, String id, String tablename,
                     String PKColumn, String colname, String colvalue,int flag) {
    Connection conn = getConnection();
    Statement stmt = null;
    ResultSet rs = null;
    String currid = "";
    try {
      stmt = conn.createStatement();
      conn.setAutoCommit(false);
      // 插入一个空的CLOB对象
      stmt.executeUpdate(sqlins);
      if (flag ==0) {
        rs = stmt.executeQuery("select " + id + " id from dual");
        while (rs.next()) {
          currid = rs.getString("id");
        }
      } else {
        currid = id;
      }
      // 查询此CLOB对象并锁定
      rs = stmt.executeQuery("select " + colname + " from " + tablename +
                             " where " + PKColumn + " =" + currid + " for update");
      while (rs.next()) {
        // 取出此CLOB对象
        oracle.sql.CLOB clob = (oracle.sql.CLOB) rs.getClob(colname);
        // 向CLOB对象中写入数据
        BufferedWriter out = new BufferedWriter(clob.getCharacterOutputStream());
        BufferedReader in = new BufferedReader(new StringReader(colvalue));
        int c;
        while ( (c = in.read()) != -1) {
          out.write(c);
        }
        in.close();
        out.close();
      }
      // 正式提交
      conn.commit();
      conn.setAutoCommit(true);
      return 1;
    } catch (Exception e1) {
      Debug.print_log(e1);
      /* 出错回滚 */
      try {
        conn.rollback();
        conn.setAutoCommit(true);
        return -1;
      }
      catch (SQLException e2) {
        Debug.print_log(e2);
        return -1;
      }
    } finally {
      this.disconnect(rs,stmt, conn);
    }
  }

  /**
     * 返回一行多列查询结果
     * @param sql
     * @param columnName
     * @return
     */
  public String[] getSnglRowMultiCol(String sql, String[] colArr, int num) {
    Connection conn = null;
    ResultSet rs = null;
    Statement stmt = null;
    Vector v = new Vector();
    String[] colType = new String[colArr.length];

    try {
      conn = getConnection(0);
      stmt = conn.createStatement();
      rs = stmt.executeQuery(sql);

      ResultSetMetaData rsmd = rs.getMetaData();

      for (int i = 1; i <= rsmd.getColumnCount(); i++) {
        colType[i - 1] = rsmd.getColumnTypeName(i);
      }

      while (rs.next()) {
        for (int i = 0; i < colArr.length; i++) {
          if (colType[i].equals("CLOB")) {
            Clob clob = rs.getClob(colArr[i]);
            v.addElement(clob.getSubString(1, (int) clob.length()));
          } else {
            v.addElement(rs.getString(colArr[i]));
          }
        }
      }
    } catch (Exception e) {
      Debug.print_log(e);
    } finally {
      this.disconnect(rs, stmt, conn);
    }

    return this.cvtVtrToArr(v);
  }

  /**
     * 返回一行一列查询结果
     * @param sql
     * @param columnName
     * @return
     */
  public String getSnglRowSnglCol(String sql, String columnName, int num) {
    Connection conn = null;
    ResultSet rs = null;
    Statement stmt = null;
    String str = "";

    try {
      conn = getConnection(0);
      stmt = conn.createStatement();
      rs = stmt.executeQuery(sql);

      ResultSetMetaData rsmd = rs.getMetaData();
      String colType = rsmd.getColumnTypeName(1);

      while (rs.next()) {
        if (colType.equals("CLOB")) {
          Clob clob = rs.getClob(columnName);
          str = clob.getSubString(1, (int) clob.length());
        } else {
          str = rs.getString(columnName);
        }
      }
    } catch (Exception e) {
      Debug.print_log(e);
    } finally {
      this.disconnect(rs, stmt, conn);
    }

    return str;
  }

  /**
   * 返回多行多列查询结果
   * @param sql
   * @param colArr
   * @return
   */
  public String[][] getMultiRowMultiCol(String sql, String[] colArr, int num) {
    Connection conn = null;
    ResultSet rs = null;
    Statement stmt = null;
    Vector v = new Vector();
    int colCount = colArr.length;
    String[] colType = new String[colCount];

    try {
      conn = getConnection(0);
      stmt = conn.createStatement();
      rs = stmt.executeQuery(sql);

      ResultSetMetaData rsmd = rs.getMetaData();

      for (int i = 1; i <= rsmd.getColumnCount(); i++) {
        colType[i - 1] = rsmd.getColumnTypeName(i);
      }

      while (rs.next()) {
        String[] arr = new String[colCount];

        for (int i = 0; i < colCount; i++) {
          if (colType[i].equals("CLOB")) {
            Clob clob = rs.getClob(colArr[i]);
            arr[i] = clob.getSubString(1, (int) clob.length());
          } else {
            arr[i] = rs.getString(colArr[i]);
          }
        }

        v.addElement(arr);
      }
    } catch (Exception e) {
      Debug.print_log(e);
    } finally {
      this.disconnect(rs, stmt, conn);
    }

    return this.cvtVtrToArr(v, colCount);
  }

  /**
    * 返回多行一列查询结果
    * @param sql
    * @param columnName
    * @return
    */
  public String[] getMultiRowSnglCol(String sql, String columnName, int num) {
    Connection conn = null;
    ResultSet rs = null;
    Statement stmt = null;
    Vector v = new Vector();

    try {
      conn = getConnection(0);
      stmt = conn.createStatement();
      rs = stmt.executeQuery(sql);

      ResultSetMetaData rsmd = rs.getMetaData();
      String colType = rsmd.getColumnTypeName(1);

      while (rs.next()) {
        if (colType.equals("CLOB")) {
          Clob clob = rs.getClob(columnName);
          v.addElement(clob.getSubString(1, (int) clob.length()));
        } else {
          v.addElement(rs.getString(columnName));
        }
      }
    } catch (Exception e) {
      Debug.print_log(e);
    } finally {
      this.disconnect(rs, stmt, conn);
    }

    return this.cvtVtrToArr(v);
  }

  /**
   * 从连接池中返回一个数据库连接
   * @return
   */
  public static Connection getConnection(int num) {
    Connection connection = null;

    try {
      InitialContext initContext = new InitialContext();
      String strDataSourceName = "jdbc/login";
      DataSource dsOracle = (DataSource) initContext.lookup("java:comp/env/jdbc/login");
      connection = dsOracle.getConnection();
    } catch (Exception e) {
        Debug.print_log(e);
    }

    return connection;
  }

  /**
   * 返回记录的数目
   * @param sql
   * @return
   */
  public int getRecordCount(String sql) {
    Connection conn = null;
    ResultSet rs = null;
    Statement stmt = null;
    int count = 0;

    try {
      conn = getConnection(0);
      stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
          ResultSet.CONCUR_READ_ONLY);
      rs = stmt.executeQuery(sql);

      if (rs != null) {
        if (rs.next()) {
          rs.last();
          count = rs.getRow();
        }
      }
    } catch (Exception e) {
      Debug.print_log(e);
    } finally {
      this.disconnect(rs, stmt, conn);
    }

    return count;
  }

  /**
   * 通过表名、字段名称数组、字段类型数组、以及对应值数组生成一条insert语句
   * @param tblName 表名
   * @param colNames 字段名称数组
   * @param colTypes 对应的字段类型数组
   * @param colValues 字段对应值的数组合
   * @return
   */
  public String genInsStmt(String tblName, String[] colNames, String[] colValues) {
    String[] colTypes = this.getColTypes(tblName, colNames);
    StringBuffer strBuff = new StringBuffer();
    strBuff.append("insert into ");
    strBuff.append(tblName);
    strBuff.append("(");

    for (int i = 0; i < (colNames.length - 1); i++) {
      strBuff.append(colNames[i]);
      strBuff.append(",");
    }

    strBuff.append(colNames[colNames.length - 1]);
    strBuff.append(") values (");

    for (int i = 0; i < (colTypes.length - 1); i++) {
      if (!colValues[i].equals("")) { // 如果字段对应的值不为null

        if (colTypes[i].equals("NUMBER")) {
          strBuff.append(colValues[i]);
        }

        if (colTypes[i].equals("VARCHAR2")) {
          strBuff.append("'");
          strBuff.append(colValues[i]);
          strBuff.append("'");
        }

        if (colTypes[i].equals("DATE")) {
          strBuff.append("to_date('");
          strBuff.append(colValues[i]);
          strBuff.append("','YYYY-MM-DD HH24:MI:SS')");
        }

        if (colTypes[i].equalsIgnoreCase("CLOB")) {
          //strBuff.append("'");
          //strBuff.append(colValues[i]);
          //strBuff.append("'");
          strBuff.append("EMPTY_CLOB()");
        }
      } else { // 如果字段对应的值为null
        strBuff.append("null");
      }
       // 外层if语句结束

      strBuff.append(",");
    }
     // for循环结束

    int i = colTypes.length - 1;

    if (!colValues[i].equals("")) { // 如果字段对应的值不为null

      if (colTypes[i].equals("NUMBER")) {
        strBuff.append(colValues[i]);
      }

      if (colTypes[i].equals("VARCHAR2")) {
        strBuff.append("'");
        strBuff.append(colValues[i]);
        strBuff.append("'");
      }

      if (colTypes[i].equals("DATE")) {
        strBuff.append("to_date('");
        strBuff.append(colValues[i]);
        strBuff.append("','YYYY-MM-DD HH24:MI:SS')");
      }

      if (colTypes[i].equalsIgnoreCase("CLOB")) {
        //strBuff.append("'");
        //strBuff.append(colValues[i]);
        //strBuff.append("'");
        strBuff.append("EMPTY_CLOB()");
      }
    } else { // 如果字段对应的值为null
      strBuff.append("null");
    }
     // 外层if语句结束

    strBuff.append(")");

    return strBuff.toString();
  }
}

