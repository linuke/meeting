package cn.com.jnpc.meeting.dao;

import cn.com.jnpc.meeting.bean.BookRoom;
import cn.com.jnpc.meeting.dao.util.DbUtilsTemplate;
import cn.com.jnpc.meeting.dao.util.JndiName;
import cn.com.jnpc.meeting.dao.util.SQLStatementGetter;

public class BookRoomDao {
	
    private DbUtilsTemplate intraweb = null;
	
	public BookRoomDao() {
        intraweb = new DbUtilsTemplate(JndiName.INTRAWEB);
	}
	
    public int save(BookRoom br) {
        String sql = SQLStatementGetter.getInsertStatement(br, "meeting_bookroom");
        return intraweb.update(sql);
    }

    public int update(BookRoom br) {
        String sql = SQLStatementGetter.getUpdateStatement(br, "meeting_bookroom", "id");
        return intraweb.update(sql);
    }

    public int delete(String id) {
        String sql = "delete meeting_bookroom where id = ?";
        return intraweb.update(sql, id);
    }
}
