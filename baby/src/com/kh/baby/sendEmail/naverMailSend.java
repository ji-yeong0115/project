package com.kh.baby.sendEmail;

import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class naverMailSend {
	public static int naverMailSend(String email) {
		String host = "smtp.naver.com";
		String user = "dldkfk31@naver.com";
		String password = "63728054lar19";
		int code = 0;
		
		Properties props = new Properties();
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", 587);
		props.put("mail.smtp.auth", "true");
		
		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(user, password);
			}
		});
		
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(user));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
			
			code = generateAuthNo1();
			message.setSubject("슬기로운 육아생활 인증번호");
			
			message.setText("인증번호 : " + code);
			
			Transport.send(message);
			System.out.println("메세지 전송 성공");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return code;
	}
	
	public static int generateAuthNo1() {
        return ThreadLocalRandom.current().nextInt(100000, 1000000);
    }
}
