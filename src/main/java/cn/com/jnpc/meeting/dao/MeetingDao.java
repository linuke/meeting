package cn.com.jnpc.meeting.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import cn.com.jnpc.meeting.bean.Counter;
import cn.com.jnpc.meeting.bean.Meeting;
import cn.com.jnpc.meeting.dao.util.DBTools;
import cn.com.jnpc.meeting.dao.util.DbHelper;
import cn.com.jnpc.meeting.dao.util.JndiName;
import cn.com.jnpc.meeting.dao.util.SQLStatementGetter;
import cn.com.jnpc.utils.DateUtil;
import cn.com.jnpc.utils.Page;
import cn.com.jnpc.utils.PropertyFilter;
import cn.com.jnpc.utils.QueryUtil;

public class MeetingDao {

    /**
     * 联合查询语句
     */
    // private static final String FIELD_SQL = "m.id,m.roomid,m.reserve_roomid,"
    // + "mr2.roomname || mr1.roomname as address1, "
    // + "mr4.roomname || mr3.roomname as reserve_address,"
    // + "m.starttime,m.endtime,m.leader,m.depart,m.remark,m.committime,"
    // + "m.commiterid,m.commitdepart,m.content,"
    // + "m.approverid,m.alloterid,m.status,m.type,m.presider,"
    // + "m.grade,m.category,m.fdepart,m.budget,m.actual_costs,m.address,"
    // + "m.contactphone,m.org,m.flow ";
    // private static final String FROM_SQL =
    // "from meeting m left join meeting_room mr1 on m.roomid=mr1.id "
    // + "left join meeting_room mr2 on mr2.id=mr1.parentid "
    // + "left join meeting_room mr3 on m.reserve_roomid=mr3.id "
    // + "left join meeting_room mr4 on mr4.id=mr3.parentid ";
    private static final String FIELD_SQL = "m.id,m.roomid,m.reserve_roomid,mr.building || mr.room as address1,"
                                                  + "mr2.building || mr2.room as reserve_address,"
                                                  + "m.starttime,m.endtime,m.leader,m.depart,m.remark,m.committime,"
                                                  + "m.commiterid,m.commitdepart,m.content,"
                                                  + "m.approverid,m.alloterid,m.status,m.type,m.presider,"
                                                  + "m.grade,m.category,m.fdepart,m.budget,m.actual_costs,m.address,"
                                                  + "m.contactphone,m.org,m.flow ";
    private static final String FROM_SQL  = " from meeting m left join meetingroom mr on m.roomid = mr.id "
                                                  + " left join meetingroom mr2 on m.reserve_roomid = mr2.id";

    /**
     * 会议室在某个时间段的会议情况
     * 
     * @param roomID
     * @param startTime
     * @param endTime
     * @return
     */
    public List<Meeting> getMeetingByRoomAndTime(String roomID, String startTime, String endTime) {
        QueryRunner qr = DbHelper.getIntrawebQueryRunner();
        StringBuffer sb = new StringBuffer();
        sb.append("select " + FIELD_SQL + FROM_SQL + " where ");
        sb.append("(m.starttime between to_date('");
        sb.append(startTime);
        sb.append("','yyyy-mm-dd hh24:mi') and to_date('");
        sb.append(endTime);
        sb.append("','yyyy-mm-dd hh24:mi')");
        sb.append(" or");
        sb.append(" m.endtime between to_date('");
        sb.append(startTime);
        sb.append("','yyyy-mm-dd hh24:mi') and to_date('");
        sb.append(endTime);
        sb.append("','yyyy-mm-dd hh24:mi'))");
        if (roomID != null && !"".equals(roomID)) {
            sb.append(" and m.roomid=");
            sb.append(roomID);
//            sb.append(" or m.reserve_roomid=");
//            sb.append(roomID);
        }
        try {
            List<Meeting> list = (List<Meeting>) qr.query(sb.toString(), new BeanListHandler<Meeting>(Meeting.class));
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 根据条件查询会议
     * 
     * @Title: getMeeting
     * @param pfList
     * @return
     */
    public List<Meeting> getMeeting(List<PropertyFilter> pfList) {
        String sql = "select " + FIELD_SQL + FROM_SQL;
        String condition = QueryUtil.toSqlString(pfList, true);
        DBTools dbt = new DBTools(JndiName.INTRAWEB);
        return dbt.query(Meeting.class, sql + condition + " order by m.starttime");
    }

    /**
     * 根据部门获取会议统计情况
     * 
     * @param org
     * @param startTime
     * @param endTime
     * @return
     */
    public List<Object[]> getMeetingStatistics(String startTime, String endTime, String org) {
        QueryRunner qr = DbHelper.getIntrawebQueryRunner();
        StringBuilder sb = new StringBuilder();
        sb.append("select count(*) as c, round(sum(floor(to_number(t.endtime-t.starttime)*24*60))/60,2) as spanHours, ");
        sb.append("t.commitdepart from MEETING t where");
        sb.append("(t.starttime between to_date('");
        sb.append(startTime);
        sb.append("','yyyy-mm-dd hh24:mi') and to_date('");
        sb.append(endTime);
        sb.append("','yyyy-mm-dd hh24:mi')");
        sb.append(" or");
        sb.append(" t.endtime between to_date('");
        sb.append(startTime);
        sb.append("','yyyy-mm-dd hh24:mi') and to_date('");
        sb.append(endTime);
        sb.append("','yyyy-mm-dd hh24:mi'))");
        sb.append(" and t.commitdepart in (");
        QueryRunner jnpcQr = DbHelper.getJNPCQueryRunner();
        String sql = "select t.org,t.ORGNAME from JNPC_DEP_ALL t where t.CLASS=3 order by sort";
        try {
            List<Object[]> list2 = jnpcQr.query(sql, new ArrayListHandler());
            if (org == null || "".equals(org)) {
                for (int i = 0, len = list2.size(); i < len; i++) {
                    if (i == (len - 1)) {
                        org += "'" + list2.get(i)[0] + "'";
                    } else if (i == 0) {
                        org = "'" + list2.get(i)[0] + "',";
                    } else {
                        org += "'" + list2.get(i)[0] + "',";
                    }
                }
            } else {
                org = "'" + org + "'";
            }
            sb.append(org);
            sb.append(") group by t.commitdepart ");
            List<Object[]> list = qr.query(sb.toString(), new ArrayListHandler());
            List<Object[]> rtnList = new ArrayList<Object[]>();
            for (Object[] obj : list2) {
                String org1 = obj[0].toString();
                for (Object[] ob : list) {
                    if (org1.equals(ob[2].toString())) {
                        ob[2] = obj[1];
                        rtnList.add(ob);
                        break;
                    }
                }
            }
            return rtnList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 会议室使用情况
     * 
     * @param roomID
     * @return
     */
    public List<Object[]> getMeetingRoomStatistics(String roomID) {
        StringBuilder sb = new StringBuilder();
        sb.append("select count(m.roomid),m.roomid,mr.building,mr.room from meeting m,meetingroom mr  where");
        sb.append(" m.roomid=mr.id and mr.deleted='0' ");
        if (roomID != null && !"".equals(roomID)) {
            sb.append(" and m.roomid=" + roomID);
        }
        sb.append(" group by m.roomid,mr.building,mr.room,mr.capacity order by mr.building,mr.capacity ");
        QueryRunner qr = DbHelper.getIntrawebQueryRunner();
        try {
            List<Object[]> list = qr.query(sb.toString(), new ArrayListHandler());
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 会议室使用情况
     * 
     * @param roomID
     * @return
     */
    public List<Object[]> getMeetingRoom2Statistics(List<PropertyFilter> pfList) {
        StringBuilder sb = new StringBuilder();
        sb.append("select count(m.roomid),m.roomid,mr.building,mr.room from "
                + "meeting m,meetingroom mr where m.roomid=mr.id");
        String s = QueryUtil.toSqlString(pfList, false);
        String sql = " group by m.roomid,mr.building,mr.room order by mr.building";
        QueryRunner qr = DbHelper.getIntrawebQueryRunner();
        try {
            List<Object[]> list = qr.query(sb.toString() + s + sql, new ArrayListHandler());
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 会议室使用详细情况
     * 
     * @param roomID
     * @return
     */
    public List<Object[]> getMeetingRoom2DetailStatistics(List<PropertyFilter> pfList) {
        StringBuilder sb = new StringBuilder();
        sb.append("select count(m.roomid),m.roomid,m.commitdepart from meeting m ");
        String s = QueryUtil.toSqlString(pfList, true);
        String sql = " group by m.roomid,m.commitdepart";
        QueryRunner qr = DbHelper.getIntrawebQueryRunner();
        QueryRunner jnpcQr = DbHelper.getJNPCQueryRunner();
        String jsql = "select t.org,t.ORGNAME from JNPC_DEP_ALL t where t.CLASS=3 order by sort";
        try {
            List<Object[]> list2 = jnpcQr.query(jsql, new ArrayListHandler());
            List<Object[]> list = qr.query(sb.toString() + s + sql, new ArrayListHandler());
            List<Object[]> rtnList = new ArrayList<Object[]>();
            for (Object[] obj : list2) {
                String org1 = obj[0].toString();
                for (Object[] ob : list) {
                    if (org1.equals(ob[2] == null ? "" : ob[2].toString())) {
                        ob[2] = obj[1];
                        rtnList.add(ob);
                        break;
                    }
                }
            }
            return rtnList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 会议室使用详细情况
     * 
     * @param roomID
     * @return
     */
    public List<Object[]> getMeetingRoomDetailStatistics(String roomID) {
        StringBuilder sb = new StringBuilder();
        sb.append("select count(m.roomid),m.roomid,m.commitdepart,mr.building,mr.room from meeting m,meetingroom mr  where");
        sb.append(" m.roomid=mr.id and mr.deleted='0' ");
        sb.append(" and m.roomid=" + roomID);
        sb.append(" group by m.roomid,mr.building,m.commitdepart,mr.room,mr.capacity order by mr.building,mr.capacity ");
        QueryRunner qr = DbHelper.getIntrawebQueryRunner();
        QueryRunner jnpcQr = DbHelper.getJNPCQueryRunner();
        String sql = "select t.org,t.ORGNAME from JNPC_DEP_ALL t where t.CLASS=3 order by sort";
        try {
            List<Object[]> list2 = jnpcQr.query(sql, new ArrayListHandler());
            List<Object[]> list = qr.query(sb.toString(), new ArrayListHandler());
            List<Object[]> rtnList = new ArrayList<Object[]>();
            for (Object[] obj : list2) {
                String org1 = obj[0].toString();
                for (Object[] ob : list) {
                    if (org1.equals(ob[2].toString())) {
                        ob[2] = obj[1];
                        rtnList.add(ob);
                        break;
                    }
                }
            }
            return rtnList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据id获取会议信息
     * 
     * @param id
     * @return
     */
    public Meeting getMeetingById(String id) {
        String sql = "select * from MEETING t where t.id=" + id;
        QueryRunner qr = DbHelper.getIntrawebQueryRunner();
        try {
            Meeting meeting = (Meeting) qr.query(sql, new BeanHandler<Meeting>(Meeting.class));
            return meeting;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 退回会议
     * 
     * @param id
     * @return
     */
    public boolean goBackMeet(String id) {
        String sql = "update meeting set status=4 where id=" + id;
        QueryRunner qr = DbHelper.getIntrawebQueryRunner();
        try {
            int flag = qr.update(sql);
            if (flag == -1) {
                return false;
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @Title: meetingAdd
     * @Author: linuke
     * @Since: 2013-4-23上午10:06:36
     * @param m
     * @return
     */
    public int meetingAdd(Meeting m, String userid) {
        int flag = 0;
        // 得到申请人所在的部门
        String commitDept = getOrg(userid);
        // 得到系统时间
        m.setCommittime(DateUtil.stringToDate(DateUtil.getCurrentDate("yyyy-MM-dd HH:mm"), "yyyy-MM-dd HH:mm"));
        m.setCommiterid(userid);
        m.setId("seq_meeting_id.nextval");
        m.setCommitdepart(commitDept);
        m.setStatus("0");
        String startTime = DateUtil.dateToString(m.getStarttime(), "yyyy-MM-dd HH:mm");
        String endTime = DateUtil.dateToString(m.getEndtime(), "yyyy-MM-dd HH:mm");
        String content = m.getContent();
        String commiterId = m.getCommiterid();
        String type = m.getType();
        String presider = m.getPresider();
        String leader = m.getLeader();
        String[] leaders = leader == null ? null : leader.split(",");
        String sql = "";
        DBTools dbt = new DBTools(JndiName.INTRAWEB);
        // 生成一条sql语句,用于查询是否有相同的记录,防止页面刷新时自动添加记录
        String sql2 = "select count(*) count from meeting where startTime=to_date('" + startTime
                + "','yyyy-mm-dd hh24:mi')";
        sql2 = sql2 + "and endTime=to_date('" + endTime + "','yyyy-mm-dd hh24:mi') and content ='" + content
                + "' and commiterid='" + commiterId + "'and type='" + type + "'";
        // 执得查询语句
        long cut = dbt.getCount(sql2);
        if (!isPresiderAvailable(startTime, endTime, presider, "")) {
            flag = -99;
            return flag;
        }
        if (!isLeadersAvailable(startTime, endTime, leaders, "")) {
            flag = -98;
            return flag;
        }
        if (cut == 0l) {
            sql = SQLStatementGetter.getInsertStatement(m, "meeting");
            // flag = sg.update(sql);
            flag = dbt.update(sql);
        } else {
            flag = -999;
        }
        return flag;
    }

    /**
     * 更新会议
     * 
     * @Title: meetingUpdate
     * @Author: linuke
     * @Since: 2013-4-23上午10:19:27
     * @param m
     * @return
     */
    public int meetingUpdate(Meeting m) {
        int flag = 0;
        // 得到申请人所在的部门
        String startTime = DateUtil.dateToString(m.getStarttime(), "yyyy-MM-dd HH:mm");
        String endTime = DateUtil.dateToString(m.getEndtime(), "yyyy-MM-dd HH:mm");
        String content = m.getContent();
        String commiterId = m.getCommiterid();
        String type = m.getType();
        String presider = m.getPresider();
        String leader = m.getLeader();
        String[] leaders = leader == null ? null : leader.split(",");
        String sql = "";
        DBTools dbt = new DBTools(JndiName.INTRAWEB);
        // 生成一条sql语句,用于查询是否有相同的记录,防止页面刷新时自动添加记录
        String sql2 = "select count(*) count from meeting where startTime=to_date('" + startTime
                + "','yyyy-mm-dd hh24:mi')";
        sql2 = sql2 + "and endTime=to_date('" + endTime + "','yyyy-mm-dd hh24:mi') and content ='" + content
                + "' and commiterid='" + commiterId + "'and type='" + type + "'";
        // 执得查询语句
        long cut = dbt.getCount(sql2);
        if (!isPresiderAvailable(startTime, endTime, presider, m.getId())) {
            flag = -99;
            return flag;
        }
        if (!isLeadersAvailable(startTime, endTime, leaders, m.getId())) {
            flag = -98;
            return flag;
        }
        if (cut == 0l) {
            sql = SQLStatementGetter.getUpdateStatement(m, "meeting", "id");
            flag = dbt.update(sql);
        } else {
            flag = -999;
        }
        return flag;
    }

    /*
     * 得到员工所在部门
     * 
     * @param userId 员工编号
     * 
     * @ return
     */
    public String getOrg(String userId) {
        DBTools dbt = new DBTools(JndiName.JNPC);
        // 查询id等于用户id的部门名称
        String sql = "select org from jnpc_user_simple where id='" + userId + "'";
        Object org = dbt.queryReturnOne(sql, "org");
        return org == null ? "" : org.toString();
    }

    /**
     * 获取所有有领导参加的会议
     * 
     * @Title: getMeetingOfLeaderAttend
     * @param page
     * @param status
     *            会议状态
     * @return
     */
    public Page<Meeting> getMeetingOfLeaderAttend(Page<Meeting> page, String status) {
        PropertyFilter lpf = new PropertyFilter("m.leader:IS_S", " not null");
        PropertyFilter spf = new PropertyFilter("m.status:EQ_S", status);
        PropertyFilter etpf = new PropertyFilter("m.endtime:GT_D", DateUtil.getCurrentDate("yyyy-MM-dd HH:mm"));
        List<PropertyFilter> pfList = new ArrayList<PropertyFilter>();
        pfList.add(lpf);
        pfList.add(spf);
        pfList.add(etpf);
        return this.getMeeting(page, pfList);
    }

    /**
     * 得到已分配会议室或已审批但未分配会议室的例会或会议.
     * 
     * @Title: getMeeting
     * @Author: linuke
     * @Since: 2013-4-18下午1:33:10
     * @param type
     *            会议类型
     * @return
     */
    public Page<Meeting> getMeeting(Page<Meeting> page, String type) {
        PropertyFilter tpf = new PropertyFilter("m.type:EQ_I", type);
        PropertyFilter epf = new PropertyFilter("m.endtime:GT_D", DateUtil.getCurrentDate("yyyy-MM-dd HH:mm"));// 会议结束时间大于当前时间
        List<PropertyFilter> pfList = new ArrayList<PropertyFilter>();
        pfList.add(tpf);
        pfList.add(epf);
        return this.getMeeting(page, pfList);
        // int size = page.getPageSize();
        // int pageNo = page.getPageNo();
        // int tempPageNo = 0;
        // if (pageNo < 1) {
        // tempPageNo = 1;
        // } else {
        // tempPageNo = pageNo;
        // }
        // String oSql = " order by m.starttime desc";
        // String cSpq = " and m.endtime > sysdate and m.type=" + type;
        // String sql = SQLStatementGetter.getPageQueryStatement("select " +
        // FROM_SQL + CONDITION_SQL + cSpq + oSql,
        // (tempPageNo - 1) * size + 1, tempPageNo * size);
        // DBTools dbt = new DBTools(DataSourceName.INTRAWEB);
        // page.setTotalCount(dbt.getCount("select count(*) " + FROM_SQL +
        // CONDITION_SQL + cSpq));
        // List<Meeting> list = dbt.query(Meeting.class, sql);
        // JNPC jnpc = new JNPC();
        // for (Meeting mp : list) {
        // mp.setCommiterid(jnpc.getName(mp.getCommiterid()));
        // }
        // page.setResult(list);
    }

    /**
     * 根据员工编号获取部门会议信息.
     * 
     * @Title: getMeetingPlanByUserid
     * @Author: linuke
     * @Since: 2013-4-18上午10:04:13
     * @param userid
     *            员工编号
     * @return 员工所在部门计划会议结束时间大于当前时间的所有计划会议
     */
    public Page<Meeting> getMeetingByUserid(Page<Meeting> page, String userid) {
        JNPC jnpc = new JNPC();
        String depart = jnpc.getOrg(userid);
        PropertyFilter dpf = new PropertyFilter("m.commitdepart:EQ_S", depart);
        PropertyFilter tpf = new PropertyFilter("m.type:UNEQ_I", "4");// 去掉培训通知
        PropertyFilter epf = new PropertyFilter("m.endtime:GT_D", DateUtil.getCurrentDate("yyyy-MM-dd HH:mm"));// 会议结束时间大于当前时间
        List<PropertyFilter> pfList = new ArrayList<PropertyFilter>();
        pfList.add(dpf);
        pfList.add(tpf);
        pfList.add(epf);
        return this.getMeeting(page, pfList);
        // int size = page.getPageSize();
        // int pageNo = page.getPageNo();
        // int tempPageNo = 0;
        // if (pageNo < 1) {
        // tempPageNo = 1;
        // } else {
        // tempPageNo = pageNo;
        // }
        // String cSql =
        // " and endtime >sysdate and type <> 4 and commitdepart='" + depart +
        // "'";
        // String pageSql = SQLStatementGetter.getPageQueryStatement("select " +
        // FIELD_SQL + FROM_SQL + CONDITION_SQL
        // + cSql + " order by starttime desc", (tempPageNo - 1) * size + 1,
        // tempPageNo * size);
        // DBTools dbt = new DBTools(DataSourceName.INTRAWEB);
        // page.setTotalCount(dbt.getCount("select count(*)" + FROM_SQL +
        // cSql));
        // List<Meeting> list = dbt.query(Meeting.class, pageSql);
        // for (Meeting mp : list) {
        // mp.setCommiterid(jnpc.getName(mp.getCommiterid()));
        // }
        // page.setResult(list);
    }

    /**
     * 根据员工编号获取部门培训信息.
     * 
     * @param userid
     *            员工编号
     * @return
     */
    public Page<Meeting> getMeetingTrainByUserid(Page<Meeting> page, String userid) {
        JNPC jnpc = new JNPC();
        String depart = jnpc.getOrg(userid);
        PropertyFilter dpf = new PropertyFilter("m.commitdepart:EQ_S", depart);
        PropertyFilter tpf = new PropertyFilter("m.type:EQ_I", "4");// 培训通知
        PropertyFilter epf = new PropertyFilter("m.endtime:GT_D", DateUtil.getCurrentDate("yyyy-MM-dd HH:mm"));// 会议结束时间大于当前时间
        List<PropertyFilter> pfList = new ArrayList<PropertyFilter>();
        pfList.add(dpf);
        pfList.add(tpf);
        pfList.add(epf);
        return this.getMeeting(page, pfList);
        // int size = page.getPageSize();
        // int pageNo = page.getPageNo();
        // int tempPageNo = 0;
        // if (pageNo < 1) {
        // tempPageNo = 1;
        // } else {
        // tempPageNo = pageNo;
        // }
        // String columnSql = "m.* ";
        // String cSql =
        // "from meeting m where endtime >sysdate and type = 4 and commitdepart='"
        // + depart + "'";
        // String pageSql = SQLStatementGetter.getPageQueryStatement("select " +
        // columnSql + cSql
        // + " order by starttime desc", (tempPageNo - 1) * size + 1, tempPageNo
        // * size);
        // DBTools dbt = new DBTools(DataSourceName.INTRAWEB);
        // page.setTotalCount(dbt.getCount("select count(*)" + cSql));
        // List<Meeting> list = dbt.query(Meeting.class, pageSql);
        // for (Meeting mp : list) {
        // mp.setCommiterid(jnpc.getName(mp.getCommiterid()));
        // }
        // page.setResult(list);
        // return page;
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
    public Page<Meeting> getMeeting(Page<Meeting> page, List<PropertyFilter> pfList) {
        JNPC jnpc = new JNPC();
        int size = page.getPageSize();
        int pageNo = page.getPageNo();
        int tempPageNo = 0;
        if (pageNo < 1) {
            tempPageNo = 1;
        } else {
            tempPageNo = pageNo;
        }
        String cSql = QueryUtil.toSqlString(pfList, true);// 条件语句
        String pageSql = SQLStatementGetter.getPageQueryStatement("select " + FIELD_SQL + FROM_SQL + cSql
                + " order by starttime desc", (tempPageNo - 1) * size + 1, tempPageNo * size);
        DBTools dbt = new DBTools(JndiName.INTRAWEB);
        page.setTotalCount(dbt.getCount("select count(*) " + FROM_SQL + cSql));
        List<Meeting> list = dbt.query(Meeting.class, pageSql);
        for (Meeting mp : list) {
            mp.setCommiterid(jnpc.getName(mp.getCommiterid()));
        }
        page.setResult(list);
        return page;
    }

    /**
     * 得到会议已经结束的历史会议信息.
     * 
     * @Title: getHistoryMeeting
     * @Author: linuke
     * @Since: 2013-4-18下午2:55:31
     * @param type
     *            会议类型(1表示例会;2表示会议;3外部会议;4培训通知)
     * @return
     */
    public Page<Meeting> getHistoryMeeting(Page<Meeting> page, String type) {
        PropertyFilter dpf = new PropertyFilter("m.status:EQ_I", "3");// 所有安排会议室的会议
        PropertyFilter tpf = new PropertyFilter("m.type:EQ_I", type);
        PropertyFilter epf = new PropertyFilter("m.endtime:LT_D", DateUtil.getCurrentDate("yyyy-MM-dd HH:mm"));// 会议结束时间小于当前时间
        List<PropertyFilter> pfList = new ArrayList<PropertyFilter>();
        pfList.add(dpf);
        pfList.add(tpf);
        pfList.add(epf);
        return this.getMeeting(page, pfList);
        // int size = page.getPageSize();
        // int pageNo = page.getPageNo();
        // int tempPageNo = 0;
        // if (pageNo < 1) {
        // tempPageNo = 1;
        // } else {
        // tempPageNo = pageNo;
        // }
        // String columnSql = " m.* ";
        // String cSql = "from meeting m " +
        // " where endtime< sysdate and type='" + type + "' and status='3'";
        // String pageSql = SQLStatementGetter.getPageQueryStatement("select " +
        // columnSql + cSql
        // + "order by starttime desc", (tempPageNo - 1) * size + 1, tempPageNo
        // * size);
        // DBTools dbt = new DBTools(DataSourceName.INTRAWEB);
        // JNPC jnpc = new JNPC();
        // page.setTotalCount(dbt.getCount("select count(*) " + cSql));
        // List<Meeting> list = dbt.query(Meeting.class, pageSql);
        // for (Meeting mp : list) {
        // mp.setCommiterid(jnpc.getName(mp.getCommiterid()));
        // }
        // page.setResult(list);
        // return page;
    }

    /**
     * 判断主持人与会时间是否冲突
     * 
     * @param startTime
     *            会议开始时间
     * @param endTime
     *            会议结束时间
     * @param presider
     *            主持人
     * @return 返回是否冲突
     */
    public boolean isPresiderAvailable(String startTime, String endTime, String presider, String meetingId) {
        DBTools dbt = new DBTools(JndiName.INTRAWEB);
        String sql3 = "select count(*) count from meeting where to_date('" + startTime
                + "','yyyy-mm-dd hh24:mi') <endTime" + " and  to_date('" + endTime
                + "','yyyy-mm-dd hh24:mi')>=startTime and presider='" + presider + "'";
        if (meetingId != null && !"".equalsIgnoreCase(meetingId)) {
            sql3 += " and id <>" + meetingId;
        }// 是否为修改会议
         // String count = ora.getSnglRowSnglCol(sql3);
        Long count = dbt.getCount(sql3);
        if (count != 0l)
            return false;
        return true;
    }

    /**
     * 判断领导与会时间是否冲突
     * 
     * @param startTime
     *            会议开始时间
     * @param endTime
     *            会议结束时间
     * @param leaders
     *            领导名单
     * @return 返回是否冲突
     */
    public boolean isLeadersAvailable(String startTime, String endTime, String[] leaders, String meetingId) {
        DBTools dbt = new DBTools(JndiName.INTRAWEB);
        if (leaders == null || leaders.length <= 0) {
            return true;
        } else if (leaders.length == 1 && "".equals(leaders[0])) {
            return true;
        }
        for (int i = 0; i < leaders.length; i++) {
            String sql3 = "select count(*) count from meeting where to_date('" + startTime
                    + "','yyyy-mm-dd hh24:mi') <endTime" + " and  to_date('" + endTime
                    + "','yyyy-mm-dd hh24:mi')>=startTime and leader like'%" + leaders[0] + "%'";
            // System.out.println(sql3);
            if (meetingId != null && !"".equals(meetingId)) {
                sql3 += " and id<>" + meetingId;
            }
            long count = dbt.getCount(sql3);
            if (count != 0)
                return false;
        }
        return true;
    }

    /**
     * 判断领导与会时间是否冲突
     * 
     * @param startTime
     *            会议开始时间
     * @param endTime
     *            会议结束时间
     * @param leaders
     *            领导名单
     * @return 返回是否冲突
     */
    public boolean isLeaderAvailable(String startTime, String endTime, String leader, String meetingId) {
        boolean flag = false;
        DBTools dbt = new DBTools(JndiName.INTRAWEB);
        String sql3 = "select count(*) count from meeting where to_date('" + startTime
                + "','yyyy-mm-dd hh24:mi') <endTime" + " and  to_date('" + endTime
                + "','yyyy-mm-dd hh24:mi')>=startTime and leader like'%" + leader + "%'";
        // System.out.println(sql3);
        if (meetingId != null && !"".equals(meetingId)) {
            sql3 += " and id<>" + meetingId;
        }
        long count = dbt.getCount(sql3);
        if (count != 0)
            return flag;
        return true;
    }

    /**
     * 删除会议
     * 
     * @Title: delete
     * @Author: linuke
     * @Since: 2013-4-23上午10:25:06
     * @param id
     * @return
     */
    public int delete(String id) {
        String sql = "delete meeting where id=" + id;
        DBTools dbt = new DBTools(JndiName.INTRAWEB);
        return dbt.update(sql);
    }

    /**
     * 根据状态查找会议.
     * 
     * @Title: getMeetingByStatus
     * @Author: linuke
     * @Since: 2013-4-26下午1:16:47
     * @param status
     * @param flow
     *            处理流程
     * @return
     */
    public List<Meeting> getMeetingByStatus(String status, String flow) {
        StringBuilder strBuff = new StringBuilder();
        strBuff.append("select " + FIELD_SQL + FROM_SQL + " where status='");
        strBuff.append(status);
        strBuff.append("' and flow='");
        strBuff.append(flow);
        strBuff.append("' and endtime> sysdate");
        strBuff.append(" order by starttime");
        String sql = strBuff.toString();
        DBTools dbt = new DBTools(JndiName.INTRAWEB);
        List<Meeting> list = dbt.query(Meeting.class, sql);
        DBTools dbt2 = new DBTools(JndiName.JNPC);
        for (Meeting m : list) {
            String s = "select t.name as name from VIEW_PA_EMP_EMPLOYEE t where t.id= " + m.getCommiterid();
            Object obj = dbt2.queryReturnOne(s, "name");
            m.setCommiterid(obj == null ? "" : obj.toString());
        }
        return list;
    }

    /**
     * 审批会议
     * 
     * @Title: approve
     * @Author: linuke
     * @Since: 2013-4-26下午2:02:02
     * @param approveIDs
     *            批准的会议id
     * @param disapproveIDs
     *            拒批的会议id
     * @return
     */
    public int approve(String[] approveIDs, String[] disapproveIDs, String userid) {
        QueryRunner qr = new QueryRunner();
        String sqla = "update meeting m set m.roomid=(case when m.flow =1 then null else m.reserve_roomid end), status=(case when m.flow =1 then 1 else 3 end),approverid='"
                + userid + "' where id in(-1";
        String sqld = "update meeting set status=2,approverid='" + userid + "' where id in(-1";
        Connection conn = DbHelper.getConnection("intraweb");
        try {
            int flag = 0;
            conn.setAutoCommit(false);
            if (approveIDs != null && approveIDs.length > 0) {
                for (String approve : approveIDs) {
                    sqla += "," + approve;
                }
                sqla += ")";
                flag = qr.update(conn, sqla);
            }
            if (disapproveIDs != null && disapproveIDs.length > 0) {
                for (String disapprove : disapproveIDs) {
                    sqld += "," + disapprove;
                }
                sqld += ")";
                flag = qr.update(conn, sqld);
            }
            conn.commit();
            return flag;
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
            return -1;
        } finally {
            DbUtils.closeQuietly(conn);
        }
    }


    /**
     * 
     * @Title: allotRoom
     * @Author: linuke
     * @Since: 2013-5-24下午12:39:54
     * @param meetingid
     * @param roomid
     * @param userid
     * @param addr
     * @return
     */
    public int allotRoom(String meetingid, String roomid, String userid) {
        DBTools dbt = new DBTools(JndiName.INTRAWEB);
        String sql = "select starttime,endtime from meeting where id=" + meetingid;
        Meeting meeting = dbt.query(sql, Meeting.class);
        String starttime = DateUtil.dateToString(meeting.getStarttime(), "yyyy-MM-dd HH:mm");
        String endtime = DateUtil.dateToString(meeting.getEndtime(), "yyyy-MM-dd HH:mm");
        ;
        StringBuilder sb = new StringBuilder();
        sb.append("select roomID from meeting where ((to_date('");
        sb.append(starttime);
        sb.append("','yyyy-mm-dd hh24:mi') >=starttime and to_date('");
        sb.append(starttime);
        sb.append("','yyyy-mm-dd hh24:mi')<=endtime) or (to_date('");
        sb.append(endtime);
        sb.append("','yyyy-mm-dd hh24:mi') >=starttime and to_date('");
        sb.append(endtime);
        sb.append("','yyyy-mm-dd hh24:mi')<=endtime )");
        sb.append("or (starttime>to_date('");
        sb.append(starttime);
        sb.append("','yyyy-mm-dd hh24:mi') and endtime<=to_date('");
        sb.append(endtime);
        sb.append("','yyyy-mm-dd hh24:mi'))) and status='3'and id <> ");
        sb.append(meetingid);
        List<Object> roomids = dbt.query(sb.toString(), "roomid");
        if (roomids != null && roomids.contains(roomid)) {
            return -99;
        } else {
            return dbt.update("update meeting set status=3, roomid=" + roomid + ",alloterid=" + userid + " where id="
                    + meetingid);
        }
    }

    /**
     * 公司各处室月度会议计划及会议执行情况汇总
     * 
     * @Title: get
     * @Author: linuke
     * @param year
     * @param month
     * @return {"部门",[会议次数,计划会议次数]}
     */
    public Map<String, List<String>> getActualPlan(int year, int month) {
        String first = DateUtil.dateToString(DateUtil.getFirstDate(year, month), "yyyy-MM-dd");
        String last = DateUtil.dateToString(DateUtil.getLastDate(year, month), "yyyy-MM-dd");
        String sql = "select count(*),m.commitdepart from meeting m where 1=1";
        String sqlp = "select count(*),m.commitdepart from meeting_plan m where 1=1";
        sql += "and m.starttime between to_date('" + first + "','yyyy-mm-dd') and to_date('" + last
                + "','yyyy-mm-dd') ";
        sql += "and m.endtime between to_date('" + first + "','yyyy-mm-dd') and to_date('" + last + "','yyyy-mm-dd') ";
        sql += " group by m.commitdepart";
        sqlp += "and m.starttime between to_date('" + first + "','yyyy-mm-dd') and to_date('" + last
                + "','yyyy-mm-dd') ";
        sqlp += "and m.endtime between to_date('" + first + "','yyyy-mm-dd') and to_date('" + last + "','yyyy-mm-dd') ";
        sqlp += " group by m.commitdepart";
        DBTools dbt = new DBTools(JndiName.INTRAWEB);
        List<Object[]> list = dbt.query(sql);
        List<Object[]> listp = dbt.query(sqlp);
        List<Object[]> orgs;
        Map<String, List<String>> map = new LinkedHashMap<String, List<String>>();
        JNPC jnpc = new JNPC();
        orgs = jnpc.getAllORG();
        for (Object[] obj : orgs) {
            String o = obj[0] == null ? "" : obj[0].toString();
            List<String> templist = new ArrayList<String>();
            map.put(o, templist);
        }
        Map<String, String> mmap = new HashMap<String, String>();
        for (Object[] obj : list) {
            String o = obj[1] == null ? "" : obj[1].toString();// org
            String c = obj[0] == null ? "" : obj[0].toString();// count
            if (o != null && !"".equals(o)) {
                mmap.put(o, c);
            }
        }
        Map<String, String> mpmap = new HashMap<String, String>();
        for (Object[] obj : listp) {
            String o = obj[1] == null ? "" : obj[1].toString();// org
            String c = obj[0] == null ? "" : obj[0].toString();// count
            if (o != null && !"".equals(o)) {
                mpmap.put(o, c);
            }
        }
        Map<String, List<String>> rtnMap = new LinkedHashMap<String, List<String>>();
        for (Entry<String, List<String>> entry : map.entrySet()) {
            String org = entry.getKey();
            List<String> templist = new ArrayList<String>();
            String mcount = mmap.get(org);
            String mpcount = mpmap.get(org);
            if (mcount == null) {
                templist.add("");
            } else {
                templist.add(mcount);
            }
            if (mpcount == null) {
                templist.add("");
            } else {
                templist.add(mpcount);
            }
            rtnMap.put(org, templist);
        }
        return rtnMap;
    }

    public Map<String, String> getCosts(String starttime, String endtime) {
        String sql = "select m.commitdepart,sum(m.actual_costs) from meeting m " + "where m.starttime>=to_date('"
                + starttime + "','yyyy-mm-hh') and m.starttime <= to_date('" + endtime
                + "','yyyy-mm-hh') group by m.commitdepart";
        DBTools dbt = new DBTools(JndiName.INTRAWEB);
        List<Object[]> obj = dbt.query(sql);
        Map<String, String> map = new HashMap<String, String>();
        for (Object[] o : obj) {
            map.put(o[0] == null ? "" : o[0].toString(), o[1] == null ? "" : o[1].toString());
        }
        return map;
    }

    /**
     * 会议召开次数统计
     * 
     * @param year
     * @param month
     * @return
     */
    public List<Counter> meetingCount(int year, int month) {
        Date curDate = DateUtil.getDate(year, month, 0);//当前月的最后一天
        Date yearFirstDate = DateUtil.getDate(year, 0, 1);//年的第一天
        Date preDate = DateUtil.addMonth(curDate, -1);//上个月的最后一天
        Date firstDate = DateUtil.addDay(preDate, 1);//当前月的第一天
        Date firstNextDate = DateUtil.addDay(curDate, 1);//下个月的第一天
        Date nextDate = DateUtil.addMonth(curDate, -1);//下个月的最后一天
        String sql = "select m.commitdepart, count(*) from meeting m where " + "m.starttime >= to_date('"
                + DateUtil.dateToString(firstDate, "yyyy-MM-dd") + "','yyyy-mm-dd') and m.endtime <= to_date('"
                + DateUtil.dateToString(curDate, "yyyy-MM-dd") + "','yyyy-mm-dd') group by m.commitdepart";
        DBTools dbt = new DBTools(JndiName.INTRAWEB);
        List<Object[]> curCount = dbt.query(sql);//当月召开次数
        sql = "select m.commitdepart, count(*) from meeting m where " + "m.starttime >= to_date('"
                + DateUtil.dateToString(yearFirstDate, "yyyy-MM-dd") + "','yyyy-mm-dd') and m.endtime <= to_date('"
                + DateUtil.dateToString(preDate, "yyyy-MM-dd") + "','yyyy-mm-dd') group by m.commitdepart";
        List<Object[]> totalCount = dbt.query(sql);//累计召开次数
        sql = "select m.commitdepart, count(*) from meeting_plan m where " + "m.starttime >= to_date('"
                + DateUtil.dateToString(firstNextDate, "yyyy-MM-dd") + "','yyyy-mm-dd') and m.endtime <= to_date('"
                + DateUtil.dateToString(nextDate, "yyyy-MM-dd") + "','yyyy-mm-dd') group by m.commitdepart";
        List<Object[]> nextCount = dbt.query(sql);//下月召开次数
        List<Counter> rtnList = new ArrayList<Counter>();
        for (Object[] tobj : totalCount) {
            String cDepart = tobj[0] == null ? "" : tobj[0].toString();
            Counter counter = new Counter();
            counter.setDepart(cDepart);
            counter.setTotalCount(tobj[1] == null ? "" : tobj[1].toString());
            for (Object[] cobj : curCount) {
                String tDepart = cobj[0] == null ? "" : cobj[0].toString();
                if (tDepart.equals(cDepart)) {
                    counter.setCurCount(cobj[1] == null ? "" : cobj[1].toString());
                    break;
                }
            }
            for (Object[] nobj : nextCount) {
                String tDepart = nobj[0] == null ? "" : nobj[0].toString();
                if (tDepart.equals(cDepart)) {
                    counter.setNextCount(nobj[1] == null ? "" : nobj[1].toString());
                    break;
                }
            }
            rtnList.add(counter);
        }
        return rtnList;
    }

}
