package cn.com.jnpc.email;

import org.apache.commons.mail.HtmlEmail;

import cn.com.jnpc.meeting.bean.Meeting;
import cn.com.jnpc.utils.DateUtil;

public class EmailSender {

    public void send(String emailTo,Meeting meeting){
        HtmlEmail email = new HtmlEmail ();
        email.setHostName (EmailConfigReader.getHost ());
        email.setAuthentication (EmailConfigReader.getUsername (), EmailConfigReader.getPassword ());
        email.setDebug (EmailConfigReader.getDebug ());
        try {
            email.setCharset ("utf-8");
            email.setFrom (EmailConfigReader.getFromAddress ());
            email.addTo (emailTo);
            if (EmailConfigReader.getDebug ()) {
                email.addTo ("linuke@126.com");
            } else {
                email.addTo (emailTo);
            }
            email.setSubject ("会议退回");
            StringBuilder sb = new StringBuilder ();
            sb.append ("<div align='center'>");
            sb.append ("<table > <tr align='center'><tr><th colspan='2'>");
            sb.append (meeting.getContent ());
            sb.append ("</th></tr>");
            sb.append ("<tr align='center'><td align='right'>会议地点：</td><td align='left'>");
            sb.append (meeting.getRoomid ());
            sb.append ("</td></tr>");
            sb.append ("<tr align='center'><td align='right'>会议时间：</td><td align='left'>");
            sb.append (meeting.getStarttime () + "至" + meeting.getEndtime ());
            sb.append ("</td></tr>");
            sb.append ("<tr align='center'><td align='right'>会议内容：</td><td align='left'>");
            sb.append (meeting.getContent ());
            sb.append ("</td></tr>");
            sb.append ("</td></tr>");
            sb.append ("<tr align='center'><td align='right'>参会领导：</td><td align='left'>");
            sb.append (meeting.getLeader ());
            sb.append ("</td></tr>");
            sb.append ("<tr align='center'><td align='right'>参会人员：</td><td align='left'>");
            sb.append (meeting.getDepart ());
            sb.append ("</td></tr>");
            sb.append ("</table>");
            sb.append ("<p>您申请的会议被退回！如有疑问请联系");
            sb.append (EmailConfigReader.getContact ());
            sb.append ("&nbsp;电话:");
            sb.append (EmailConfigReader.getPhone ());
            sb.append ("&nbsp;电子邮件:");
            sb.append("<a href='mailto:" + EmailConfigReader.getEmail() + "'>" + EmailConfigReader.getEmail() + "</a>");
            sb.append (".</p></div>");
            email.setHtmlMsg (sb.toString ());
            email.send ();
        } catch (Exception e) {
            e.printStackTrace ();
        }

    }

    public void send(Meeting meeting){
        HtmlEmail email = new HtmlEmail ();
        email.setHostName (EmailConfigReader.getHost ());
        email.setDebug (EmailConfigReader.getDebug ());
        email.setAuthentication (EmailConfigReader.getUsername (), EmailConfigReader.getPassword ());
        try {
            email.setCharset ("utf-8");
            email.setFrom (EmailConfigReader.getFromAddress ());
            email.addTo(EmailConfigReader.getEmail());
            email.setSubject (meeting.getContent ());
            StringBuilder sb = new StringBuilder ();
            sb.append ("<div align='center'>");
            sb.append ("<table > <tr align='center'><tr><th colspan='2'>");
            sb.append (meeting.getContent ());
            sb.append ("</th></tr>");
            sb.append ("<tr align='center'><td align='right'>会议地点：</td><td align='left'>");
            sb.append(meeting.getAddress() + meeting.getAddress1());
            sb.append ("</td></tr>");
            sb.append ("<tr align='center'><td align='right'>会议时间：</td><td align='left'>");
            sb.append(DateUtil.dateToString(meeting.getStarttime(), "yyyy-MM-dd HH:mm") + "至"
                    + DateUtil.dateToString(meeting.getEndtime(), "yyyy-MM-dd HH:mm"));
            sb.append ("</td></tr>");
            sb.append ("<tr align='center'><td align='right'>会议内容：</td><td align='left'>");
            sb.append (meeting.getContent ());
            sb.append ("</td></tr>");
            sb.append ("</td></tr>");
            sb.append ("<tr align='center'><td align='right'>参会领导：</td><td align='left'>");
            sb.append (meeting.getLeader ());
            sb.append ("</td></tr>");
            sb.append ("<tr align='center'><td align='right'>参会人员：</td><td align='left'>");
            sb.append (meeting.getDepart ());
            sb.append ("</td></tr>");
            sb.append ("</table></div>");
            email.setHtmlMsg (sb.toString ());
            email.send ();
        } catch (Exception e) {
            e.printStackTrace ();
        }

    }

}
