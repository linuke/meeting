package cn.com.jnpc.meeting.dao.util;

import java.io.*;
import java.lang.reflect.*;
import java.sql.*;
import java.util.*;

public class Global {
  /**
   * @param      sql
   * @call       none
   * @return     处理好的SQL语句
   * @function   过滤处SQL语句中的单引号，转成''
   * @date
   * @author
   * @modify
   */
  public static String sqlFilter(String sql) {
    String sqlStr = ""; 	//存储处理的SQL语句
    String tmpContext = ""; //存储临时处理好的语句
    String firstStr;  		//单引号前的语句
    String lastStr; 		//单引号后的语句
    char   oldStr = 39; 	//"'"的ASCII码
    int pos; 				//定义单引号在句中的位置
    sqlStr = sql.trim(); 	//去掉SQL语句中的空格
    if (sqlStr == null) {
      /*空语句，直接返回*/
      return "";
    }
    pos = sqlStr.indexOf(oldStr); //得到第一个单引号在句中的位置
    while(pos>=0) {
      /*找到每一个存在的单引号，直到句中不存在单引号为止*/
      firstStr   = sqlStr.substring(0,pos+1); //得到单引号前的语句
      lastStr    = sqlStr.substring(pos+1,sqlStr.length()); //得到单引号后的语句
      tmpContext = tmpContext+firstStr+oldStr; //临时处理好的语句
      sqlStr     = lastStr; //下一次要处理的语句
      pos        = sqlStr.indexOf(oldStr); //得到下一个单引号的位置
    }
    sqlStr = tmpContext+sqlStr;  //得到处理好的语句
    return sqlStr;  //返回处理好的语句
  }

/**
 * @param      type,context
 *             type:
 *             context:需要转换的字符串
 * @call       none
 * @return     转换后的字符串
 * @function   把在WML/HTML中的保留字符专成对应的取代字符。
 * @call by
 * @date       2001.10.24
 * @author
 * @modify
 */
public static String getContextConvert(String context) {
  String tmpContext = ""; 	//在转换中间，临时存放己经转换好的字符串
  String firstStr; 			//存要转换的字符串中第一个遇到的要转换字符前的字符串
  String lastStr;  			//存要转换的字符串中第一个遇到的要转换字符后的字符串
  String tmpStr="";  			//存可能含有要转换字符的字符串
  String convertStr = ""; 	//转换成的自符
  int pos; 					//找到的要转换字符在整个字符串中的位置
  //char[] chararry={'&','<','>','$','"','\''};
  //char[] chararry={'&','<','>','"','\''};	//要转换的字符
  char[] chararry={'&','<','>','"'};	//要转换的字符
  if (context==null)
     return "";
  tmpStr=context.trim(); //去掉字符串中的空格
  if (tmpStr.equals(""))
    return "";
  for (int i=0;i<4;i++) {
    /*否则，依次查找字符串中是否含有要转换字符数组中的字符*/
    pos=tmpStr.indexOf(chararry[i]); //确定第一次遇到的当前要转换的字符在字符串中的位置
    while(pos>=0) {
      /*此字符串中含有要转换的字符，则继续循环*/
      firstStr   = tmpStr.substring(0,pos); //得到在整个字符串中，第一个遇到的当前要转换字符前的字符串
      lastStr    = tmpStr.substring(pos+1,tmpStr.length()); //得到在整个字符串中，第一个遇到的当前要转换字符后的字符串
      switch (i) {
        /*转换成对应的字符*/
        case 0:
          convertStr = "&amp;";
          break;
        case 1:
          convertStr = "&lt;";
          break;
        case 2:
          convertStr = "&gt;";
          break;
        case 3:
          convertStr = "&quot;";
          break;
        }
      tmpContext = tmpContext+firstStr+convertStr; //始终存有转换好的字符串
      tmpStr = lastStr;  //始终存有未做转换的字符串
      pos = tmpStr.indexOf(chararry[i]);  //确定下一个遇到的当前要转换的字符在字符串中的位置
      } //end while(字符串中含有当前要转化的字符）
      tmpContext = tmpContext+tmpStr; /*已经将上一个要转换字符转换好后的字符串*/
       tmpStr = tmpContext;  /*将其作为新的未转换的字符串，用于转换下一个要转换的字符*/
      tmpContext = ""; //将存有转换好的字符串的字符串置空，用作下一次的开始
  }
  return tmpStr;  //返回转换好的字符串
  }

 /**
   * @param      id
   * @call       none
   * @return     处理好的id
   * @function   对字符串id进行加一处理
   * @date
   * @author
   * @modify
   */
  public String idAddOne(String id) {
    try {
      int i = Integer.parseInt(id);
      i = i + 1;
      String s = Integer.toString(i);
      int k = 9 - s.length();
      for (int j=0;j<k;j++) {
        s = "0"+ s ;
      }
      return s;
      } catch (Exception e){
          Debug.print_log(e);
          return null;
      }
  }

  public String changeDate(java.sql.Date date,String time) {
    try {
      Calendar cal = Calendar.getInstance();
      cal.setTime(date);
      String s = Integer.toString(cal.get(Calendar.YEAR)) + "-";
      s = s + Integer.toString(cal.get(Calendar.MONTH) + 1) + "-";
      s = s + Integer.toString(cal.get(Calendar.DAY_OF_MONTH));
      StringTokenizer tokenizer = new StringTokenizer(time," ");
      tokenizer.nextToken();
      String t = tokenizer.nextToken();
      tokenizer = new StringTokenizer(t,":");
      s = s + " " + tokenizer.nextToken();
      s = s + ":" + tokenizer.nextToken();
      return s;
    } catch (Exception e){
        Debug.print_log(e);
        return null;
    }
  }

 /**
   * @param      comm
   * @call       none
   * @return     处理好的comm
   * @function   对字符串comm进行加一处理
   * @date
   * @author
   * @modify
   */
  public String commAddOne(String comm) {
    try {
      int i = Integer.parseInt(comm);
      i = i + 1;
      String s = Integer.toString(i);
      int k = 5 - s.length();
      for (int j=0;j<k;j++) {
        s = "0"+ s ;
      }
      return s;
      } catch (Exception e){
          Debug.print_log(e);
          return null;
      }
  }

 /**
   * @param      meetnum
   * @call       none
   * @return     处理好的meetnum
   * @function   为会议纪要号加一
   * @date
   * @author
   * @modify
   */
  public String meetnumAddOne(String meetnum) {
    try {
      int i = Integer.parseInt(meetnum);
      i = i + 1;
      String s = Integer.toString(i);
      int k = 3 - s.length();
      for (int j=0;j<k;j++){
        s = "0"+ s ;
      }
      return s;
      } catch (Exception e){
          Debug.print_log(e);
          return null;
      }
  }

  public static String[][] CreateStringArrayFromResultSet(ResultSet rs, int[] SizeOfResultSet) {
    String[][] result = null;
    String[][] TemporaryResult = null;
    int MAX_SIZE_CANDIDATE = 1000;
    try{
      ResultSetMetaData rsmd = rs.getMetaData();
      int numberOfColumns = rsmd.getColumnCount();
      TemporaryResult = new String[MAX_SIZE_CANDIDATE + 1][numberOfColumns];
      for (int i = 1; i <= numberOfColumns; i++) {
        String columnName = rsmd.getColumnName(i);
        TemporaryResult[0][i-1] = columnName;
      }
      int icount = 1;
      while (rs.next()) {
        for (int i = 1; i <= numberOfColumns; i++) {
        String columnValue = rs.getString(i);
        TemporaryResult[icount][i-1] = columnValue;
      }
      icount++;
      }//END of while
      int numberOfRows = icount;
      // copy the size of ResultSet
      SizeOfResultSet[0] = numberOfColumns;
      SizeOfResultSet[1] = numberOfRows;
      // copy to result array from temporary array.
      result = new String[numberOfRows + 1][numberOfColumns];
      for (int i = 0; i <= numberOfRows; i++) {
        for (int j = 0; j < numberOfColumns; j++) {
          result[i][j]=TemporaryResult[i][j];
        }
      }
    }catch(SQLException e) {
      Debug.print_log(e);
    }
     return result;
  }
}

