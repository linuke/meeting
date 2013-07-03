package cn.com.jnpc.meeting.servlet;

import java.io.IOException;

import java.util.List;

import javax.servlet.ServletException;

import cn.com.jnpc.meeting.bean.MeetingCommon;
import cn.com.jnpc.meeting.dao.MeetingCommonDao;

public class MeetingCommonServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	private MeetingCommonDao meetingCommonDao = new MeetingCommonDao();

	public String del() throws ServletException, IOException {
		String id = request.getParameter("id");
		meetingCommonDao.delete(id);
		return list();
	}

	public String update() throws ServletException, IOException {
		String title = request.getParameter("name");
		String id = request.getParameter("id");
		meetingCommonDao.update(id, title);
		return list();
	}

	public String add() throws ServletException, IOException {
		String title = request.getParameter("name");
		String type = request.getParameter("type");
		meetingCommonDao.add(title, type);
		return list();
	}

	public String list() throws ServletException, IOException {
		List<MeetingCommon> list = meetingCommonDao.getALlMeetingCommon();
		request.setAttribute("mcs", list);
		return BASE_JSP+"setting/mc.jsp";
	}

}
