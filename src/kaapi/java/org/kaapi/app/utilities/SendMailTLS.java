package org.kaapi.app.utilities;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMailTLS {

	public void sendMaile(String address,String type,String msg){

		final String username = "khmer.academy999@gmail.com";
		final String password = "abc123+-*";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("khmer.academy999@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
	
			InternetAddress.parse(address));
			if(type.equals("reset")){
					message.setSubject("Khmer Academy - Reset Password");
			}
			else{
				message.setSubject("Khmer Academy - Email Verification");
			}
			
			message.setContent(msg, "text/html; charset=utf-8");
			
//			message.setText(msg);

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void main(String[] args) {
//		for(int i=0;i<1000;i++){
			new SendMailTLS().sendMaile("pirangphan@gmail.com", "Test", "Hello Hello");
//		}
	}
}