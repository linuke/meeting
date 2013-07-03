package cn.com.jnpc.meeting.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.com.jnpc.meeting.bean.Employee;
import cn.com.jnpc.meeting.dao.util.DBTools;
import cn.com.jnpc.meeting.dao.util.JndiName;
import cn.com.jnpc.meeting.dao.util.DbHelper;

/**
 * 和人事相关的操作
 * 
 */
public class JNPC {

    /**
     * 根据员工姓名或姓名首字母获取员工完整姓名和部门
     * 
     * @param str
     * @return
     */
    public List<Employee> getEmployeeNamesAndOrgName(String str) {
        String sql = "select t.name , t.org from VIEW_PAY_EMPLOYEE t where F_TRANS_PINYIN_CAPITAL(t.name) like '%?%' or t.name like '%?%' and t.precostatus ='在职'";
        QueryRunner qr = DbHelper.getJNPCQueryRunner();
        try {
            List<Employee> list = (List<Employee>) qr.query(sql.replaceAll("\\?", str), new BeanListHandler<Employee>(
                    Employee.class));
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据员工姓名或姓名首字母获取员工完整姓名
     * 
     * @param str
     * @return
     */
    public List<Object> getEmployeeNames(String str) {
        String sql = "select t.name from VIEW_PAY_EMPLOYEE t where lower(F_TRANS_PINYIN_CAPITAL(t.name)) like '%'||lower('?')||'%' or t.name like '%?%' and t.precostatus ='在职'";
        QueryRunner qr = DbHelper.getJNPCQueryRunner();
        try {
            List<Object> list = qr.query(sql.replaceAll("\\?", str), new ColumnListHandler<Object>("name"));
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 公司内部处室
     * 
     * @param str
     * @return
     */
    public List<Object> getORGName(String str) {
        String sql = "select t.ORGNAME from JNPC_DEP_ALL t where t.CLASS=3 and (lower(F_TRANS_PINYIN_CAPITAL(t.ORGNAME)) like '%'||lower('?')||'%' or t.ORGNAME like '%?%' or lower(t.org) like '%'|| lower('?')||'%') order by t.SORT";
        QueryRunner qr = DbHelper.getJNPCQueryRunner();
        try {
            List<Object> list = qr.query(sql.replaceAll("\\?", str), new ColumnListHandler<Object>("orgname"));
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 公司内部处室
     * 
     * @param str
     * @return org,orgname,sort
     */
    public List<Object[]> getAllORG() {
        String sql = "select t.org,t.ORGNAME,t.sort from JNPC_DEP_ALL t where t.CLASS=3 order by t.SORT";
        QueryRunner qr = DbHelper.getJNPCQueryRunner();
        try {
            List<Object[]> list = qr.query(sql, new ArrayListHandler());
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据员工编号获取员工Email
     * 
     * @param userID
     * @return
     */
    public String getEmailByUserID(String userID) {
        String sql = "select t.email from PA_EMP_EMPLOYEE t where t.id=" + userID;
        QueryRunner qr = DbHelper.getJNPCQueryRunner();
        try {
            Object rtn = qr.query(sql, new ScalarHandler<Object>("email"));
            return rtn == null ? "" : rtn.toString();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取领导的email
     * 
     * @param leaders
     * @return
     */
    public String[] getLeadersEmail(String... leaders) {
        String ls = "";
        for (String str : leaders) {
            ls += "'" + str + "',";
        }
        String sql = "select p.email from JNPC_DEP_ALL t,PA_EMP_EMPLOYEE p where t.ID=p.id and t.ORGNAME in (" + ls
                + "'')";
        QueryRunner qr = DbHelper.getJNPCQueryRunner();
        try {
            List<Object> list = qr.query(sql, new ColumnListHandler<Object>("email"));
            if (list != null && list.size() > 0) {
                return list.toArray(new String[0]);
                // int len = list.size();
                // String[] emails = new String[len];
                // for(Object obj : list){
                //
                // }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Object[]> getLeadersEmail2(String... leaders) {
        String ls = "";
        for (String str : leaders) {
            ls += "'" + str + "',";
        }
        String sql = "select t.ORGNAME,p.email from JNPC_DEP_ALL t,PA_EMP_EMPLOYEE p where t.ID=p.id and t.ORGNAME in ("
                + ls + "'')";
        QueryRunner qr = DbHelper.getJNPCQueryRunner();
        try {
            List<Object[]> list = qr.query(sql, new ArrayListHandler());
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 得到员工所在部门
     * 
     * @param userId
     *            员工编号
     * 
     *            @ return
     */
    public String getOrg(String userId) {
        QueryRunner qr = DbHelper.getJNPCQueryRunner();
        String sql = "select org from jnpc_user_simple where id='" + userId + "'"; // 查询id等于用户id的部门名称
        try {
            String org = qr.query(sql, new ScalarHandler<String>("org"));
            return org;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 得到员工所在部门
     * 
     * @param userId
     *            员工编号
     * 
     *            @ return
     */
    public String getName(String userId) {
        QueryRunner qr = DbHelper.getJNPCQueryRunner();
        String sql = "select name from jnpc_user_simple where id='" + userId + "'"; // 查询id等于用户id的部门名称
        try {
            String org = qr.query(sql, new ScalarHandler<String>("name"));
            return org;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 得到公司领导列表
     * 
     * @ return
     */
    public List<Object> getLeaders() {
        String sql = "";
        DBTools dbt = new DBTools(JndiName.JNPC);
        sql = "select orgname from jnpc_dep where class in (1,2)";
        return dbt.query(sql, "orgname");
    }
}
