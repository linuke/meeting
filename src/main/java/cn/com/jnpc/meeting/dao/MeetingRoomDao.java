package cn.com.jnpc.meeting.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;

import cn.com.jnpc.meeting.bean.Meeting;
import cn.com.jnpc.meeting.bean.MeetingRoom;
import cn.com.jnpc.meeting.dao.util.DBTools;
import cn.com.jnpc.meeting.dao.util.JndiName;
import cn.com.jnpc.meeting.dao.util.DbHelper;
import cn.com.jnpc.meeting.dao.util.SQLStatementGetter;
import cn.com.jnpc.utils.DateUtil;
import cn.com.jnpc.utils.Page;
import cn.com.jnpc.utils.PropertyFilter;
import cn.com.jnpc.utils.QueryUtil;

public class MeetingRoomDao {

    /**
     * 所有的会议室建筑.
     * 
     * @Title: getAllBuilding
     * @return
     */
    public List<String> getAllBuilding() {
        QueryRunner qr = DbHelper.getIntrawebQueryRunner();
        String sql = "select distinct t.building from MEETINGROOM t where t.deleted =0 and t.type=1 group by t.building";
        try {
            return qr.query(sql, new ColumnListHandler<String>("building"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取所有会议室
     * 
     * @Title: getMeetingRoom
     * @return
     */
    public List<MeetingRoom> getMeetingRoom() {
        QueryRunner qr = DbHelper.getIntrawebQueryRunner();
        String sql = "select id,building,room,capacity,remark from meetingroom where deleted='0' and type=1 order by building,capacity";
        try {
            List<MeetingRoom> list = (List<MeetingRoom>) qr.query(sql, new BeanListHandler<MeetingRoom>(
                    MeetingRoom.class));
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取所有培训教室
     * 
     * @Title: getMeetingRoom
     * @return
     */
    public List<MeetingRoom> getMeetingRoom2() {
        QueryRunner qr = DbHelper.getIntrawebQueryRunner();
        String sql = "select id,building,room,capacity,remark from meetingroom where deleted='0' and type=2 order by building,capacity";
        try {
            List<MeetingRoom> list = (List<MeetingRoom>) qr.query(sql, new BeanListHandler<MeetingRoom>(
                    MeetingRoom.class));
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<MeetingRoom> getAllMeetingRoomShow() {
        QueryRunner qr = DbHelper.getIntrawebQueryRunner();
        String sql =
            "select id,building,room,capacity,remark from meetingroom where deleted='0' order by building,capacity";
        try {
            List<MeetingRoom> list =
                (List<MeetingRoom>)qr.query(sql, new BeanListHandler<MeetingRoom>(MeetingRoom.class));
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public Page<MeetingRoom> getMeetingRoom(Page<MeetingRoom> page, List<PropertyFilter> pfList) {
        int size = page.getPageSize();
        int pageNo = page.getPageNo();
        int tempPageNo = 0;
        if (pageNo < 1) {
            tempPageNo = 1;
        } else {
            tempPageNo = pageNo;
        }
        String columnSql = "id,building,room,capacity,remark ";
        String cSql = "from meetingroom where deleted='0'";
        String conSql = QueryUtil.toSqlString(pfList, false);;
        String sql =
            SQLStatementGetter.getPageQueryStatement("select "+columnSql + cSql+conSql + " order by building", (tempPageNo - 1) * size + 1, tempPageNo * size);
        DBTools dbt = new DBTools(JndiName.INTRAWEB);
        page.setTotalCount(dbt.getCount("select count(*)" + cSql+conSql));
        page.setResult(dbt.query(MeetingRoom.class, sql));
        return page;
    }
    
    public int add(String building, String room, String capacity, String remark, String type) {
        String sql = "insert into meetingroom(id,building,room,capacity,remark,type)values(seq_meetingroom_id.nextval,?,?,?,?,?)";
        DBTools dbt = new DBTools(JndiName.INTRAWEB);
        return dbt.update(sql, building, room, capacity, remark, type);
    }
    
    public int update(MeetingRoom mr) {
        String sql = SQLStatementGetter.getUpdateStatement(mr, "meetingroom", "id");
        DBTools dbt = new DBTools(JndiName.INTRAWEB);
        return dbt.update(sql);
    }
    
    public int delete(String id) {
        String sql = "delete meetingroom where id=" + id;
        DBTools dbt = new DBTools(JndiName.INTRAWEB);
        return dbt.update(sql);
    }
    
    public MeetingRoom findById(String id){
        String sql = "select * from meetingroom where id="+id;
        DBTools dbt = new DBTools(JndiName.INTRAWEB);
        return dbt.query(sql, MeetingRoom.class);
    }
    
    public List<MeetingRoom> getFreeRoom(String meetingId){
        DBTools dbt = new DBTools(JndiName.INTRAWEB);
        String sql = "select starttime,endtime from meeting where id="+meetingId;
        Meeting meeting = dbt.query(sql, Meeting.class);
        String starttime = DateUtil.dateToString(meeting.getStarttime(), "yyyy-MM-dd HH:mm");
        String endtime = DateUtil.dateToString(meeting.getEndtime(), "yyyy-MM-dd HH:mm");
        StringBuilder sb = new StringBuilder();
        sb.append("select * from meetingroom where id not in(");
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
        sb.append("','yyyy-mm-dd hh24:mi'))) and status='3'and id <> '");
        sb.append(meetingId);
        sb.append("') and deleted='0' order by building,capacity");
        return dbt.query(MeetingRoom.class, sb.toString());
    }

}
