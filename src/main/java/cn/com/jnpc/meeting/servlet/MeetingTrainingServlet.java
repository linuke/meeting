package cn.com.jnpc.meeting.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;

import cn.com.jnpc.meeting.bean.Meeting;
import cn.com.jnpc.meeting.bean.MeetingRoom;
import cn.com.jnpc.meeting.dao.JNPC;
import cn.com.jnpc.meeting.dao.MeetingDao;
import cn.com.jnpc.meeting.dao.MeetingExplainDao;
import cn.com.jnpc.meeting.dao.MeetingRoomDao;
import cn.com.jnpc.utils.Page;
import cn.com.jnpc.utils.PropertyFilter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MeetingTrainingServlet extends BaseServlet {

    private static final long serialVersionUID  = 1L;
    private MeetingRoomDao    meetingRoomDao    = new MeetingRoomDao();
    private JNPC              jnpc              = new JNPC();
    private MeetingExplainDao meetingExplainDao = new MeetingExplainDao();
    private MeetingDao        meetingDao        = new MeetingDao();
    private Page<Meeting>     page              = new Page<Meeting>(15);

    /**
     * 添加培训通知
     * 
     * @Title: add
     * @return
     */
    public String add() {
        int flag;
        String json = request.getParameter("json");
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm").create();
        Meeting m = gson.fromJson(json, Meeting.class);
        m.setCommiterid(userid);
        flag = meetingDao.meetingAdd(m, userid);
        // 添加记录并返回是否添加成功
        if (flag != -1 && flag != -999 && flag != -99 && flag != -98) { // 添加成功
            return listByDepart();
        } else if (flag == -99) {
            error = "教员在此时间段正参与其他会议，请检查！";
        } else if (flag == -999) {
            error = "已经存在一条相同的记录！";
        } else { // 添加不成功
            error = "";
        }
        return toErrorPage(error);
    }

    public String approve() throws ServletException, IOException {
        if (vec.contains("380701")) {
            request.setAttribute("meetings", meetingDao.getMeetingByStatus("0", "2"));
            request.setAttribute("title", "培训通知审批");
            return BASE_JSP + "meetingTrain/approve.jsp";
        } else {
            error = "对不起，您没有培训通知审核的权限！";
            return toErrorPage(error);
        }
    }

    public String approved() throws ServletException, IOException {
        int flag;
        String[] approves = request.getParameterValues("approve");
        String[] disapproves = request.getParameterValues("disapprove");
        flag = meetingDao.approve(approves, disapproves, userid);
        if (flag != -1) {
            return approve();
        } else {
            return toErrorPage(error);
        }
    }

    public String list() {
        String starttime = request.getParameter("starttime");
        String starttime2 = request.getParameter("starttime2");
        String endtime = request.getParameter("endtime");
        String endtime2 = request.getParameter("endtime2");
        String org = request.getParameter("org");
        String roomID = request.getParameter("roomID");
        String content = request.getParameter("content");
        String pageNo = request.getParameter("pageNo");
        request.setAttribute("starttime", starttime);
        request.setAttribute("starttime2", starttime2);
        request.setAttribute("endtime", endtime);
        request.setAttribute("endtime2", endtime2);
        request.setAttribute("org", org);
        request.setAttribute("roomID", roomID);
        request.setAttribute("content", content);
        PropertyFilter st = new PropertyFilter("m.starttime:GE_D", starttime);
        PropertyFilter st2 = new PropertyFilter("m.starttime:LE_D", starttime2);
        PropertyFilter et = new PropertyFilter("m.endtime:GE_D", endtime);
        PropertyFilter et2 = new PropertyFilter("m.endtime:LE_D", endtime2);
        PropertyFilter orgpf = new PropertyFilter("m.commitdepart:EQ_S", org);
        PropertyFilter rpf = new PropertyFilter("m.roomid:EQ_I", roomID);
        PropertyFilter cpf = new PropertyFilter("m.content:LIKE_S", content);
        PropertyFilter t = new PropertyFilter("m.type:EQ_I", "4");
        List<PropertyFilter> pfList = new ArrayList<PropertyFilter>();
        pfList.add(st);
        pfList.add(st2);
        pfList.add(et);
        pfList.add(et2);
        pfList.add(orgpf);
        pfList.add(rpf);
        pfList.add(cpf);
        pfList.add(t);
        if (pageNo == null || "".equals(pageNo)) {
            page.setPageNo(1);
        } else {
            page.setPageNo(Integer.parseInt(pageNo));
        }
        page.setForwordName("MeetingTrainServlet?ctrl=list&starttime=" + starttime + "&starttime2=" + starttime2
                + "&endtime=" + endtime + "&endtime2=" + endtime2 + "&org=" + org + "&roomID=" + roomID + "&content="
                + content + "&pageNo=");
        request.setAttribute("meetings", meetingDao.getMeeting(page, pfList).getResult());
        request.setAttribute("tag", page.getTag());
        request.setAttribute("orgs", jnpc.getAllORG());// 所有部门
        List<MeetingRoom> mrs = meetingRoomDao.getMeetingRoom();
        request.setAttribute("mrs", mrs);
        request.setAttribute("title", "培训通知查询");
        return BASE_JSP + "meetingTrain/list.jsp";
    }

    public String listByDepart() {
        String pageNo = request.getParameter("pageNo");
        if (pageNo == null || "".equals(pageNo)) {
            page.setPageNo(1);
        } else {
            page.setPageNo(Integer.parseInt(pageNo));
        }
        page.setForwordName("MeetingTrainingServlet?ctrl=listByDepart&pageNo=");
        request.setAttribute("meetings", meetingDao.getMeetingTrainByUserid(page, userid).getResult());
        request.setAttribute("tag", page.getTag());
        request.setAttribute("title", "培训通知申请浏览");
        return BASE_JSP + "meetingTrain/listByDepart.jsp";
    }

//    public String meetingTrainlist() {
//        request.setAttribute("orgs", jnpc.getAllORG());// 所有部门
//        List<MeetingRoom2> mrs = meetingRoom2Dao.getParentRoom();
//        request.setAttribute("mrs", mrs);
//        request.setAttribute("title", "培训通知浏览");
//        request.setAttribute("meetings", meetingDao.getMeeting(page, new ArrayList<PropertyFilter>()).getResult());
//        request.setAttribute("tag", page.getTag());
//        return BASE_JSP + "meetingTrain/list.jsp";
//    }

    public String toAdd() {
        if (vec.contains("380601")) {
            // 所有的会议室建筑
            // request.setAttribute ("mrs", meetingRoom2Dao.getParentRoom ());
            // 所有的培训教室
            // request.setAttribute ("trs", meetingRoom2Dao.getTrainingRoom ());
            // 培训教室
            request.setAttribute("mrs2", meetingRoomDao.getMeetingRoom2());
            // 会议室
            request.setAttribute("mrs", meetingRoomDao.getMeetingRoom());
            request.setAttribute("orgs", jnpc.getAllORG());// 所有部门
            request.setAttribute("ctrl", "add");
            request.setAttribute("u_org", u_org);
            request.setAttribute("title", "培训通知申请");// 页面标题
//            request.setAttribute("mes", meetingExplainDao.getAllShow());// 所有显示的有关说明
            return BASE_JSP + "meetingTrain/meetingTrain.jsp";
        } else {
            // 没有权限
            error = "对不起，您没有部门培训通知申请的权限！";
            return toErrorPage(error);
        }
    }

    public String toQuery() {
        if (vec.contains("380601")) {
//            return meetingTrainlist();
            return list();
        } else {
            error = "对不起，您没有部门培训通知浏览的权限！";
            return toErrorPage(error);
        }
    }

    public String toUpdate() {
        if (vec.contains("380601")) {
            String id = request.getParameter("id");
            Meeting meeting = meetingDao.getMeetingById(id);
            getParameter("show");
            request.setAttribute("meeting", meeting);
            // 培训教室
            request.setAttribute("mrs2", meetingRoomDao.getMeetingRoom2());
            // 会议室
            request.setAttribute("mrs", meetingRoomDao.getMeetingRoom());
            request.setAttribute("orgs", jnpc.getAllORG());// 所有部门
            request.setAttribute("ctrl", "update");
            request.setAttribute("u_org", u_org);
            request.setAttribute("title", "培训通知修改");// 页面标题
            request.setAttribute("mes", meetingExplainDao.getAllShow());// 所有显示的有关说明
            return BASE_JSP + "meetingTrain/meetingTrain.jsp";
        } else {
            // 没有权限
            error = "对不起，您没有部门培训通知修改的权限！";
            return toErrorPage(error);
        }
    }

    /**
     * 修改培训通知
     * 
     * @Title: update
     * @return
     */
    public String update() {
        int flag;
        // String url = request.getParameter ("url");
        String show = request.getParameter("show");
        String json = request.getParameter("json");
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm").create();
        Meeting m = gson.fromJson(json, Meeting.class);
        flag = meetingDao.meetingUpdate(m);
        if (flag != -1 && flag != -999 && flag != -99 && flag != -98) {
            if ("my".equals(show)) {
                return listByDepart();
            } else {
                return list();
            }
        } else if (flag == -99) {
            error = "教员在此时间段正参与其他会议，请检查！";
        } else if (flag == -999) {
            error = "已经存在一条相同的记录！";
        } else {
            error = "";
        }
        return toErrorPage(error);
    }

    public String toReAllot() {
        if (vec.contains("380801")) {
            request.setAttribute("title", "重新分配培训教室");
            request.setAttribute("btn", "调配会议室");// 页面按钮文字
            // 获取所有的建筑
            // List<MeetingRoom2> mrs = meetingRoom2Dao.getParentRoom();
            request.setAttribute("mrs", meetingRoomDao.getMeetingRoom2());
            request.setAttribute("meetings", meetingDao.getMeetingByStatus("3", "2"));
            return BASE_JSP + "meetingTrain/reAllot.jsp";
        } else {
            error = "对不起，您没有重新分配培训教室的权限！";
            return toErrorPage(error);
        }
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
}
