package cn.com.jnpc.meeting.dao.util;
/**
 * <p>Title: 处理数据增、删、改的Java Bean</p>
 * <p>Description: 该类是真正意义的数据Bean，只面向数据，与业务无关，也可用于其他项目，可重用度极高！</p>
 * <p>Company: 大连超维计算机技术有限公司</p>
 * @author 崔冠宇
 * @version 1.0
 */
import javax.servlet.*;
import java.io.*;
import java.sql.*;
import javax.sql.*;
import oracle.sql.BLOB;
import javax.naming.*;
import java.util.*;
//import com.chaowei.warehouse.bean.util.RecordLog;

public class Oracle {
    
  /**
   * 完成数据库的增删改操作，要求传入的sql语句必须为insert,update或delete
   * 有返回值，-1表示操作不成功，0表示没有更新行，正整数代表更新的行数
   * @param sql
   */
  public int update(String sql) {
    Connection conn = getConnection();
    Statement  stmt = null;
    int result = 0;
    try {
      stmt = conn.createStatement();
      conn.setAutoCommit(false);
      result = stmt.executeUpdate(sql);
      // 如果是更新和删除操作，记录到日志文件
      //if ((sql.toLowerCase().trim().startsWith("update")||sql.toLowerCase().trim().startsWith("delete"))) {
       // RecordLog.println(sql);
      //}
    
      conn.commit();
      conn.setAutoCommit(true);   
      return result;
    } catch(SQLException e1) {
      Debug.print_log(e1,sql);
      try{
        conn.rollback();
        conn.setAutoCommit(true);
        return -1;
      } catch(SQLException e2) {
        Debug.print_log(e2,sql);
        return -1;
      }
    } finally {
      this.disconnect(stmt, conn);
    }
  }

  /**
   * 传入一组sql语句，完成数据库的增删改操作
   *有返回值，-1表示操作不成功，0表示没有更新行，正整数代表更新的行数
   * 要求传入的sql语句必须为insert,update或delete。
   * 将这组sql语句做成一个Transaction
   * @param sqlArr
   */
  public int update(String[] sqlArr) {
    Connection conn = getConnection();
    Statement  stmt = null;
    int result = 0;
    try {
      stmt = conn.createStatement();
      conn.setAutoCommit(false);
      for (int i = 0; i < sqlArr.length; i++) {
        // 如果是更新和删除操作，记录到日志文件
        //if ( (sqlArr[i].toLowerCase().trim().startsWith("update") ||
         //     sqlArr[i].toLowerCase().trim().startsWith("delete"))) {
        //  RecordLog.println(sqlArr[i]);
       // }

        result += stmt.executeUpdate(sqlArr[i]);
      }
      conn.commit();
      conn.setAutoCommit(true);
      return result;
    } catch(SQLException e1) {
      Debug.print_log(e1,sqlArr[0]);  
      try{
        conn.rollback();
        conn.setAutoCommit(true);
        return -1;
      } catch(SQLException e2) {
        Debug.print_log(e2,sqlArr[0]);
        return -1;
      }
    } finally {
      this.disconnect(stmt, conn);
    }

  }

  /**
   * 更新Blob类型查询结果
   * @param sql
   * @param columnName
   * @param sis
   */
  public void updateBlob(String sql, String sqlBlob, String columnName, ServletInputStream sis, int fileSize) {
    Connection conn = null;
    ResultSet rs = null;
    Statement stmt = null;
    BufferedInputStream bis = null;
    BufferedOutputStream bos = null;
    oracle.sql.BLOB content = null;
    try {
      conn = getConnection();
      conn.setAutoCommit(false);
      stmt = conn.createStatement();
      stmt.executeUpdate(sql);
      rs = stmt.executeQuery(sqlBlob);
      while (rs.next()) {
        content = (oracle.sql.BLOB)rs.getBlob(columnName);
      }
      bos = new BufferedOutputStream(content.getBinaryOutputStream());
      bis = new BufferedInputStream(sis);
      byte[] buffer = new byte[ content.getBufferSize() ];
      int bytesRead = 0;
      int hasRead = 0;
      while( ( bytesRead = bis.read( buffer ) ) != -1 ) {
        hasRead += bytesRead;
        if (hasRead > fileSize) {
          bytesRead = bytesRead - (hasRead - fileSize);
        }
        bos.write( buffer, 0, bytesRead );
      }
      bos.flush();
      bos.close();
      conn.commit();
      conn.setAutoCommit(true);
    } catch(Exception e1) {
      Debug.print_log(e1,sql);
      try {
        conn.rollback();
        conn.setAutoCommit(true);
        bos.flush();
        bos.close();
      } catch (Exception e2) {
        Debug.print_log(e2,sql);
      }
    } finally {
      this.disconnect(rs, stmt, conn);
    }
  }

  /**
   * 返回Blob类型查询结果
   * @param sql
   * @param columnName
   * @return
   */
  public Blob getBlob(String sql, String columnName) {
    Connection conn = null;
    ResultSet rs = null;
    Statement stmt = null;
    Blob blob = null;
    try {
      conn = getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery(sql);
      while (rs.next()) {
        blob = rs.getBlob(columnName);
      }
    } catch(Exception e) {
      Debug.print_log(e,sql);
    } finally {
      this.disconnect(rs, stmt, conn);
    }
    return blob;
  }

  /**
   * 返回一行一列查询结果
   * @param sql
   * @param columnName
   * @return
   */
  public String getSnglRowSnglCol(String sql, String columnName) {
    Connection conn = null;
    ResultSet rs = null;
    Statement stmt = null;
    String str = "";
    try {
      conn = getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery(sql);
      ResultSetMetaData rsmd = rs.getMetaData();
      String colType = rsmd.getColumnTypeName(1);
      while (rs.next()) {
        if (colType.equals("CLOB")) {
          Clob clob = rs.getClob(columnName);
          str = clob.getSubString(1,(int)clob.length());
        } else {
          str = rs.getString(columnName);
        }
      }
    } catch(Exception e) {
      Debug.print_log(e,sql);
    } finally {
      this.disconnect(rs, stmt, conn);
    }
    return str;
  }

  /**
   * 返回一行多列查询结果
   * @param sql
   * @param columnName
   * @return
   */
  public String[] getSnglRowMultiCol(String sql, String[] colArr) {
    Connection conn = null;
    ResultSet rs = null;
    Statement stmt = null;
    Vector v = new Vector();
    String[] colType = new String[colArr.length];
    try {
      conn = getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery(sql);
      ResultSetMetaData rsmd = rs.getMetaData();
      for (int i = 1; i <= rsmd.getColumnCount(); i++) {
        colType[i-1] = rsmd.getColumnTypeName(i);
      }
      while (rs.next()) {
        for (int i = 0; i < colArr.length; i++) {
          if (colType[i].equals("CLOB")) {
            Clob clob = rs.getClob(colArr[i]);
            v.addElement(clob.getSubString(1,(int)clob.length()));
          } else {
            v.addElement(rs.getString(colArr[i]));
          }
        }
      }
    } catch(Exception e) {
      Debug.print_log(e,sql);
    } finally {
      this.disconnect(rs, stmt, conn);
    }
    return this.cvtVtrToArr(v);
  }

  /**
   * 返回多行一列查询结果
   * @param sql
   * @param columnName
   * @return
   */
  public String[] getMultiRowSnglCol(String sql, String columnName) {
    Connection conn = null;
    ResultSet rs = null;
    Statement stmt = null;
    Vector v = new Vector();
    try {
      conn = getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery(sql);
      ResultSetMetaData rsmd = rs.getMetaData();
      String colType = rsmd.getColumnTypeName(1);
      while (rs.next()) {
        if (colType.equals("CLOB")) {
          Clob clob = rs.getClob(columnName);
          v.addElement(clob.getSubString(1,(int)clob.length()));
        } else {
          v.addElement(rs.getString(columnName));
        }
      }
    } catch(Exception e) {
      Debug.print_log(e,sql);
    } finally {
      this.disconnect(rs, stmt, conn);
    }
    return this.cvtVtrToArr(v);
  }

  /**
   * 返回多行多列查询结果
   * @param sql
   * @param colArr
   * @return
   */
  public String[][] getMultiRowMultiCol(String sql, String colArr[]) {
    Connection conn = null;
    ResultSet rs = null;
    Statement stmt = null;
    Vector v = new Vector();
    int colCount = colArr.length;
    String[] colType = new String[colCount];
    try {
      conn = getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery(sql);
      ResultSetMetaData rsmd = rs.getMetaData();
      for (int i = 1; i <= rsmd.getColumnCount(); i++) {
        colType[i-1] = rsmd.getColumnTypeName(i);
      }
      while (rs.next()) {
        String[] arr = new String[colCount];
        for (int i = 0; i < colCount; i++) {
          if ("CLOB".equals(colType[i])) {
            Clob clob = rs.getClob(colArr[i]);
            arr[i] = clob.getSubString(1,(int)clob.length());
          } else {
          try{
            arr[i] = rs.getString(colArr[i]);
           }catch(Exception e) {
      Debug.print_log(e,sql+colArr[i]);
    } 
          }
        }
        v.addElement(arr);
      }
    } catch(Exception e) {
      Debug.print_log(e,sql);
    } finally {
      this.disconnect(rs, stmt, conn);
    }
    return this.cvtVtrToArr(v, colCount);
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
      conn = getConnection();
      stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
      rs   = stmt.executeQuery(sql);
      if (rs != null) {
        if (rs.next()) {
          rs.last();
          count = rs.getRow();
        }
      }
    } catch(Exception e) {
      Debug.print_log(e,sql);
    } finally {
      this.disconnect(rs, stmt, conn);
    }
    return count;
  }

  /**
   * 返回sql语句中用到的字段的名称数组
   * @param sql
   * @return
   */
  public String[] getColNames(String sql) {
    Connection conn = null;
    ResultSet rs = null;
    Statement stmt = null;
    Vector v = new Vector();
    StringBuffer s = new StringBuffer();
    try {
      conn = getConnection();
      stmt = conn.createStatement();
      rs   = stmt.executeQuery(sql);
      ResultSetMetaData rsmd = rs.getMetaData();
      for (int i = 1; i <= rsmd.getColumnCount(); i++) {
        v.addElement(rsmd.getColumnName(i));
      }
    } catch(Exception e) {
      Debug.print_log(e,sql);
    } finally {
      this.disconnect(rs, stmt, conn);
    }
    return this.cvtVtrToArr(v);
  }

  /**
   * 返回字段对应的字段类型
   * @param tblName 表名
   * @param Id 表的Id字段
   * @param names 字段组成的向量
   * @return
   */
  public String[] getColTypes(String tblName, String[] colNames) {
    Connection conn = null;
    ResultSet rs = null;
    Statement stmt = null;
    StringBuffer strBuff = new StringBuffer();
    int k = 0;
    String[] colTypes = new String[colNames.length];
    String[] selColName = {"column_name","data_type"};
    strBuff.append("select column_name,data_type from user_tab_columns where table_name='");
    strBuff.append(tblName.toUpperCase());
    strBuff.append("' and (column_name in (");
    for (int i = 0; i < colNames.length; i++) {
      strBuff.append("'");
      strBuff.append(colNames[i].toUpperCase());
      strBuff.append("',");
    }
    strBuff.deleteCharAt(strBuff.length()-1);
    strBuff.append("))");
    String sql = strBuff.toString();
    // 字段类型数组，包含Id字段
    String[][] colNameType = this.getMultiRowMultiCol(sql,selColName);
    // 把从数据字典中查询出的字段名与字段类型数组中查到的与传入的字段名数组相对应的字段类型放入新的字段类型数组中
    for (int i = 0; i < colNames.length; i++) {
      for (int j = 0; j < colNameType.length; j++) {
        if (colNames[i].toUpperCase().equals(colNameType[j][0])) {
          colTypes[k] = colNameType[j][1];
          k++;
        }
      }
    }
    return colTypes;
  }

  /**
   * 返回表的所有的字段名和类型
   * @param tblName 表名
   * @return
   */
  public String[][] getColumnsTypes(String tblName) {
    Connection conn = null;
    ResultSet rs = null;
    Statement stmt = null;
    StringBuffer strBuff = new StringBuffer();
    strBuff.append("select column_name,data_type from user_tab_columns where table_name='");
    strBuff.append(tblName.toUpperCase());
    strBuff.append("'");
    String sql = strBuff.toString();
    String[] colArr = this.getColNames(sql);
    String[][] ColumnsTypes = this.getMultiRowMultiCol(sql, colArr);
    return ColumnsTypes;
  }

  /**
   * 返回所有表的所有的字段，按照表的先后顺序连接
   * @param tblName 表名数组
   * @return  所有的表明字段
   */
  public String[] getColumns(String[] tblName) {
    String[] result = null;
    Vector colNames = new Vector();
    Oracle oracle = new Oracle();
    for (int i = 0; i < tblName.length; i++) {
      result = oracle.getColumns(tblName[i]);
      for (int j = 0; j < result.length; j++) {
        colNames.addElement(result[j]);
      }
    }
    result = oracle.cvtVtrToArr(colNames);
    return result;
  }

  /**
   * 返回表的所有的字段
   * @param tblName 表名
   * @return
   */
  public String[] getColumns(String tblName) {
    Connection conn = null;
    ResultSet rs = null;
    Statement stmt = null;
    StringBuffer strBuff = new StringBuffer();
    strBuff.append("select column_name from user_tab_columns where table_name='");
    strBuff.append(tblName.toUpperCase());
    strBuff.append("'");
    String sql = strBuff.toString();
    String[] columns = this.getMultiRowSnglCol(sql, "COLUMN_NAME");
    return columns;
  }


  /**
   * 返回表的所有的主键字段
   * @param tblName 表名
   * @return主键字段数组
   */
  public String[] getPKColumns(String tblName) {
    Connection conn = null;
    ResultSet rs = null;
    Statement stmt = null;
    StringBuffer strBuff = new StringBuffer();
    strBuff.append("select column_name from user_cons_columns a,user_constraints b where a.constraint_name=b.constraint_name and constraint_type='P' and table_name='");
    strBuff.append(tblName.toUpperCase());
    strBuff.append("'");
    String sql = strBuff.toString();
    String[] columns = this.getMultiRowSnglCol(sql, "COLUMN_NAME");
    return columns;
  }

  /**
   * 返回表的所有的非主键字段
   * @param tblName 表名
   * @return非主键字段数组
   */
  public String[] getNotPKColumns(String tblName) {
    Connection conn = null;
    ResultSet rs = null;
    Statement stmt = null;
    StringBuffer strBuff = new StringBuffer();
    strBuff.append("select column_name from user_cons_columns a,user_constraints b where a.constraint_name=b.constraint_name and constraint_type<>'P' and table_name='");
    strBuff.append(tblName.toUpperCase());
    strBuff.append("'");
    String sql = strBuff.toString();
    String[] columns = this.getMultiRowSnglCol(sql, "COLUMN_NAME");
    return columns;
  }

  /**
   * 将Vector转换为一个一维数组
   * @param v
   * @param colCount
   * @return
   */
  public String[] cvtVtrToArr(Vector v) {
    Object[] obj = v.toArray();
    int rowCount = obj.length;
    String[] arr = new String[rowCount];
    for (int i = 0; i < rowCount; i++) {
      arr[i] = (String)obj[i];
    }
    return arr;
  }

  /**
   * 将Vector转换为一个二维数组
   * @param v
   * @param colCount
   * @return
   */
  public String[][] cvtVtrToArr(Vector v, int colCount) {
    Object[] obj = v.toArray();
    int rowCount = obj.length;
    String[][] arr = new String[rowCount][colCount];
    for (int i = 0; i < rowCount; i++) {
      arr[i] = (String[])obj[i];
    }
    return arr;
  }

  /**
   * 将数组中的null转换为""，并返回转换后的数组
   * @param arr
   */
  public String[] cvtNullToBlank(String[] arr) {
    for (int i = 0; i < arr.length; i++) {
      if (arr[i] == null) {
        arr[i] = "";
      }
    }
    return arr;
  }

  /**
   * 将数组中的null转换为""，并返回转换后的数组
   * @param arr
   */
  public String[][] cvtNullToBlank(String[][] arr) {
    for (int i = 0; i < arr.length; i++) {
      for (int j = 0; j < arr[i].length; j++) {
        if (arr[i][j] == null) {
          arr[i][j] = "";
        }
      }
    }
    return arr;
  }

  /**
   * 断开与数据库的连接
   * @param rs
   * @param stmt
   * @param conn
   */
  public void disconnect(ResultSet rs, Statement stmt, Connection conn) {
    try {
      if(rs != null){  
        rs.close();
      }
    }catch(Exception e1){
        Debug.print_log(e1);
    }try{
      if(stmt != null){
        stmt.close();
      }
    }catch(Exception e2){
        Debug.print_log(e2);
    }try{
      if(conn != null && !conn.isClosed()){
        conn.close();
      }
    } catch (Exception e3) {
      Debug.print_log(e3);
    }
  }

  /**
   * 断开与数据库的连接
   * @param stmt
   * @param conn
   */
  public void disconnect(Statement stmt, Connection conn) {
    try {
        if(stmt != null){
          stmt.close();
        }
    } catch (Exception e1) {
      Debug.print_log(e1);
    }try{
       if(conn != null && !conn.isClosed()){
        conn.close();
      }
    } catch (Exception e2) {
      Debug.print_log(e2);
    }
  }

  /**
   * 从连接池中返回一个数据库连接
   * @return
   */
  public static Connection getConnection() {
    Connection connection = null;
    try {
      InitialContext initContext = new InitialContext();
      String strDataSourceName = "jdbc/intraweb";
      DataSource dsOracle = (DataSource)initContext.lookup("java:comp/env/jdbc/intraweb");
      connection = dsOracle.getConnection();
    } catch(Exception e) {
      Debug.print_log(e);
    }
    return connection;
  }
  
}
