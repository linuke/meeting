package cn.com.jnpc.meeting.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;

import cn.com.jnpc.meeting.bean.Meeting;
import cn.com.jnpc.meeting.bean.MeetingRoom;
import cn.com.jnpc.meeting.dao.MeetingDao;
import cn.com.jnpc.meeting.dao.MeetingRoomDao;
import cn.com.jnpc.utils.Page;
import cn.com.jnpc.utils.PropertyFilter;

public class MeetingRoomServlet extends BaseServlet {

    private static final long serialVersionUID = 1L;
    private Page<MeetingRoom> page             = new Page<MeetingRoom>(30);
    private MeetingRoomDao    meetingRoomDao   = new MeetingRoomDao();
    private MeetingDao        meetingDao       = new MeetingDao();

    public MeetingRoomServlet() {
        super();
    }

    public String del() {
        int flag;
        String id = request.getParameter("id");
        flag = meetingRoomDao.delete(id);
        if (flag != -1) {
            return list();
        } else {
            return toErrorPage(error);
        }
    }

    public String update() {
        int flag;
        String building = request.getParameter("building");
        String room = request.getParameter("room");
        String capacity = request.getParameter("capacity");
        String remark = request.getParameter("remark");
        String id = request.getParameter("id");
        String type = request.getParameter("type");
        String from = getParameter("from");
        MeetingRoom meetingRoom = new MeetingRoom(id, building, room, capacity, remark, type);
        flag = meetingRoomDao.update(meetingRoom);
        if (flag != -1) {
        	if("cr".equals(from)){
        		return listClassRoom();
        	}else {
        		return list();
        	}
        } else {
            return toErrorPage(error);
        }
    }

    public String toUpdate() {
        String id = request.getParameter("id");
        String from = getParameter("from");
        if ("cr".equals(from)) {
            request.setAttribute("title", "修改培训教室");
            request.setAttribute("ctrl", "update");
            request.setAttribute("mr", meetingRoomDao.findById(id));
            return BASE_JSP + "meetingRoom/classroom.jsp";
        } else {
            request.setAttribute("bds", meetingRoomDao.getAllBuilding());
            request.setAttribute("title", "修改会议室");
            request.setAttribute("ctrl", "update");
            request.setAttribute("mr", meetingRoomDao.findById(id));
            return BASE_JSP + "meetingRoom/meetingRoom.jsp";
        }

    }

    public String add() {
        int flag;
        String building = request.getParameter("building");
        String room = request.getParameter("room");
        String capacity = request.getParameter("capacity");
        String remark = request.getParameter("remark");
        String type = request.getParameter("type");
        flag = meetingRoomDao.add(building, room, capacity, remark, type);
        if (flag != -1) {
            return list();
        } else {
            return toErrorPage(error);
        }
    }

    public String toAdd() {
        String error;
        if (vec.contains("380501")) {
            request.setAttribute("title", "添加会议室");
            request.setAttribute("ctrl", "add");
            request.setAttribute("bds", meetingRoomDao.getAllBuilding());
            return BASE_JSP + "meetingRoom/meetingRoom.jsp";
        } else {
            error = "对不起，您没有会议室添加的权限！";
            return toErrorPage(error);
        }
    }

    /**
     * 
     * @Title: toAddClassRoom
     * @Author: linuke
     * @Since: 2013年6月8日下午4:07:30
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String toAddClassRoom() {
        String error;
        if (vec.contains("380502")) {
            request.setAttribute("title", "添加培训教室");
            request.setAttribute("ctrl", "add");
            request.setAttribute("bds", meetingRoomDao.getAllBuilding());
            return BASE_JSP + "meetingRoom/classroom.jsp";
        } else {
            error = "对不起，您没有培训教室添加的权限！";
            return toErrorPage(error);
        }
    }

    public String listClassRoom() {
        if (vec.contains("380502")) {
            String building = request.getParameter("_building");
            String room = request.getParameter("_room");
            getParameter("from");
            building = building == null ? "" : building;
            room = room == null ? "" : room;
            request.setAttribute("building", building);
            request.setAttribute("room", room);
            String pageNo = request.getParameter("pageNo");
            if (pageNo == null || "".equals(pageNo)) {
                page.setPageNo(1);
            } else {
                page.setPageNo(Integer.parseInt(pageNo));
            }
            PropertyFilter bpf = new PropertyFilter("building:LIKE_S", building);
            PropertyFilter rpf = new PropertyFilter("room:LIKE_S", room);
            PropertyFilter tpf = new PropertyFilter("type:EQ_I", "2");
            List<PropertyFilter> pfList = new ArrayList<PropertyFilter>();
            pfList.add(bpf);
            pfList.add(rpf);
            pfList.add(tpf);
            page.setForwordName("MeetingRoomServlet?ctrl=list&from=cr&building=" + building + "&room=" + room
                    + "&pageNo=");
            request.setAttribute("mrs", meetingRoomDao.getMeetingRoom(page, pfList).getResult());
            request.setAttribute("tag", page.getTag());
            request.setAttribute("title", "培训教室管理");
            return BASE_JSP + "meetingRoom/meetingRoomList.jsp";
        } else {
            error = "对不起，您没有培训教室维护的权限！";
            return toErrorPage(error);
        }
    }

    public String list() {
        if (vec.contains("380501")) {
            String building = request.getParameter("_building");
            String room = request.getParameter("_room");
            building = building == null ? "" : building;
            room = room == null ? "" : room;
            building = building == "null" ? "" : building;
            room = room == "null" ? "" : room;
            request.setAttribute("building", building);
            request.setAttribute("room", room);
            String pageNo = request.getParameter("pageNo");
            if (pageNo == null || "".equals(pageNo)) {
                page.setPageNo(1);
            } else {
                page.setPageNo(Integer.parseInt(pageNo));
            }
            PropertyFilter bpf = new PropertyFilter("building:LIKE_S", building);
            PropertyFilter rpf = new PropertyFilter("room:LIKE_S", room);
            PropertyFilter tpf = new PropertyFilter("type:EQ_I", "1");
            List<PropertyFilter> pfList = new ArrayList<PropertyFilter>();
            pfList.add(bpf);
            pfList.add(rpf);
            pfList.add(tpf);
            page.setForwordName("MeetingRoomServlet?ctrl=list&from=mr&building=" + building + "&room=" + room
                    + "&pageNo=");
            request.setAttribute("mrs", meetingRoomDao.getMeetingRoom(page, pfList).getResult());
            request.setAttribute("tag", page.getTag());
            request.setAttribute("title", "会议室管理");
            return BASE_JSP + "meetingRoom/meetingRoomList.jsp";
        } else {
            error = "对不起，您没有会议室维护的权限！";
            return toErrorPage(error);
        }
    }

    public void roomDetail() {
        String roomid = request.getParameter("roomID");
        MeetingRoom mr = meetingRoomDao.findById(roomid);
        String startTime = request.getParameter("starttime");
        String endTime = request.getParameter("endtime");
        List<Meeting> meetings = meetingDao.getMeetingByRoomAndTime(roomid, startTime, endTime);
        if (meetings != null && meetings.size() > 0) {
            mr.setIsFree("1");
        } else {
            mr.setIsFree("0");
        }
        writeObjToPage(mr);
    }
    
    /**
     * 获取所有的会议室
     */
    public void meetingRoom(){
        List<MeetingRoom> mrs =meetingRoomDao.getMeetingRoom();
        writeListToPage(mrs);
    }
}
