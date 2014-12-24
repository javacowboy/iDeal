package com.javacowboy.ideal;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Constants {
	//Application settings ----------------------------------------------------------------------------------------------
	public static String propertiesFile = "resources/config/application.properties";
	//documents
	public static String kslConfigDir = "resources/user/ksl/";
	public static String kslExampleFile = "example.xml";
	public static String docDir = "resources/docs/";
	public static String docCategoryFilename = "Category Options.txt";
	//email
	public static final String mailHost = "smtp.gmail.com";
	public static final String mailPort = "465";
	public static final String mailFrom = "eyedealhound@gmail.com";
	public static final String mailFromUsername = "eyedealhound";
	public static final String mailFromPassword = "1deal123";
	//database
	public static final String dbFile = "resources/store/data.db";
	
	//User settings -------------------------------------------------------------------------------------------------------
	//emailing results
	public static boolean sendEmail;
	public static String[] sendEmailAddresses;
	public static Integer emailItemMaxViews;
	//html results
	public static boolean htmlCreate;
	public static String htmlDir;
	public static String htmlFilename;
	public static Integer htmlItemMaxViews;
	//user search criteria
	public static String userKslDir;
	
	static {
		try {
			Properties properties = new Properties();
			properties.load(new FileInputStream(propertiesFile));
			sendEmail = toBoolean(properties, "send.email", false);
			sendEmailAddresses = toStringArray(properties, "send.email.address");
			emailItemMaxViews = toInteger(properties, "item.max.views.email", null);
			htmlCreate = toBoolean(properties, "html.create", false);
			htmlDir = properties.getProperty("html.dir", null);
			htmlFilename = properties.getProperty("html.filename", null);
			htmlItemMaxViews = toInteger(properties, "item.max.views.html", null);
			userKslDir = properties.getProperty("user.ksl.dir", null);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//property helper methods---------------------------------------------------------------------------
	protected static String[] toStringArray(Properties properties, String name) {
		String value = properties.getProperty(name, null);
		if(value != null) {
			return value.split(",");
		}
		return null;
	}

	protected static boolean toBoolean(Properties properties, String name, boolean defaultValue) {
		return Boolean.valueOf(properties.getProperty(name, String.valueOf(defaultValue)));
	}

	protected static Integer toInteger(Properties properties, String name, Integer defaultValue) {
		try {
			return Integer.valueOf(properties.getProperty(name));
		}catch(NumberFormatException e) {
			return defaultValue;
		}
	}
	
	//helper methods-------------------------------------------------------------------------------------
	public static boolean hasEmailItemRestriction() {
		return emailItemMaxViews != null;
	}
	
	public static boolean hasHtmlItemRestriction() {
		return htmlItemMaxViews != null;
	}
}
