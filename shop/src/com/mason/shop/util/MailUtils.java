package com.mason.shop.util;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

/**
 * 发送邮件的工具类
 * @author Mason
 *
 */
public class MailUtils {
	/**
	 * 
	 * @param whereTo :收件人
	 * @param code :激活码
	 */
	public static void sendMail(String whereTo,String code){
		/**
		 * 1. 获取一个Session对象(一个连接,连接邮箱服务器)
		 * 2. 创建一个代表邮件的对象Message
		 * 3. 发送邮件Transport
		 */
		
		//1. 获得连接对象
		Properties props = new Properties();
		props.setProperty("mail.smtp.auth", "true");  
	    props.setProperty("mail.host","smtp.nankai.edu.cn");
		Session session = Session.getInstance(props, new Authenticator() {

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("1914991129@mail.nankai.edu.cn", "starscreem11");
			}
			
		});
		
		//2. 创建邮件对象
		//MIME:多用途互联网邮件扩展类型。是设定某种扩展名的文件用一种应用程序来打开的方式类型
		Message message = new MimeMessage(session);
				
		//设置发件人
		try {
			message.setFrom(new InternetAddress("1914991129@mail.nankai.edu.cn"));
			//设置收件人
			message.addRecipient(RecipientType.TO, new InternetAddress(whereTo));
			//抄送CC ， 密送 BCC
			//设置标题
			message.setSubject("来自NKU商城的官方激活邮件");
			//设置内容
			message.setContent("<h1>NKU商城官方激活邮件!点下面的链接完成激活~</h1><h3><a href='http://127.0.0.1:8080/shop/user_activate.action?code="+code+"'>http://127.0.0.1:8080/shop/user_activate.action?code="+code+"</a><h3>", "text/html;charset=UTF-8");
			
			//3. 发送邮件
			Transport.send(message);
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
			
	}
	
//	public static void main(String[] args) {
//		sendMail("1914991129@qq.com", "1111111111111111");
//		System.out.println("邮件已发送!");
//	}
}
