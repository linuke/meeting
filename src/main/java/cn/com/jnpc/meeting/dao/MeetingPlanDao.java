package cn.com.jnpc.meeting.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.com.jnpc.meeting.bean.MeetingPlan;
import cn.com.jnpc.meeting.dao.util.DBTools;
import cn.com.jnpc.meeting.dao.util.JndiName;
import cn.com.jnpc.meeting.dao.util.DbHelper;
import cn.com.jnpc.meeting.dao.util.SQLStatementGetter;
import cn.com.jnpc.utils.Page;
import cn.com.jnpc.utils.PropertyFilter;
import cn.com.jnpc.utils.QueryUtil;

/**
 * 计划会议数据操作类.
 * 
 * @ClassName: MeetingPlanDao
 * @Title:
 * @Description:计划会议数据操作类
 * @Author:linuke
 * @Since:2013-4-18上午10:00:30
 * @Version:1.0
 */
public class MeetingPlanDao {

    private static final String FIELD_SQL = " m.id,m.reserve_roomid,mr.building || mr.room as reserve_address,"
                                                  + "m.starttime,m.endtime,m.leader,m.depart,m.remark,m.creattime,"
                                                  + "m.commiterid,m.commitdepart,m.content, m.type,m.presider,"
                                                  + "m.grade,m.category,m.fdepart,m.budget,m.address, m.contactphone,m.org ";
    private static final String FROM_SQL  = " from meeting_plan m left join meetingroom mr on m.reserve_roomid = mr.id ";
    private DBTools             dbt       = new DBTools(JndiName.INTRAWEB);

    /**
     * 根据条件获取计划会议
     * 
     * @Title: getMeetingPlan
     * @param pfList
     *            条件的集合
     * @return
     */
    public List<MeetingPlan> getMeetingPlan(List<PropertyFilter> pfList) {
        String sql = "select " + FIELD_SQL + FROM_SQL;
        String condition = QueryUtil.toSqlString(pfList, true);
        return dbt.query(MeetingPlan.class, sql + condition);
    }

    public Page<MeetingPlan> getMeetingPlan(Page<MeetingPlan> page, List<PropertyFilter> pfList) {
        int size = page.getPageSize();
        int pageNo = page.getPageNo();
        int tempPageNo = 0;
        if (pageNo < 1) {
            tempPageNo = 1;
        } else {
            tempPageNo = pageNo;
        }
        String condition = QueryUtil.toSqlString(pfList, true);
        String pageSql = SQLStatementGetter.getPageQueryStatement("select " + FIELD_SQL + FROM_SQL + condition
                + " order by m.starttime desc", (tempPageNo - 1) * size + 1, tempPageNo * size);
        String countSql = "select count(*) " + FROM_SQL + condition;
        page.setTotalCount(dbt.getCount(countSql));// 设置总记录数
        List<MeetingPlan> result = dbt.query(MeetingPlan.class, pageSql);
        JNPC jnpc = new JNPC();
        for (MeetingPlan mp : result) {
            mp.setCommiterid(jnpc.getName(mp.getCommiterid()));
        }
        page.setResult(result);
        return page;
    }

    /**
     * 计划会议添加.
     * 
     * @Title: meetingPlanAdd
     * @Author: linuke
     * @Since: 2013-4-18上午10:06:05
     * @param mp
     *            计划会议对象
     * @return 影响的行数,返回-1则失败
     */
    public int meetingPlanAdd(MeetingPlan mp, String userid) {
        int flag = 0;
        mp.setId("seq_meeting_plan_id.nextval");
        String sql = SQLStatementGetter.getInsertStatement(mp, "meeting_plan");
        QueryRunner qr = DbHelper.getIntrawebQueryRunner();
        JNPC jnpc = new JNPC();
        mp.setCommiterid(userid);
        mp.setCommitdepart(jnpc.getOrg(userid));
        try {
            flag = qr.update(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
        return flag;
    }

    /**
     * 更新计划会议.
     * 
     * @Title: update
     * @Author: linuke
     * @Since: 2013-4-18上午10:09:04
     * @param mp
     *            计划会议对象
     * @return 影响的行数,返回-1则失败
     */
    public int update(MeetingPlan mp) {
        try {
            QueryRunner qr = DbHelper.getIntrawebQueryRunner();
            String sql = SQLStatementGetter.getUpdateStatement(mp, "meeting_plan", "id");
            return qr.update(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 删除计划会议.
     * 
     * @Title: delete
     * @Author: linuke
     * @Since: 2013-4-18上午10:09:51
     * @param id
     *            计划会议id
     * @return 影响的行数,返回-1则失败
     */
    public int delete(String id) {
        try {
            QueryRunner qr = DbHelper.getIntrawebQueryRunner();
            String sql = "delete meeting_plan where id= " + id;
            return qr.update(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 批量删除计划会议.
     * 
     * @Title: delete
     * @Author: linuke
     * @Since: 2013-4-18上午10:09:51
     * @param ids
     *            计划会议id的数组
     * @return 影响的行数,返回-1则失败
     */
    public int delete(String... ids) {
        try {
            QueryRunner qr = DbHelper.getIntrawebQueryRunner();
            String str = "";
            for (String id : ids) {
                str += id + ",";
            }
            String sql = "delete meeting_plan where id in (" + str + "0)";
            return qr.update(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 根据员工编号获取部门计划会议信息.
     * 
     * @Title: getMeetingPlanByUserid
     * @Author: linuke
     * @Since: 2013-4-18上午10:04:13
     * @param userid
     *            员工编号
     * @return 员工所在部门计划会议结束时间大于当前时间的所有计划会议
     */
    public List<MeetingPlan> getMeetingPlanByUserid(String userid) {
        JNPC jnpc = new JNPC();
        String depart = jnpc.getOrg(userid);
        String sql = "select * from meeting_plan where endtime >sysdate and " + "commitdepart='" + depart
                + "' order by starttime";
        QueryRunner qr = DbHelper.getIntrawebQueryRunner();
        try {
            List<MeetingPlan> list = qr.query(sql, new BeanListHandler<MeetingPlan>(MeetingPlan.class));
            for (MeetingPlan mp : list) {
                mp.setCommiterid(jnpc.getName(mp.getCommiterid()));
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据主键获取计划会议.
     * 
     * @Title: getMeetingPlanByID
     * @Author: linuke
     * @Since: 2013-4-18上午10:03:32
     * @param id
     *            计划会议id
     * @return 计划会议
     */
    public MeetingPlan getMeetingPlanByID(String id) {
        String sql = "select * from meeting_plan where id=" + id;
        QueryRunner qr = DbHelper.getIntrawebQueryRunner();
        try {
            return qr.query(sql, new BeanHandler<MeetingPlan>(MeetingPlan.class));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 计划会议转为正式会议.
     * 
     * @Title: toMeeting
     * @Description: 计划会议转为正式会议
     * @Author: linuke
     * @Since: 2013-4-18上午9:54:38
     * @param id
     *            计划会议id
     * @return 影响的行数
     */
    public int toMeeting(String id) {
        Connection conn = null;
        try {
            int flag = 0;
            conn = DbHelper.getConnection("intraweb");
            conn.setAutoCommit(false);
            QueryRunner qr = new QueryRunner();
            String sql1 = "select * from meeting_plan where id=" + id;
            MeetingPlan mp = qr.query(conn, sql1, new BeanHandler<MeetingPlan>(MeetingPlan.class));
            mp.setId("seq_meeting_id.nextval");
            mp.setMeetingid("");
            mp.setCreattime(null);
            String sql = SQLStatementGetter.getInsertStatement(mp, "meeting");
            flag = qr.update(conn, sql);
            String meetingId = (BigDecimal) qr.query(conn, "select seq_meeting_id.nextval as id from dual",
                    new ScalarHandler<BigDecimal>("id")) + "";
            mp.setMeetingid(meetingId);
            mp.setId(id);
            String updateSql = SQLStatementGetter.getUpdateStatement(mp, "meeting_plan", "id");
            flag = qr.update(conn, updateSql);
            conn.commit();
            return flag;
        } catch (Exception e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            return -1;
        } finally {
            try {
                org.apache.commons.dbutils.DbUtils.close(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
