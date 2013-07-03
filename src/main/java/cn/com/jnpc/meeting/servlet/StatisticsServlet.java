package cn.com.jnpc.meeting.servlet;

import java.util.ArrayList;
import java.util.List;

import cn.com.jnpc.meeting.bean.Counter;
import cn.com.jnpc.meeting.bean.Meeting;
import cn.com.jnpc.meeting.bean.MeetingRoom;
import cn.com.jnpc.meeting.dao.JNPC;
import cn.com.jnpc.meeting.dao.MeetingDao;
import cn.com.jnpc.meeting.dao.MeetingRoomDao;
import cn.com.jnpc.utils.DateUtil;
import cn.com.jnpc.utils.PropertyFilter;

/**
 * 处理统计用的Servlet
 */
public class StatisticsServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

    private MeetingRoomDao    meetingRoomDao   = new MeetingRoomDao();
    private MeetingDao        meetingDao       = new MeetingDao();
    private JNPC              jnpc             = new JNPC();


    public String meetingRoomQry() {
        List<MeetingRoom> resu = meetingRoomDao.getAllMeetingRoomShow();
        request.setAttribute("result", resu);
        String roomID = request.getParameter("roomID");
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        if (startTime == null || "".equals(startTime)) {
            startTime = DateUtil.getCurrentDate("yyyy-MM-dd");
        }
        if (endTime == null || "".equals(endTime)) {
            endTime = DateUtil.getCurrentDate("yyyy-MM-dd");
        }
        List<Meeting> res = meetingDao.getMeetingByRoomAndTime(roomID, startTime, endTime);
        request.setAttribute("meetings", res);
        return BASE_JSP + "statistics/meetingRoomQry.jsp";
    }

    public String sdetail() {
        String roomID = getParameter("roomID");
        String startTime = getParameter("startTime");
        String endTime = getParameter("endTime");
        String roomname = getParameter("roomname");
        PropertyFilter rp = new PropertyFilter("m.roomid:EQ_I", roomID);
        PropertyFilter sp = new PropertyFilter("m.starttime:GE_D", startTime);
        PropertyFilter ep = new PropertyFilter("m.endtime:LE_D", endTime);
        List<PropertyFilter> pfList = new ArrayList<PropertyFilter>();
        pfList.add(rp);
        pfList.add(sp);
        pfList.add(ep);
        // List<Object[]> list = meetingDao.getMeetingRoomDetailStatistics(roomID);
        List<Object[]> list = meetingDao.getMeetingRoom2DetailStatistics(pfList);
        request.setAttribute("title", roomname + "&nbsp;&nbsp;使用详细情况统计");
        request.setAttribute("result", list);
        return BASE_JSP + "statistics/statistics2Detail.jsp";
    }

    public String statistics() {
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        if ((startTime == null || "".equals(startTime)) && (endTime == null || "".equals(endTime))) {
            endTime = DateUtil.getCurrentDate("yyyy-MM-dd");
            startTime = DateUtil.dateToString(DateUtil.addMonth(DateUtil.stringToDate(endTime, "yyyy-MM-dd"), -1),
                    "yyyy-MM-dd");
        }
        String org = request.getParameter("org");
        List<Object[]> list = meetingDao.getMeetingStatistics(startTime, endTime, org);
        request.setAttribute("result", list);
        List<Object[]> orgs = jnpc.getAllORG();
        request.setAttribute("orgs", orgs);// 所有的部门
        request.setAttribute("title", "会议通知处理情况统计查询");
        return BASE_JSP + "statistics/statistics.jsp";
    }

    public String statistics2() {
        String roomID = getParameter("_roomID");
        String startTime = getParameter("startTime");
        String endTime = getParameter("endTime");
        PropertyFilter rp = new PropertyFilter("m.roomid:EQ_I", roomID);
        PropertyFilter sp = new PropertyFilter("m.starttime:GE_D", startTime);
        PropertyFilter ep = new PropertyFilter("m.endtime:LE_D", endTime);
        List<PropertyFilter> pfList = new ArrayList<PropertyFilter>();
        pfList.add(rp);
        pfList.add(sp);
        pfList.add(ep);
        List<MeetingRoom> mrs = meetingRoomDao.getMeetingRoom();
        request.setAttribute("mrs", mrs);
        // request.setAttribute("mrs", meetingRoom2Dao.getParentRoom());//
        // 所有的会议室建筑
        request.setAttribute("title", "会议室情况统计表");
        // List<Object[]> list = meetingDao.getMeetingRoomStatistics (roomID);
        List<Object[]> list = meetingDao.getMeetingRoom2Statistics(pfList);
        request.setAttribute("result", list);
        return BASE_JSP + "statistics/statistics2.jsp";
    }

    /**
     * 去查找会议室使用情况。
     * 
     * @Title: toMeetingRoomQry
     * @return
     */
    public String toMeetingRoomQry() {
        request.setAttribute("title", "会议室详细信息查询");
        List<MeetingRoom> res = meetingRoomDao.getAllMeetingRoomShow();
        request.setAttribute("result", res);
        return BASE_JSP + "statistics/meetingRoomQry.jsp";
    }

    public String count() {
        String year = request.getParameter("year");
        String month = request.getParameter("month");
        if (isEmpty(year)) {
            year = DateUtil.getCurrentDate("yyyy");
        }
        if (isEmpty(month)) {
            month = DateUtil.getCurrentDate("MM");
        }
        List<Counter> counters = meetingDao.meetingCount(Integer.parseInt(year), Integer.parseInt(month));
        request.setAttribute("counters", counters);
        request.setAttribute("title", "会议召开次数统计");
        return BASE_JSP + "statistics/count.jsp";
    }

    public String meetingAndMeetingPlan() {
        String y = request.getParameter("year");
        String m = request.getParameter("month");
        request.setAttribute("title", "会议计划及会议执行情况");
        int year = (y == null || "".equals(y)) ? Integer.parseInt(DateUtil.getCurrentDate("yyyy")) : Integer
                .parseInt(y);
        int month = ((m == null || "".equals(m)) ? Integer.parseInt(DateUtil.getCurrentDate("MM")) : Integer
                .parseInt(m));
        request.setAttribute("result", meetingDao.getActualPlan(year, month));
        return BASE_JSP + "statistics/asp.jsp";
    }

}
