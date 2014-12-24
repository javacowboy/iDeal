package com.javacowboy.ideal.mail;

import java.util.Properties;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.StringUtils;


import com.javacowboy.ideal.Constants;

public class SendMail {
	
	static Logger logger = Logger.getLogger(SendMail.class.getName());
	
	enum MailType{
		TEXT(""),
		HTML("text/html");
		
		String contentType;
		private MailType(String contentType) {
			this.contentType = contentType;
		}
		
		public String getContentType() {
			return contentType;
		}
	}
	
	public static void sendHtmlMessage(String subject, String html, String ... recipients) {
		send(subject, html, MailType.HTML, recipients);
	}
	
	public static void sendTextMessage(String subject, String text, String ... recipients) {
		send(subject, text, MailType.TEXT, recipients);
	}
	
	protected static void send(String subject, String content, MailType type, String ... recipients) {
		String commaSeparatedRecipients = StringUtils.join(recipients, ",");
		logger.info("Emailing through: " + Constants.mailHost + " To: " + commaSeparatedRecipients);
		
		Properties props = new Properties();
		props.put("mail.smtp.host", Constants.mailHost);
		props.put("mail.smtp.socketFactory.port", Constants.mailPort);
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", Constants.mailPort);

		Session session = Session.getDefaultInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(Constants.mailFromUsername, Constants.mailFromPassword);
					}
				});

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(Constants.mailFrom));
			message.setRecipients(Message.RecipientType.TO,	InternetAddress.parse(commaSeparatedRecipients));
			message.setSubject(subject);
			switch (type) {
			case HTML:
				message.setContent(content, type.getContentType());
				break;
			default:
				message.setText(content);
				break;
			}
			Transport.send(message);
			logger.info("Email sent.");

		} catch (MessagingException e) {
			logger.severe("Email failed: " + e.getMessage());
			e.printStackTrace();
		}
	
	}
}
