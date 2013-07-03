package cn.com.jnpc.mail;


import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.MultiPartEmail;

public class Snippet {
	
	public static void main(String[] args){
	         //这个类主要是设置邮件
		  MailInfo mailInfo = new MailInfo(); 
		  mailInfo.setMailServerHost("email.jnpc.com.cn"); 
		  mailInfo.setMailServerPort("25"); 
		  mailInfo.setValidate(true); 
		  mailInfo.setFromAddress("notification-service@jnpc.con.cn");
		  mailInfo.setUserName("notification-service"); 
	      mailInfo.setPassword("123456");
		  mailInfo.setToAddress("kuangjw@jnpc.con.cn");
		  mailInfo.setSubject("设置邮箱标题"); 
		  mailInfo.setContent("设置邮箱内容"); 
	         //这个类主要来发送邮件
		  SimpleMailSender sms = new SimpleMailSender();
	          sms.sendTextMail(mailInfo);//发送文体格式 
	          sms.sendHtmlMail(mailInfo);//发送html格式
		}
	
	public void mailTest(){
		try {
			Email email = new MultiPartEmail();
			
			email.setHostName("");
			email.send();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}

