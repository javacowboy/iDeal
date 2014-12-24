package com.javacowboy.ideal.mail;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMailTLS {
	public static void main(String[] args) {
		String host = "smtp.gmail.com";
		int port = 587;
		String username = "eyedealhound";
		String password = "1deal123";
 
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
 
		Session session = Session.getInstance(props);
 
		try {
 
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("eyedealhound@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse("javacowboy@gmail.com"));
			message.setSubject("Testing TLS");
			message.setText("Dear Mail Crawler," +
					"\n\n No spam to my email, please!");
 
			Transport transport = session.getTransport("smtp");
			transport.connect(host, port, username, password);
 
			Transport.send(message);
 
			System.out.println("Done");
 
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}
