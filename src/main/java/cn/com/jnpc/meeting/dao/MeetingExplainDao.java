package cn.com.jnpc.meeting.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import cn.com.jnpc.meeting.bean.MeetingExplain;
import cn.com.jnpc.meeting.dao.util.DbHelper;

public class MeetingExplainDao {

    /**
     * 插入会议有关说明
     * 
     * @param name
     * @param style
     * @param unit
     * @param parentid
     * @return
     */
    public boolean add(String name, String style, String unit,String display,
	    String parentid) {
	String sql = "insert into meeting_explain (id,name,style,unit,display,parentid) values(SEQ_MEETING_EXPLAIN_ID.Nextval,?,?,?,?,?)";
	QueryRunner qr = DbHelper.getIntrawebQueryRunner();
	try {
	    int flag = qr.update(sql, name, style, unit,display, parentid);
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
     * 更新有关说明
     * @param id
     * @param name
     * @param style
     * @param unit
     * @param display
     * @return
     */
    public boolean update(String id,String name,String style,String unit,String display){
	String sql = "update meeting_explain set name=?,style=?,unit=?,display=? where id=?";
	QueryRunner qr = DbHelper.getIntrawebQueryRunner();
	try {
	    int flag = qr.update(sql, name, style, unit, display, id);
	    if (flag == -1) {
		return false;
	    }
	    return true;
	} catch (SQLException e) {
	    e.printStackTrace();
	    return false;
	}
    }
    
    public boolean del(String id){
	String sql = "delete MEETING_EXPLAIN where id=? or parentid=?";
	QueryRunner qr = DbHelper.getIntrawebQueryRunner();
	try {
	    int flag = qr.update(sql, id, id);
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
     * 获取所有显示的说明项
     * 
     * @return
     */
    public List<MeetingExplain> getAllShow() {
	String sql1 = "select * from MEETING_EXPLAIN t where t.display=1 and t.parentid is null order by t.style";// 所有的父类
	QueryRunner qr = DbHelper.getIntrawebQueryRunner();
	try {
	    List<MeetingExplain> parents = (List<MeetingExplain>) qr.query(
		    sql1, new BeanListHandler<MeetingExplain>(MeetingExplain.class));
	    for (MeetingExplain me : parents) {
		String id = me.getId();
		if (id != null && !"".equals(id)) {
		    String sql = "select * from MEETING_EXPLAIN t where t.parentid ="
			    + id;
		    List<MeetingExplain> child = (List<MeetingExplain>) qr
			    .query(sql, new BeanListHandler<MeetingExplain>(
				    MeetingExplain.class));
		    me.setChild(child);
		}

	    }
	    return parents;
	} catch (SQLException e) {
	    e.printStackTrace();
	    return null;
	}
    }

    /**
     * 获取所有的说明项
     * 
     * @return
     */
    public List<MeetingExplain> getAll() {
	String sql1 = "select * from MEETING_EXPLAIN t where t.parentid is null  order by t.id desc";// 所有的父类
	QueryRunner qr = DbHelper.getIntrawebQueryRunner();
	try {
	    List<MeetingExplain> parents = (List<MeetingExplain>) qr.query(
		    sql1, new BeanListHandler<MeetingExplain>(MeetingExplain.class));
	    for (MeetingExplain me : parents) {
		String id = me.getId();
		if (id != null && !"".equals(id)) {
		    String sql = "select * from MEETING_EXPLAIN t where t.parentid ="
			    + id;
		    List<MeetingExplain> child = (List<MeetingExplain>) qr
			    .query(sql, new BeanListHandler<MeetingExplain>(
				    MeetingExplain.class));
		    me.setChild(child);
		}

	    }
	    return parents;
	} catch (SQLException e) {
	    e.printStackTrace();
	    return null;
	}
    }
}
