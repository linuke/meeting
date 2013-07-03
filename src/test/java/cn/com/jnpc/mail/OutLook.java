package cn.com.jnpc.mail;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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

import org.junit.Test;

public class OutLook {

    @Test
    public void sendMail(){
        try {
            MailInfo mailInfo = new MailInfo ();
            mailInfo.setMailServerPort ("25");
            mailInfo.setValidate (true);

            mailInfo.setMailServerHost ("email.jnpc.com.cn");
			mailInfo.setFromAddress("notification-service@jnpc.com.cn");
			mailInfo.setUserName("notification-service");
			mailInfo.setPassword("123456");

            mailInfo.setToAddress ("linuke@163.com");

            MyAuthenticator authenticator = null;
            Properties pro = mailInfo.getProperties ();
            if (mailInfo.isValidate ()) {
                // 如果需要身份认证，则创建一个密码验证器
                authenticator = new MyAuthenticator (mailInfo.getUserName (),mailInfo.getPassword ());
            }
            // 根据邮件会话属性和密码验证器构造一个发送邮件的session
            Session session = Session.getDefaultInstance (pro, authenticator);

            // register the text/calendar mime type
            MimetypesFileTypeMap mimetypes = (MimetypesFileTypeMap) MimetypesFileTypeMap.getDefaultFileTypeMap ();
            mimetypes.addMimeTypes ("text/calendar ics ICS");

            // register the handling of text/calendar mime type
            MailcapCommandMap mailcap = (MailcapCommandMap) MailcapCommandMap.getDefaultCommandMap ();
            mailcap.addMailcap ("text/calendar;; x-java-content-handler=com.sun.mail.handlers.text_plain");

            MimeMessage message = new MimeMessage (session);
            message.setFrom (new InternetAddress (mailInfo.getFromAddress ()));
            message.setSubject ("test");
            message.addRecipient (Message.RecipientType.TO, new InternetAddress (mailInfo.getToAddress ()));

            // Create an alternative Multipart
            Multipart multipart = new MimeMultipart ("alternative");

            // part 1, html text
            BodyPart messageBodyPart = buildHtmlTextPart ();
            multipart.addBodyPart (messageBodyPart);

            // Add part two, the calendar
            BodyPart calendarPart = buildCalendarPart ();
            multipart.addBodyPart (calendarPart);

            // Put the multipart in message
            message.setContent (multipart);
            // send the message
            Transport transport = session.getTransport ("smtp");
            transport.connect ();
            transport.sendMessage (message, message.getAllRecipients ());
            transport.close ();

        } catch (Exception e) {
            e.printStackTrace ();
        }

    }

    private BodyPart buildHtmlTextPart() throws MessagingException{

        MimeBodyPart descriptionPart = new MimeBodyPart ();

        // Note: even if the content is spcified as being text/html, outlook won't read correctly tables at all
        // and only some properties from div:s. Thus, try to avoid too fancy content
        String content = "<font size=\"2\">内容 </font>";
        descriptionPart.setContent (content, "text/html; charset=utf-8");
        return descriptionPart;
    }

    // define somewhere the icalendar date format
    private static SimpleDateFormat iCalendarDateFormat = new SimpleDateFormat ("yyyyMMdd'T'HHmm'00'");

    private BodyPart buildCalendarPart() throws Exception{

        BodyPart calendarPart = new MimeBodyPart ();

        Calendar cal = Calendar.getInstance ();
        cal.add (Calendar.DAY_OF_MONTH, 1);
        Date start = cal.getTime ();
        cal.add (Calendar.HOUR_OF_DAY, 3);
        Date end = cal.getTime ();

        // check the icalendar spec in order to build a more complicated meeting request
        /*
         * String calendarContent = "BEGIN:VCALENDAR\n" + "METHOD:REQUEST\n" + "PRODID: BCP - Meeting\n" + "VERSION:2.0\n" + "BEGIN:VEVENT\n" + "DTSTAMP:" + iCalendarDateFormat.format(start) + "\n" +
         * "DTSTART:" + iCalendarDateFormat.format(start)+ "\n" + "DTEND:" + iCalendarDateFormat.format(end)+ "\n" + "SUMMARY:test request\n" + "UID:324\n" +
         * "ATTENDEE;ROLE=REQ-PARTICIPANT;PARTSTAT=NEEDS-ACTION;RSVP=TRUE:MAILTO:organizer@yahoo.com\n" + "ORGANIZER:MAILTO:organizer@yahoo.com\n" + "LOCATION:"+"地点"+"\n" +
         * "DESCRIPTION:learn some stuff\n" + "SEQUENCE:0\n" + "PRIORITY:5\n" + "CLASS:PUBLIC\n" + "STATUS:CONFIRMED\n" + "TRANSP:OPAQUE\n" + "BEGIN:VALARM\n" + "ACTION:DISPLAY\n" +
         * "DESCRIPTION:REMINDER\n" + "TRIGGER;RELATED=START:-PT00H15M00S\n" + "END:VALARM\n" + "END:VEVENT\n" + "END:VCALENDAR";
         */
        // String calendarContent = "BEGIN:VCALENDAR\n" + "METHOD:REQUEST\n" +
        // "PRODID:-//Microsoft Corporation//Outlook 15.0 MIMEDIR//EN\n"
        // + "VERSION:2.0\n" + "METHOD:PUBLISH\n" +
        // "X-MS-OLK-FORCEINSPECTOROPEN:TRUE\n" + "BEGIN:VTIMEZONE\n" +
        // "TZID:China Standard Time\n"
        // + "BEGIN:STANDARD\n" + "DTSTART:16010101T000000\n" +
        // "TZOFFSETFROM:+0800\n" + "TZOFFSETTO:+0800\n" + "END:STANDARD\n"
        // + "END:VTIMEZONE\n" + "BEGIN:VEVENT\n" + "CLASS:PUBLIC\n" +
        // "CREATED:20130510T010445Z\n"
        // + "DTEND;TZID=\"China Standard Time\":20130510T133500\n" +
        // "DTSTAMP:20130510T005350Z"
        // + "DTSTART;TZID=\"China Standard Time\":20130510T134000\n" +
        // "LAST-MODIFIED:20130510T010445Z\n" + "LOCATION:地点\n" + "PRIORITY:5\n"
        // + "SEQUENCE:0\n" + "SUMMARY;LANGUAGE=zh-cn:主题\n" + "TRANSP:OPAQUE\n"
        // +
        // "UID:040000008200E00074C5B7101A82E0080000000040A4B9E35B4DCE01000000000000000"
        // + "	01000000054A3F2366AF5EA449AEBE6FEADA97D94\n"
        // + "BEGIN:VALARM\n" + "TRIGGER:-PT15M\n" + "ACTION:DISPLAY\n" +
        // "DESCRIPTION:Reminder\n" + "END:VALARM\n" + "END:VEVENT\n"
        // + "END:VCALENDAR\n";
        String calendarContent = "BEGIN:VCALENDAR\n"+
"PRODID:-//Microsoft Corporation//Outlook 15.0 MIMEDIR//EN\n"+
"VERSION:2.0\n"+
"METHOD:REQUEST\n"+
"X-MS-OLK-FORCEINSPECTOROPEN:TRUE\n"+
"BEGIN:VTIMEZONE\n"+
"TZID:China Standard Time\n"+
"BEGIN:STANDARD\n"+
"DTSTART:16010101T000000\n"+
"TZOFFSETFROM:+0800\n"+
"TZOFFSETTO:+0800\n"+
"END:STANDARD\n"+
"END:VTIMEZONE\n"+
"BEGIN:VEVENT\n"+
 "ATTENDEE;CN=linuke;RSVP=TRUE:mailto:linuke@126.com\n"
                +
"CLASS:PUBLIC\n"+
 "CREATED:20130608T072301Z\n"
                +
"DESCRIPTION:测试内容\n\n"+
 "DTEND;TZID=\"China Standard Time\":20130608T090000\n"
                +
 "DTSTAMP:20130608T072302Z\n"
                + "DTSTART;TZID=\"China Standard Time\":20130608T080000\n"
                + "LAST-MODIFIED:20130605T072301Z\n"
                +
"LOCATION:地点\n"+
 "ORGANIZER;CN=\"刘科\":mailto:linuke@126.com\n"
                +
"PRIORITY:5\n"+
"SEQUENCE:0\n"+
"SUMMARY;LANGUAGE=zh-cn:主题\n"+
"TRANSP:OPAQUE\n"+
"UID:040000008200E00074C5B7101A82E00800000000D04748450062CE0100000000000000001000000052AA3979DFDAFF418043734141DA2B9D\n"+
 "X-ALT-DESC;FMTTYPE=text/html:<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 3.2//EN\">\n<HTML>\n<HEAD>\n<META NAME=\"Generator\" CONTENT=\"MS Exchange Server version rmj.rmm.rup.rpr\">\n<TITLE></TITLE>\n</HEAD>\n<BODY>\n<!-- Converted from text/rtf format -->\n\n<P DIR=LTR ALIGN=JUSTIFY><SPAN LANG=\"en-us\"><FONT FACE=\"宋体\">测试</FONT><FONT FACE=\"宋体\">内容</FONT></SPAN><SPAN LANG=\"en-us\"></SPAN></P>\n\n</BODY>\n</HTML>\n"
                +
"X-MICROSOFT-CDO-BUSYSTATUS:BUSY\n"+
"X-MICROSOFT-CDO-IMPORTANCE:1\n"+
"X-MICROSOFT-DISALLOW-COUNTER:FALSE\n"+
"X-MS-OLK-AUTOFILLLOCATION:FALSE\n"+
"X-MS-OLK-CONFTYPE:0\n"+
"BEGIN:VALARM\n"+
"TRIGGER:-PT30M\n"+
"ACTION:DISPLAY\n"+
"DESCRIPTION:Reminder\n"+
"END:VALARM\n"+
"END:VEVENT\n"+
"END:VCALENDAR\n";

        // calendarPart.addHeader ("Content-Class",
        // "urn:content-classes:calendarmessage");
        calendarPart.setContent (calendarContent, "text/calendar;method=CANCEL; charset=utf-8");

        return calendarPart;
    }

}
