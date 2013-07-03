package cn.com.jnpc.meeting.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import cn.com.jnpc.meeting.bean.MeetingPlan;
import cn.com.jnpc.meeting.dao.JNPC;
import cn.com.jnpc.meeting.dao.MeetingExplainDao;
import cn.com.jnpc.meeting.dao.MeetingPlanDao;
import cn.com.jnpc.meeting.dao.MeetingRoomDao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MeetingPlanServlet extends BaseServlet {

    private static final long serialVersionUID  = 1L;

    private MeetingPlanDao    meetingPlanDao    = new MeetingPlanDao ();
    // private MeetingRoom2Dao meetingRoom2Dao = new MeetingRoom2Dao ();
    private MeetingRoomDao    meetingRoomDao    = new MeetingRoomDao();
    private MeetingExplainDao meetingExplainDao = new MeetingExplainDao ();

    private JNPC              jnpc              = new JNPC ();
    private int               flag              = 0;

    /**
     * 计划会议添加到会议
     * 
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String toMeeting() throws ServletException,IOException{
        String id = request.getParameter ("id");
        flag = meetingPlanDao.toMeeting (id);
        if (flag != -1) {
            return listDepart ();
        } else {
            return toErrorPage (error);
        }
    }

    public String update() throws ServletException,IOException{
        String json = request.getParameter ("json");
        Gson gson = new GsonBuilder ().setDateFormat ("yyyy-MM-dd HH:mm").create ();

        MeetingPlan mp = gson.fromJson (json, MeetingPlan.class);
        flag = meetingPlanDao.update (mp);
        return judgeResult ();
    }

    public String add() throws ServletException,IOException{
        String json = request.getParameter ("json");
        Gson gson = new GsonBuilder ().setDateFormat ("yyyy-MM-dd HH:mm").create ();
        MeetingPlan m = gson.fromJson (json, MeetingPlan.class);
        flag = meetingPlanDao.meetingPlanAdd(m, userid);
        return judgeResult ();
    }

    public String toUpdate(){
        String id = request.getParameter ("id");
        request.setAttribute ("ctrl", "update");
        request.setAttribute ("meeting", meetingPlanDao.getMeetingPlanByID (id));
        request.setAttribute ("title", "计划会议修改");
        request.setAttribute ("mes", meetingExplainDao.getAllShow ());

        getParameter ("show");
        // 得到公司领导
        List<Object> leaderList = jnpc.getLeaders ();
        request.setAttribute("mrs", meetingRoomDao.getMeetingRoom());
        request.setAttribute ("leaderList", leaderList); // 保存结果
        return BASE_JSP + "meetingPlan/meetingPlanApply.jsp";
    }

    public String del() throws ServletException,IOException{
        String[] ids = request.getParameterValues ("id");
        flag = meetingPlanDao.delete (ids);
        if (flag != -1) {
            return listDepart ();
        } else {
            return toErrorPage (error);
        }
    }

    public String toAdd() throws ServletException,IOException{
        if (vec.contains ("380101")) {
            // 所有的领导
            request.setAttribute("leaderList", jnpc.getLeaders());
            // // 所有的会议室建筑
            // request.setAttribute("mrs", meetingRoom2Dao.getParentRoom());
            // // 所有的培训教室
            // request.setAttribute("trs", meetingRoom2Dao.getTrainingRoom());
            request.setAttribute("mrs", meetingRoomDao.getMeetingRoom());
            // 所有部门
            request.setAttribute("orgs", jnpc.getAllORG());
            request.setAttribute ("title", "计划会议申请");
            request.setAttribute ("ctrl", "add");
            // 所有显示的有关说明
            request.setAttribute("mes", meetingExplainDao.getAllShow());
            return BASE_JSP + "meetingPlan/meetingPlanApply.jsp";
        } else {
            error = "对不起，您没有部门会议申请的权限！";
            return toErrorPage (error);
        }
    }

    /**
     * 判断结果并返回到相应的页面
     * 
     * @return
     * @throws ServletException
     * @throws IOException
     */
    private String judgeResult() throws ServletException,IOException{

        if (flag != -1 && flag != -999 && flag != -99 && flag != -98) { // 添加成功
            return listDepart ();
        } else if (flag == -99) {
            error = "主持人在此时间段正参与其他会议，请检查！";
        } else if (flag == -98) {
            error = "领导在此时间段正参与其他会议，请检查！";
        } else if (flag == -999) {
            error = "已经存在一条相同的记录！";
        }
        return toErrorPage (error);
    }

    public String listDepart() throws ServletException,IOException{
        List<MeetingPlan> mps = meetingPlanDao.getMeetingPlanByUserid (userid); // 得到登录者所在部门得申请会议信息
        request.setAttribute ("mps", mps); // 保存结果
        request.setAttribute("title", "计划会议申请浏览");
        // 返回到游览页面
        return BASE_JSP + "meetingPlan/meetingPlanList.jsp";
    }

}
