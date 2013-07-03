package cn.com.jnpc.meeting.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.com.jnpc.meeting.bean.Employee;
import cn.com.jnpc.meeting.dao.JNPC;
import cn.com.jnpc.meeting.dao.MeetingCommonDao;
import cn.com.jnpc.meeting.dao.MeetingDao;

import com.google.gson.Gson;

public class AJAXServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    MeetingCommonDao          meetingCommonDao;
    JNPC                      jnpc;
    MeetingDao meetingDao;
    public AJAXServlet() {
        super();
    }
    
    @Override
    public void init()
        throws ServletException {
        meetingCommonDao = new MeetingCommonDao();
        jnpc = new JNPC();
        meetingDao = new MeetingDao();
        super.init();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        doPost(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("application/x-javascript;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        
        String ctrl = request.getParameter("ctrl");
        
        if ("ens".equalsIgnoreCase(ctrl)) {// 获取员工姓名,部门 主持人的自动完成
            String presider = request.getParameter("presider");
            presider = URLDecoder.decode(presider,"utf-8");
            List<Employee> list = jnpc.getEmployeeNamesAndOrgName(presider);
            jsonToPage(response, list);
        } else if ("pu".equals(ctrl)) {// 参会内部单位、人员
            String unit = request.getParameter("unit");
            unit = URLDecoder.decode(unit,"utf-8");
            String[] str = unit.split(",");
            unit = str[str.length - 1];
            List<Object> empls = jnpc.getEmployeeNames(unit);
            // List<Object> list1 = jnpc.getCommons(unit);
            List<Object> list2 = jnpc.getORGName(unit);
            List<Object> list = new ArrayList<Object>();
            if (!listIsEmpty(empls)) {
                list.addAll(empls);
            }
            // if (!listIsEmpty(list1)) {
            // list.addAll(list1);
            // }
            if (!listIsEmpty(list2)) {
                list.addAll(list2);
            }
            jsonToPage(response, list);
        } else if ("wu".equals(ctrl)) {// 外部单位
            String unit = request.getParameter("unit");
            unit = URLDecoder.decode(unit,"utf-8");
            if (unit.endsWith(",")) {
                String[] str = unit.split(",");
                unit = str[str.length - 1];
                meetingCommonDao.addWldw(unit);
            } else {
                String[] str = unit.split(",");
                unit = str[str.length - 1];
                List<Object> list = meetingCommonDao.getCommons(unit);
                jsonToPage(response, list);
            }
        }else if("checkP".equals(ctrl)){//检查主持人是否有会议
            String presider = request.getParameter("presider");
            presider = URLDecoder.decode(presider,"utf-8");
            String startTime = request.getParameter("starttime");
            String endTime = request.getParameter("endtime");
            String meetingId = request.getParameter("id");
            boolean flag = meetingDao.isPresiderAvailable(startTime, endTime, presider, meetingId);
            if(flag){
                PrintWriter out = response.getWriter();
                out.println(0);
                out.close();
            }else {
                PrintWriter out = response.getWriter();
                out.println(1);
                out.close();
            }
        }else if("checkL".equals(ctrl)){//检查领导是否有会议
            String leader = request.getParameter("leader");
            leader = URLDecoder.decode(leader,"utf-8");
            String startTime = request.getParameter("starttime");
            String endTime = request.getParameter("endtime");
            String meetingId = request.getParameter("id");
            boolean flag = meetingDao.isLeaderAvailable(startTime, endTime, leader, meetingId);
            if(flag){
                PrintWriter out = response.getWriter();
                out.println(0);
                out.close();
            }else {
                PrintWriter out = response.getWriter();
                out.println(1);
                out.close();
            }
        }
    }
    
    private void jsonToPage(HttpServletResponse response, List<?> list)
        throws IOException {
        PrintWriter out = response.getWriter();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        out.println(json);
        out.close();
    }
    
    private boolean listIsEmpty(List<?> list) {
        if (list != null && list.size() > 0) {
            return false;
        }
        return true;
    }
    
}
