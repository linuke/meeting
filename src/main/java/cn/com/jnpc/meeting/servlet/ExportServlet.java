package cn.com.jnpc.meeting.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.com.jnpc.excel.ExcelWriter;
import cn.com.jnpc.meeting.bean.Meeting;
import cn.com.jnpc.meeting.bean.MeetingPlan;
import cn.com.jnpc.meeting.dao.JNPC;
import cn.com.jnpc.meeting.dao.MeetingDao;
import cn.com.jnpc.meeting.dao.MeetingMoneyDao;
import cn.com.jnpc.meeting.dao.MeetingPlanDao;
import cn.com.jnpc.utils.DateUtil;
import cn.com.jnpc.utils.PropertyFilter;

public class ExportServlet extends BaseServlet {

    private static final long serialVersionUID = 1L;
    private MeetingDao        meetingDao       = new MeetingDao();
    private MeetingPlanDao    meetingPlanDao   = new MeetingPlanDao();
    private MeetingMoneyDao   meetingMoneyDao  = new MeetingMoneyDao();
    private JNPC              jnpc             = new JNPC();

    public ExportServlet() {
    }

    public void exportMeeting() throws IOException {
        String year = request.getParameter("year");
        String month = request.getParameter("month");
        String org = request.getParameter("org");
        if (!isEmpty(year) && !isEmpty(month)) {
            int iyear = Integer.parseInt(year);
            int imonth = Integer.parseInt(month);
            PropertyFilter opf = new PropertyFilter("m.commitdepart:EQ_S", org);
            Date startDate = DateUtil.getFirstDate(iyear, imonth);
            Date endDate = DateUtil.getLastDate(iyear, imonth);
            PropertyFilter s1 = new PropertyFilter("m.starttime:GE_D", DateUtil.dateToString(startDate, "yyyy-MM-dd"));
            PropertyFilter s2 = new PropertyFilter("m.starttime:LE_D", DateUtil.dateToString(endDate, "yyyy-MM-dd"));
            List<PropertyFilter> pfList = new ArrayList<PropertyFilter>();
            pfList.add(s1);
            pfList.add(s2);
            pfList.add(opf);
            List<Meeting> meetingList = meetingDao.getMeeting(pfList);
            ExcelWriter ew = new ExcelWriter();
            String fileName = "会议执行情况汇总表（" + month + "月）";
            OutputStream os = response.getOutputStream();
            response.setContentType("application/octet-stream");
            fileName = new String(fileName.getBytes("gb2312"), "ISO8859_1");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + ".xls" + "\"");
            ew.writeReality(meetingList, os, year, month);
        }
    }

    public void exportPlan() throws IOException {
        String year = request.getParameter("year");
        String month = request.getParameter("month");
        String org = request.getParameter("org");
        if (!isEmpty(year) && !isEmpty(month)) {
            int iyear = Integer.parseInt(year);
            int imonth = Integer.parseInt(month);
            PropertyFilter opf = new PropertyFilter("m.commitdepart:EQ_S", org);
            Date startDate = DateUtil.getFirstDate(iyear, imonth);
            Date endDate = DateUtil.getLastDate(iyear, imonth);
            PropertyFilter st1 = new PropertyFilter("m.starttime:GE_D", DateUtil.dateToString(startDate, "yyyy-MM-dd"));
            PropertyFilter st2 = new PropertyFilter("m.starttime:LE_D", DateUtil.dateToString(endDate, "yyyy-MM-dd"));
            List<PropertyFilter> pfList = new ArrayList<PropertyFilter>();
            pfList.add(st1);
            pfList.add(st2);
            pfList.add(opf);
            List<MeetingPlan> meetingPlanList = meetingPlanDao.getMeetingPlan(pfList);
            // 年初到上月底已完成预算
            Map<String, String> costsMap = meetingDao.getCosts(year + "-01-01",
                    DateUtil.dateToString(DateUtil.getLastDate(iyear, imonth - 1), "yyyy-MM-dd"));
            // 获取年度预算金额
            Map<String, String> planMap = meetingMoneyDao.getMeetingMoneyByOrgAndYear(year);
            ExcelWriter ew = new ExcelWriter();
            String fileName = "会议计划汇总表（" + month + "月）";
            OutputStream os = response.getOutputStream();
            response.setContentType("application/octet-stream");
            fileName = new String(fileName.getBytes("gb2312"), "ISO8859_1");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + ".xls" + "\"");
            ew.writerPlan(meetingPlanList, os, costsMap, planMap, year, month);
        }
    }

    public String toExport() {
        request.setAttribute("orgs", jnpc.getAllORG());
        request.setAttribute("title", "数据导出");
        return BASE_JSP + "export/export.jsp";
    }
}
