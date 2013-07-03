package cn.com.jnpc.meeting.dao.util;
/**
 * <p>Title: 自动生成SQL语句的Java Bean</p>
 * <p>Description: 自动生成SQL语句，与Oracle结合使用，与业务无关，也可用于其他项目，可重用度极高！</p>
 * <p>Company: 大连超维计算机技术有限公司</p>
 * @author 崔冠宇
 * @version 1.0
 */

import java.util.*;


public class SqlGenerator extends Oracle {
  public SqlGenerator(){
    super();
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
      if (colValues[i]!=null && !colValues[i].equals("")) { // 如果字段对应的值不为null

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
          strBuff.append("','YYYY-MM-DD HH24:MI')");
        }

        if (colTypes[i].equalsIgnoreCase("CLOB")) {
          //strBuff.append("'");
          //strBuff.append(colValues[i]);
          //strBuff.append("'");
          strBuff.append("EMPTY_CLOB()");
        }

        if (colTypes[i].equalsIgnoreCase("BLOB")) {
          strBuff.append("EMPTY_BLOB()");
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
        strBuff.append("','YYYY-MM-DD')");
      }

      if (colTypes[i].equalsIgnoreCase("CLOB")) {
        //strBuff.append("'");
        //strBuff.append(colValues[i]);
        //strBuff.append("'");
        strBuff.append("EMPTY_CLOB()");
      }

      if (colTypes[i].equalsIgnoreCase("BLOB")) {
        strBuff.append("EMPTY_BLOB()");
      }

    } else { // 如果字段对应的值为null
      strBuff.append("null");
    }
     // 外层if语句结束

    strBuff.append(")");

    return strBuff.toString();
  }

  /**
   * 通过表名、主键字段名、主键值、字段名称数组、字段类型数组、以及对应值数组生成一条insert语句
   * @param tblName 表名
   * @param PKField 主键字段名
   * @param PKValue 主键值
   * @param colNames 字段名称数组
   * @param colTypes 对应的字段类型数组
   * @param colValues 字段对应值的数组合
   * @return
   */
  public String genInsStmt(String tblName, String PKColumn, String PKValue,
    String[] colNames, String[] colValues) {
    String[] colTypes = this.getColTypes(tblName, colNames);
    StringBuffer strBuff = new StringBuffer();
    strBuff.append("insert into ");
    strBuff.append(tblName);
    strBuff.append("(" + PKColumn + ",");

    for (int i = 0; i < (colNames.length - 1); i++) {
      strBuff.append(colNames[i]);
      strBuff.append(",");
    }

    strBuff.append(colNames[colNames.length - 1]);
    strBuff.append(") values (" + PKValue + ",");

    for (int i = 0; i < (colTypes.length - 1); i++) {
      if (colValues[i] != null && !colValues[i].equals("") ) { // 如果字段对应的值不为null

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
          strBuff.append("','YYYY-MM-DD HH24:MI')");
        }

        if (colTypes[i].equalsIgnoreCase("CLOB")) {
          // strBuff.append("'");
          // strBuff.append(colValues[i]);
          // strBuff.append("'");
          strBuff.append("EMPTY_CLOB()");
        }

        if (colTypes[i].equalsIgnoreCase("BLOB")) {
          strBuff.append("EMPTY_BLOB()");
        }

      } else { // 如果字段对应的值为null
        strBuff.append("null");
      }
       // 外层if语句结束

      strBuff.append(",");
    }
     // for循环结束

    int i = colTypes.length - 1;

    if (colValues[i] != null && !colValues[i].equals("")) { // 如果字段对应的值不为null
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
        strBuff.append("','YYYY-MM-DD')");
      }

      if (colTypes[i].equalsIgnoreCase("CLOB")) {
      //  strBuff.append("'");
      //  strBuff.append(colValues[i]);
      //  strBuff.append("'");
        strBuff.append("EMPTY_CLOB()");
      }

      if (colTypes[i].equalsIgnoreCase("BLOB")) {
        strBuff.append("EMPTY_BLOB()");
      }

    } else { // 如果字段对应的值为null
      strBuff.append("null");
    }
     // 外层if语句结束

    strBuff.append(")");
    return strBuff.toString();
  }

  /**
   * 返回一条Update语句
   * @param tblName
   * @param id
   * @param idVal
   * @param colNames
   * @param colTypes
   * @param colValues
   * @return
   */
  public String genUpdateStmt(String tblName, String id, String idVal,
    String[] colNames, String[] colValues) {
    String[] colTypes = this.getColTypes(tblName, colNames);
    StringBuffer strBuff = new StringBuffer();
    strBuff.append("update ");
    strBuff.append(tblName);
    strBuff.append(" set ");

    for (int i = 0; i < (colTypes.length - 1); i++) {
      strBuff.append(colNames[i]);
      strBuff.append("=");

      if (colValues[i] != null && !colValues[i].equals("")) { // 如果字段对应的值不为null

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
          strBuff.append("','YYYY-MM-DD')");
        }

        if (colTypes[i].equalsIgnoreCase("CLOB")) {
          //          strBuff.append("'");
          //          strBuff.append(colValues[i]);
          //          strBuff.append("'");
          strBuff.append("EMPTY_CLOB()");
        }

        if (colTypes[i].equalsIgnoreCase("BLOB")) {
          strBuff.append("EMPTY_BLOB()");
        }

      } else { // 如果字段对应的值为null
        strBuff.append("null");
      }
       // 外层if语句结束

      strBuff.append(",");
    }
     // for循环结束

    int i = colTypes.length - 1;
    strBuff.append(colNames[i]);
    strBuff.append("=");

    if (colValues[i] != null && !colValues[i].equals("")) { // 如果字段对应的值不为null

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
        strBuff.append("','YYYY-MM-DD')");
      }

      if (colTypes[i].equalsIgnoreCase("CLOB")) {
        // strBuff.append("'");
        // strBuff.append(colValues[i]);
        // strBuff.append("'");
        strBuff.append("EMPTY_CLOB()");
      }

      if (colTypes[i].equalsIgnoreCase("BLOB")) {
        strBuff.append("EMPTY_BLOB()");
      }

    } else { // 如果字段对应的值为null
      strBuff.append("null");
    }
     // 外层if语句结束

    strBuff.append(" where ");
    strBuff.append(id);
    strBuff.append("=");
    strBuff.append(idVal);

    return strBuff.toString();
  }

  /**
   * 返回一条特定日期格式的Update语句
   * @param tblName
   * @param id
   * @param idVal
   * @param colNames
   * @param colTypes
   * @param colValues
   * @param dateFormat
   * @return
   */
  public String genDateUpdateStmt(String tblName, String id, String idVal,
    String[] colNames, String[] colValues, String dateFormat) {
    String[] colTypes = this.getColTypes(tblName, colNames);
    StringBuffer strBuff = new StringBuffer();
    strBuff.append("update ");
    strBuff.append(tblName);
    strBuff.append(" set ");

    for (int i = 0; i < (colTypes.length - 1); i++) {
      strBuff.append(colNames[i]);
      strBuff.append("=");

      if (colValues[i] != null && !colValues[i].equals("")) { // 如果字段对应的值不为null

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
          strBuff.append("','");
          strBuff.append(dateFormat);
          strBuff.append("')");
        }

        if (colTypes[i].equalsIgnoreCase("CLOB")) {
          //          strBuff.append("'");
          //          strBuff.append(colValues[i]);
          //          strBuff.append("'");
          strBuff.append("EMPTY_CLOB()");
        }

        if (colTypes[i].equalsIgnoreCase("BLOB")) {
          strBuff.append("EMPTY_BLOB()");
        }

      } else { // 如果字段对应的值为null
        strBuff.append("null");
      }
       // 外层if语句结束

      strBuff.append(",");
    }
     // for循环结束

    int i = colTypes.length - 1;
    strBuff.append(colNames[i]);
    strBuff.append("=");

    if (colValues[i] != null && !colValues[i].equals("")) { // 如果字段对应的值不为null

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
        strBuff.append("','");
        strBuff.append(dateFormat);
        strBuff.append("')");
      }

      if (colTypes[i].equalsIgnoreCase("CLOB")) {
        // strBuff.append("'");
        // strBuff.append(colValues[i]);
        // strBuff.append("'");
        strBuff.append("EMPTY_CLOB()");
      }

      if (colTypes[i].equalsIgnoreCase("BLOB")) {
        strBuff.append("EMPTY_BLOB()");
      }

    } else { // 如果字段对应的值为null
      strBuff.append("null");
    }
     // 外层if语句结束

    strBuff.append(" where ");
    strBuff.append(id);
    strBuff.append("=");
    strBuff.append(idVal);

    return strBuff.toString();
  }
  
  /**
   * 产生具有双外键的update语句
   * @param tblName 表名
   * @param firstId 关键字1
   * @param firstIdVal 关键字1值
   * @param secondId 关键字2
   * @param secondIdVal 关键字2值
   * @param colNames 列名不含关键字
   * @param colTypes 列类型
   * @param colValues 列值
   * @return
   */
  public String genUpdateStmt(String tblName, String firstId,
    String firstIdVal, String secondId, String secondIdVal, String[] colNames,
    String[] colValues) {
    String[] colTypes = this.getColTypes(tblName, colNames);
    StringBuffer strBuff = new StringBuffer();
    strBuff.append("update ");
    strBuff.append(tblName);
    strBuff.append(" set ");

    for (int i = 0; i < (colTypes.length - 1); i++) {
      strBuff.append(colNames[i]);
      strBuff.append("=");

      if (colValues[i] != null && !colValues[i].equals("")) { // 如果字段对应的值不为null

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
          strBuff.append("','YYYY-MM-DD')");
        }

        if (colTypes[i].equalsIgnoreCase("CLOB")) {
          // strBuff.append("'");
          // strBuff.append(colValues[i]);
          // strBuff.append("'");
          strBuff.append("EMPTY_CLOB()");
        }

        if (colTypes[i].equalsIgnoreCase("BLOB")) {
          strBuff.append("EMPTY_BLOB()");
        }


      } else { // 如果字段对应的值为null
        strBuff.append("null");
      }
       // 外层if语句结束

      strBuff.append(",");
    }
     // for循环结束

    int i = colTypes.length - 1;
    strBuff.append(colNames[i]);
    strBuff.append("=");

    if (colValues[i] != null && !colValues[i].equals("")) { // 如果字段对应的值不为null

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
        strBuff.append("','YYYY-MM-DD')");
      }

      if (colTypes[i].equalsIgnoreCase("CLOB")) {
        // strBuff.append("'");
        // strBuff.append(colValues[i]);
        // strBuff.append("'");
        strBuff.append("EMPTY_CLOB()");
      }

      if (colTypes[i].equalsIgnoreCase("BLOB")) {
        strBuff.append("EMPTY_BLOB()");
      }


    } else { // 如果字段对应的值为null
      strBuff.append("null");
    }
     // 外层if语句结束

    strBuff.append(" where ");
    strBuff.append(firstId);
    strBuff.append("=");
    strBuff.append(firstIdVal);
    strBuff.append(" and ");
    strBuff.append(secondId);
    strBuff.append("='");
    strBuff.append(secondIdVal);
    strBuff.append("'");

    return strBuff.toString();
  }
}
