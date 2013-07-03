package cn.com.jnpc.meeting.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import cn.com.jnpc.meeting.bean.MeetingMoney;
import cn.com.jnpc.meeting.dao.JNPC;
import cn.com.jnpc.meeting.dao.MeetingMoneyDao;
import cn.com.jnpc.utils.DateUtil;

/**
 * 会议经费
 */
public class MeetingMoneyServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	private JNPC jnpc = new JNPC();
	private MeetingMoneyDao meetingMoneyDao = new MeetingMoneyDao();

	public static final int SUCCEED = 1;
	public static final int ERROR = 0;

	public String del() throws ServletException, IOException {
		boolean flag;
		String id = request.getParameter("id");
		flag = meetingMoneyDao.delete(id);
		if (flag) {
			request.setAttribute("msg", SUCCEED);
		} else {
			request.setAttribute("msg", ERROR);
		}
		return list();
	}

	public String update() throws ServletException, IOException {
		boolean flag;
		String id = request.getParameter("id");
		String money = request.getParameter("money");
		flag = meetingMoneyDao.update(id, money);
		if (flag) {
			request.setAttribute("msg", SUCCEED);
		} else {
			request.setAttribute("msg", ERROR);
		}
		return list();
	}

	public String add() throws ServletException, IOException {
		boolean flag;
		String money = request.getParameter("money");
		Gson gson = new Gson();
		List<MeetingMoney> mms = gson.fromJson(money,
				new TypeToken<List<MeetingMoney>>() {
				}.getType());
		flag = meetingMoneyDao.batch(mms);
		if (flag) {
			request.setAttribute("msg", SUCCEED);
		} else {
			request.setAttribute("msg", ERROR);
		}
		return list();
	}

	public String toAdd() throws ServletException, IOException {
		String year = DateUtil.getCurrentDate("yyyy");// 当前年
		request.setAttribute("year", year);
		request.setAttribute("orgs", jnpc.getAllORG());// 处室信息
		return  BASE_JSP+"meetingMoney/meetingMoneyAdd.jsp";
	}

	public String list() throws ServletException, IOException {
		String org = request.getParameter("org");
		String y = request.getParameter("year");
		request.setAttribute("orgs", jnpc.getAllORG());// 处室信息
		String year = DateUtil.getCurrentDate("yyyy");// 当前年
		request.setAttribute("year", year);
		request.setAttribute("mms", meetingMoneyDao.find(org, y));
		return  BASE_JSP+"meetingMoney/meetingMoneyList.jsp";
	}

}
