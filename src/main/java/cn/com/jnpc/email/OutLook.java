package cn.com.jnpc.email;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.activation.MailcapCommandMap;
import javax.activation.MimetypesFileTypeMap;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import cn.com.jnpc.meeting.bean.Meeting;

public class OutLook {

    public void sendAppointment(Meeting meeting/* , String[] lemails */) {
		try {
			MailInfo mailInfo = new MailInfo();
			mailInfo.setMailServerPort(EmailConfigReader.getPort());
			mailInfo.setValidate(true);

			mailInfo.setMailServerHost(EmailConfigReader.getHost());
			mailInfo.setFromAddress(EmailConfigReader.getFromAddress());
			mailInfo.setUserName(EmailConfigReader.getUsername());
			mailInfo.setPassword(EmailConfigReader.getPassword());

			MyAuthenticator authenticator = null;
			Properties pro = mailInfo.getProperties();
			if (mailInfo.isValidate()) {
				// 如果需要身份认证，则创建一个密码验证器
				authenticator = new MyAuthenticator(mailInfo.getUserName(),
						mailInfo.getPassword());
			}
			// 根据邮件会话属性和密码验证器构造一个发送邮件的session
			Session session = Session.getDefaultInstance(pro, authenticator);

			// register the text/calendar mime type
			MimetypesFileTypeMap mimetypes = (MimetypesFileTypeMap) MimetypesFileTypeMap
					.getDefaultFileTypeMap();
			mimetypes.addMimeTypes("text/calendar ics ICS");

			// register the handling of text/calendar mime type
			MailcapCommandMap mailcap = (MailcapCommandMap) MailcapCommandMap
					.getDefaultCommandMap();
			mailcap.addMailcap("text/calendar;; x-java-content-handler=com.sun.mail.handlers.text_plain");

			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(mailInfo.getFromAddress()));
			message.setSubject("会议变更");
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(EmailConfigReader.getEmail()));
//			message.setSubject("会议通知");
//            if (EmailConfigReader.getDebug()) {
//                message.addRecipient(Message.RecipientType.TO, new InternetAddress("linuke@126.com"));
//            } else {
//                int len = lemails.length;
//                InternetAddress[] addresses = new InternetAddress[len];
//                for (int i = 0; i < len; i++) {
//                    addresses[i] = new InternetAddress(lemails[i]);
//                }
//                message.addRecipients(Message.RecipientType.TO, addresses);
//			}

			// Create an alternative Multipart
			Multipart multipart = new MimeMultipart("alternative");

			// part 1, html text
            BodyPart messageBodyPart = buildHtmlTextPart(meeting);
			multipart.addBodyPart(messageBodyPart);

			// Add part two, the calendar
			BodyPart calendarPart = buildCalendarPart(meeting);
			multipart.addBodyPart(calendarPart);

			// Put the multipart in message
			message.setContent(multipart);
			// send the message
			Transport transport = session.getTransport("smtp");
			transport.connect();
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

    private BodyPart buildHtmlTextPart(Meeting meeting)
			throws MessagingException {

		MimeBodyPart descriptionPart = new MimeBodyPart();

		// Note: even if the content is spcified as being text/html, outlook
		// won't read correctly tables at all
		// and only some properties from div:s. Thus, try to avoid too fancy
		// content
        StringBuilder sb = new StringBuilder();
        sb.append("<div align='center'>");
        sb.append("<table > <tr align='center'><tr><th colspan='2'>");
        sb.append(meeting.getContent());
        sb.append("</th></tr>");
        sb.append("<tr align='center'><td align='right'>会议地点：</td><td align='left'>");
        sb.append(meeting.getRoomid());
        sb.append("</td></tr>");
        sb.append("<tr align='center'><td align='right'>会议时间：</td><td align='left'>");
        sb.append(meeting.getStarttime() + "至" + meeting.getEndtime());
        sb.append("</td></tr>");
        sb.append("<tr align='center'><td align='right'>会议内容：</td><td align='left'>");
        sb.append(meeting.getContent());
        sb.append("</td></tr>");
        sb.append("</td></tr>");
        sb.append("<tr align='center'><td align='right'>参会领导：</td><td align='left'>");
        sb.append(meeting.getLeader());
        sb.append("</td></tr>");
        sb.append("<tr align='center'><td align='right'>参会人员：</td><td align='left'>");
        sb.append(meeting.getDepart());
        sb.append("</td></tr>");
        sb.append("</table></div>");
        descriptionPart.setContent(sb.toString(), "text/html; charset=utf-8");
		return descriptionPart;
	}

	// define somewhere the icalendar date format
	private static SimpleDateFormat iCalendarDateFormat = new SimpleDateFormat(
			"yyyyMMdd'T'HHmm'00'");
	private static SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyyMMdd'T'HHmmss'Z'");

	private BodyPart buildCalendarPart(Meeting meeting) throws Exception {

		BodyPart calendarPart = new MimeBodyPart();

		String start = iCalendarDateFormat.format(meeting.getStarttime());
		String now = dateFormat.format(new Date());
		// check the icalendar spec in order to build a more complicated meeting
		// request

		StringBuilder calendarContent = new StringBuilder();
		calendarContent.append("BEGIN:VCALENDAR\n");
		calendarContent.append("METHOD:REQUEST\n");
		calendarContent
				.append("PRODID:-//Microsoft Corporation//Outlook 15.0 MIMEDIR//EN\n");
		calendarContent.append("VERSION:2.0\n");
		calendarContent.append("METHOD:PUBLISH\n");
		calendarContent.append("X-MS-OLK-FORCEINSPECTOROPEN:TRUE\n");
		calendarContent.append("BEGIN:VTIMEZONE\n");
		calendarContent.append("TZID:China Standard Time\n");
		calendarContent.append("BEGIN:STANDARD\n");
		calendarContent.append("DTSTART:16010101T000000\n");
		calendarContent.append("TZOFFSETFROM:+0800\n");
		calendarContent.append("TZOFFSETTO:+0800\n");
		calendarContent.append("END:STANDARD\n");
		calendarContent.append("END:VTIMEZONE\n");
		calendarContent.append("BEGIN:VEVENT\n");
		calendarContent.append("CLASS:PUBLIC\n");
		calendarContent.append("CREATED:" + now + "\n");
		calendarContent.append("DTEND;TZID=\"China Standard Time\":"
				+ iCalendarDateFormat.format(meeting.getEndtime()) + "\n");
		calendarContent.append("DTSTAMP:" + now);
		calendarContent.append("DTSTART;TZID=\"China Standard Time\":" + start
				+ "\n");
		calendarContent.append("LAST-MODIFIED:" + now + "\n");
		calendarContent.append("LOCATION:" + meeting.getAddress() + "\n");
		calendarContent.append("PRIORITY:5\n");
		calendarContent.append("SEQUENCE:0\n");
        calendarContent.append("SUMMARY;LANGUAGE=zh-cn:会议变更\n");
		calendarContent.append("TRANSP:OPAQUE\n");
		calendarContent
				.append("UID:040000008200E00074C5B7101A82E0080000000040A4B9E35B4DCE0100000000000000001000000054A3F2366AF5EA449AEBE6FEADA97D94\n");
		calendarContent.append("BEGIN:VALARM\n");
		calendarContent.append("ACTION:DISPLAY\n");
		calendarContent.append("DESCRIPTION:Reminder\n");
		calendarContent.append("END:VALARM\n");
		calendarContent.append("END:VEVENT\n");
		calendarContent.append("END:VCALENDAR\n");
		calendarPart.addHeader("Content-Class",
				"urn:content-classes:calendarmessage");
		calendarPart.setContent(calendarContent.toString(),
				"text/calendar;method=CANCEL; charset=utf-8");
		return calendarPart;
	}

}
