package cn.com.jnpc.meeting.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;

import cn.com.jnpc.meeting.bean.MeetingCommon;
import cn.com.jnpc.meeting.dao.util.DbHelper;

/**
 * 系统设置的操作
 * @author linuke
 *
 */
public class MeetingCommonDao {

    public List<MeetingCommon> getALlMeetingCommon(){
	String sql = "select t.id,t.title,t.type from MEETING_COMMON t where type in (1,2) group by t.type,t.id,t.title order by t.id desc";
	QueryRunner qr = DbHelper.getIntrawebQueryRunner();
	try {
	    List<MeetingCommon> list = (List<MeetingCommon>) qr.query(sql, new BeanListHandler<MeetingCommon>(MeetingCommon.class));
	    return list;
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return null;
    }
    
    public boolean update(String id,String title){
	String sql = "update MEETING_COMMON set title='"+title+"' where id="+id;
	QueryRunner qr = DbHelper.getIntrawebQueryRunner();
	try {
	    int flag = qr.update(sql);
	    if(flag != -1){
		return true;
	    }
	} catch (SQLException e) {
	    e.printStackTrace();
	    return false;
	    
	}
	return false;
    }
    
    public boolean add(String title,String type){
	String sql = "insert into MEETING_COMMON (id,title,type) values(seq_meeting_common_id.nextval,'"+title+"',"+type+")";
	QueryRunner qr = DbHelper.getIntrawebQueryRunner();
	try {
	    int flag = qr.update(sql);
	    if(flag != -1){
		return true;
	    }
	} catch (SQLException e) {
	    e.printStackTrace();
	    return false;
	    
	}
	return false;
    }
    
    public boolean delete(String id){
	String sql = "delete MEETING_COMMON where id="+id;
	QueryRunner qr = DbHelper.getIntrawebQueryRunner();
	try {
	    int flag = qr.update(sql);
	    if(flag != -1){
		return true;
	    }
	    return false;
	} catch (SQLException e) {
	    e.printStackTrace();
	    return false;
	}
    }
    
    /**
     * 外单位名称和固定称谓
     * 
     * @param str
     * @return
     */
    public List<Object> getCommons(String str) {
	String sql = "select t.title from MEETING_COMMON t where lower(F_TRANS_PINYIN_CAPITAL(t.title)) like '%'||lower('?')||'%' or t.title like '%?%' ";
	QueryRunner qr = DbHelper.getIntrawebQueryRunner();
	try {
	    List<Object> list = qr.query(sql.replaceAll("\\?", str),
		    new ColumnListHandler<Object>("title"));
	    return list;
	} catch (SQLException e) {
	    e.printStackTrace();
	    return null;
	}
	
    }
    
    /**
     * 添加外来单位
     * @param str
     */
    public void addWldw(String str) {
	String sql = "insert into meeting_common (id,title,type)values(seq_meeting_common.nextval,?,2)";
	QueryRunner qr = DbHelper.getIntrawebQueryRunner();
	try {
	    qr.update(sql, str);
	} catch (SQLException e) {
	    e.printStackTrace();
	}
    }
}
