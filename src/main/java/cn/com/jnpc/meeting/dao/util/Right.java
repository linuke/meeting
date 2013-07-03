package cn.com.jnpc.meeting.dao.util;

/**
 * <p>Title: 权限维护</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * @author not attributable
 * @version 1.0
 */
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.naming.InitialContext;
import javax.sql.DataSource;

public class Right {
  private Connection conn = null;
  private DataSource ds = null;
  private InitialContext ctx = null;
  private ResultSet rs = null;
  private Statement st = null;
  public Right() {
  }
  public int getEmpNo(String user_id) {
    String[] result = null;
    OracleConnection oracleConn = new OracleConnection();
   // 定义查询表中字段的sql语句
   StringBuffer strBuffer = new StringBuffer();
   strBuffer.append("select count(userid) user_id from st_rightuser where userid=");
   strBuffer.append("'");
   strBuffer.append(user_id);
   strBuffer.append("'");

    String sql = strBuffer.toString();
    int flag = Integer.parseInt(oracleConn.getSnglRowSnglCol(sql,"user_id",0));
    return flag;
  }

  /**
   * 根据员工编号获得该员工密码
   * @param empNo 员工编号
   * @return 员工密码
   */
  public String getEmpPassword(String user_id) {
    String password = "";
    OracleConnection oracleConn = new OracleConnection();
    // 定义查询表中字段的sql语句
    StringBuffer strBuffer = new StringBuffer();
    strBuffer.append("select passwd from st_rightuser where userid=");
    strBuffer.append("'");
    strBuffer.append(user_id);
    strBuffer.append("'");
    String sql = strBuffer.toString();
    // 执行一行一列查询并返回结果
    password = oracleConn.getSnglRowSnglCol(sql, "passwd",0);
    return password;
  }
  public Statement DBInit() throws java.lang.Exception {
    //初始化数据库
    ctx=new InitialContext();
        String dsName = "jdbc/jnpc";
    
        ds = (javax.sql.DataSource) ctx.lookup("java:comp/env/" + dsName);
    conn=ds.getConnection();
    st=conn.createStatement(java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE,java.sql.ResultSet.CONCUR_READ_ONLY);
    return st;
  }

  /*
   * 用户时候有添加权限
   * @param:   userid :用户登陆编号
   * @param:   menuId :模块编号
   * @return   返回值， 有添加权限返回True,没有添加权限返回False
   */
  public boolean isHaveAdd(String userId,int menuId) {
    return isHaveRight(userId,menuId,1);
  }
  /*
   * 用户是否有修改权限
   * @param:   userid :用户登陆编号
   * @param:   menuId :模块编号
   * @return   返回值， 有修改权限返回True,没有修改权限返回False
   */
  public boolean isHaveModify(String userId,int menuId) {
    return isHaveRight(userId,menuId,2);
  }

  /*
   * 用户是否有删除权限
   * @param:   userid :用户登陆编号
   * @param:   menuId :模块编号
   * @return   返回值， 有删除权限返回True,没有删除权限返回False
   */
  public boolean isHaveDelete(String userId,int menuId) {
    return isHaveRight(userId,menuId,4);
  }

  /*
  * 用户登录身份验证
  * @param:   userid :用户登陆编号
  * @param:   passwd :用户登陆密码
  * @return   返回值， 验证正确返回1,不正确返回0，数据库错误返回-1
  */
 public boolean isHaveRight(String userid,int menuId,int flag) {
   // 用户没有权限
   boolean reHaveRight = false;
   StringBuffer strBuffer = new StringBuffer();
   try {
     SqlGenerator oracle = new SqlGenerator();
     // 设置查询登陆人在当前模块的权限
     strBuffer.append("select right_code from menu_user ");
     strBuffer.append("where menu_id=");
     strBuffer.append(menuId);
     strBuffer.append(" and user_id='");
     strBuffer.append(userid);
     strBuffer.append("'");
     // 取得登陆人在当前模块的权限Code
     String rightCode = oracle.getSnglRowSnglCol(strBuffer.toString(),"right_code");
     switch (flag) {
       case 1:
         if (rightCode.equals("1") || rightCode.equals("3") ||
             rightCode.equals("5") || rightCode.equals("7")) {
           // 解析责任人read权限
           reHaveRight = true;
         }
         break;
       case 2:
         if (rightCode.equals("2") || rightCode.equals("3") ||
             rightCode.equals("6") || rightCode.equals("7")) {
           // 解析责任人modify权限
           reHaveRight = true;
         }
         break;
       case 4:
         if (rightCode.equals("4") || rightCode.equals("5") ||
             rightCode.equals("6") || rightCode.equals("7")) {
           // 解析责任人modify权限
           reHaveRight = true;
         }
         break;
     }
   } catch (Exception e){
     System.out.println("RightVerify.java-->isHaveAdd() e1:" + e.getMessage());
   }
   return reHaveRight ;
 }

  public String computeDigest(String msg){
    try {
      java.security.MessageDigest alg = java.security.MessageDigest.getInstance("SHA-1");
      alg.reset();
      alg.update(msg.getBytes());
      byte[] hash = alg.digest();
      String digest = "";
      for (int i = 0;i < hash.length;i++){
        int v = hash[i] & 0xFF;
        if( v < 16 ) digest += "0";
        digest += Integer.toString(v,16).toUpperCase();
      }
      return digest;
    }catch (Exception e) {
      Debug.print_log(e);
      return msg;
    }
  }


  //得到某人在某系统下的所有权限，包括代理的权限,和公有权限
  public Vector<String> getRights(String userID,String systemID) {
    String sqlStr = "";
    String all = "";
    Vector<String> rightIDs = new Vector<String>();
    try {
      DBInit();

      //从用户权限表里得到通过角色赋给用户的权限和接受的代理权限
      sqlStr = " select r.define from st_role r,st_user_role u where r.roleid=u.roleid"
             + " and (u.userid = '"+Global.sqlFilter(userID)+"' or u.proxy_userid='"+Global.sqlFilter(userID)+"') "
             + " and subStr(r.define,1,2)= '"+Global.sqlFilter(systemID)+"' ";
      rs=st.executeQuery(sqlStr);
      while (rs.next()){
        all = all.trim()+rs.getString(1);
      }
      //从function里得到公有权限
      sqlStr = "";
      sqlStr = " select funid from st_function where subStr(funid,1,2)= '"+Global.sqlFilter(systemID)+"' "
             + " and length(funid)=6 and ifpublic='y' order by 1";
      rs = st.executeQuery(sqlStr);
      while(rs.next()){
        all = all.trim()+rs.getString(1)+",";
      }
      //对all字符串进行解析，去掉重复的权限
      StringTokenizer tokenizer = new StringTokenizer(all);
      String temp = "";
      while (tokenizer.hasMoreTokens()) {
        temp = tokenizer.nextToken(",");
        if (!rightIDs.contains(temp))
          rightIDs.addElement(temp);
      }
      CloseDB();
    } catch(Exception e){
      Debug.print_log(e);  
      CloseDB();
      return null;
    }

    return rightIDs;
  }

  public Vector getAdminUserIDs(String userID,String systemID,
                                String right1,String right2,String right3) {
    Vector adminUserID = new Vector();
    String sqlStr = "";
    String all = "";
    try {
      DBInit();
      //从用户权限表里得到通过角色赋给用户的权限和接受的代理权限
      sqlStr = " select u.userid,r.define from st_role r,st_user_role u where r.roleid=u.roleid"
             + " and (u.userid = '" + Global.sqlFilter(userID)
             + "' or u.proxy_userid='" + Global.sqlFilter(userID) + "') "
             + " and subStr(r.define,1,2)= '" + Global.sqlFilter(systemID) + "' ";
      rs = st.executeQuery(sqlStr);
      while (rs.next()) {
        String define = rs.getString(2);
        if (define.indexOf(right1)!=-1 && define.indexOf(right2)!=-1 && define.indexOf(right3)!=-1) {
           all = all.trim() + rs.getString(1);
        }
      }
      //对all字符串进行解析，去掉重复的权限
      StringTokenizer tokenizer = new StringTokenizer(all);
      String temp = "";
      while (tokenizer.hasMoreTokens()) {
        temp = tokenizer.nextToken(",");
        if (!adminUserID.contains(temp))
          adminUserID.addElement(temp);
      }
      CloseDB();
    }
    catch (Exception e) {
      Debug.print_log(e);  
      CloseDB();
      return null;
    }
    return adminUserID;
  }


  //是否有某个权限
  public boolean isHaveRight(String userID,String functionID) {
    boolean isHave = false;
    String systemid =functionID.substring(0,2);
    Vector rightIDs = new Vector();
    rightIDs = this.getRights(userID,systemid);

    if (rightIDs != null || rightIDs.size() != 0) {
      if (rightIDs.contains(functionID))
        isHave = true;
    }
    return isHave;

  }


  //for pdb 得到某部门有此权限的员工编号
  //输入部门编号，权限编号
  //输出该部门有此权限的员工编号
  public Vector getAuditUserID(String depid,String funid) {
    Vector users = new Vector();
    String sqlStr="";

    sqlStr = "select distinct(userid) from st_user_role u,st_role r ,view_employee e "
           + "where r.roleid=u.roleid and define like '%"+Global.sqlFilter(funid)+"%' and "
           + "org='"+Global.sqlFilter(depid)+"' and userid = e.id order by 1";
    try {
      DBInit();
      rs = st.executeQuery(sqlStr);
      while (rs.next())
        users.addElement(rs.getString(1));
      CloseDB();
    } catch (Exception e){
      Debug.print_log(e);   
      CloseDB();
      return null;
    }
    return users;
  }

  public Vector getSignUserID(String funid) {
    Vector signUserID = new Vector();
    String sqlStr="";
    sqlStr = "select distinct(userid) from st_user_role u,st_role r "
           + "where r.roleid=u.roleid and define like '%"+Global.sqlFilter(funid)+"%' order by 1";
    try {
      DBInit();
      rs = st.executeQuery(sqlStr);
      while (rs.next())
        signUserID.addElement(rs.getString(1));
      CloseDB();
    } catch (Exception e){
      Debug.print_log(e);  
      CloseDB();
      return null;
    }
    return signUserID;
  }

  //for pdb 得到权限
  //输入用户编号和系统编号
  //输出此用户（包括被代理人）和权限列表
  public java.util.Hashtable rightList(String userID,String systemID){
    String sqlStr = "";
    String all = "";
    java.util.Hashtable rightList = new java.util.Hashtable();

    try {
      DBInit();
      //得到此人和权限的列表
      sqlStr = " select r.define from st_role r,st_user_role u where r.roleid=u.roleid"
             + " and u.userid = '"+Global.sqlFilter(userID)+"'"
             + " and subStr(r.define,1,2)= '"+Global.sqlFilter(systemID)+"' ";
      rs=st.executeQuery(sqlStr);
      while (rs.next()){
        all = all.trim()+rs.getString(1);
      }


      //从function里得到公有权限
      sqlStr = "";
      sqlStr = " select funid from st_function where subStr(funid,1,2)= '"+Global.sqlFilter(systemID)+"' "
             + " and length(funid)=6 and ifpublic='y' order by 1";
      rs = st.executeQuery(sqlStr);
      while(rs.next()){
        all = all.trim()+rs.getString(1)+",";
      }

      rightList.put(userID,all);

      //得到给此人代理的被代理人用户编号和权限的列表
      all = "";
      sqlStr = " select distinct(userid) from st_role r,st_user_role u where r.roleid=u.roleid"
             + " and proxy_userid= '"+Global.sqlFilter(userID)+"' and subStr(r.define,1,2)='"+Global.sqlFilter(systemID)+"' order by 1";

      rs = st.executeQuery(sqlStr);
      Vector store = new Vector();
      String temp = "";
      while (rs.next()){
        store.addElement(rs.getString(1));
      }


      for(int i = 0; i<store.size();i++){
        all = "";
        temp = store.elementAt(i).toString();
        sqlStr = " select r.define from st_role r,st_user_role u where r.roleid=u.roleid"
                + " and userid = '"+Global.sqlFilter(temp)+"' and proxy_userid='"+Global.sqlFilter(userID)+"'"
                + " and subStr(r.define,1,2)= '"+Global.sqlFilter(systemID)+"' ";

        rs = st.executeQuery(sqlStr);
        while (rs.next()){
          all = all.trim()+rs.getString(1);
        }

        if (!all.equals(""))
          rightList.put(temp,all);
      }
      CloseDB();
    } catch(Exception e){
      Debug.print_log(e);
      CloseDB();
      return null;
    }
    return rightList;
  }

  public String VerifyUser(String userid,String passwd){
    String flag = "1";   //用户登录非正常
    String sqlStr="";
    //passwd = computeDigest(passwd);
    sqlStr = "select userid from st_rightuser where userid='"+Global.sqlFilter(userid)+"' and passwd='"+Global.sqlFilter(passwd)+"'";
    try {
      DBInit();
      rs = st.executeQuery(sqlStr);
      if (rs.next())
        flag = "0";    //正确的用户登录

      CloseDB();
    } catch (Exception e){
      Debug.print_log(e);  
      CloseDB();
      return "2";
    }
    return flag ;
  }

  public String VerifyUserLogin(String userid,String passwd){
    String flag = "1";   //用户登录非正常
    String sqlStr="";
    passwd = computeDigest(passwd);
    sqlStr = "select userid from st_rightuser where userid='"+Global.sqlFilter(userid)+"' and passwd='"+Global.sqlFilter(passwd)+"'";
    try {
      DBInit();
      rs = st.executeQuery(sqlStr);
      if (rs.next())
        flag = "0";    //正确的用户登录

      CloseDB();
    } catch (Exception e){
      Debug.print_log(e);  
      CloseDB();
      return "2";
    }
    return flag ;
  }


  public String CloseDB() {
    try {
      if (rs != null)
        rs.close();
      if (st != null)
        st.close();
      if (conn != null)
        conn.close();
    } catch (Exception e){
      Debug.print_log(e);  
      return null;
    }
    return "ok";
  }
}
