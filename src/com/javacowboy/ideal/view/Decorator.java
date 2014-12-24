package com.javacowboy.ideal.view;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.javacowboy.ideal.model.dto.ksl.KslParameterDto;

public class Decorator {
	
	static final String shortDatePattern = "MMM dd";
	static final String timePattern = "h:mm a";
	static final String shortDateTimePattern = shortDatePattern + " " + timePattern;
	
	public static String decorate(String value) {
		if(value == null) {
			return "";
		}
		return value;
	}
	
	public static String shortDateTime(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat(shortDateTimePattern);
		return formatter.format(date);
	}
	
	public static String money(Float value) {
		if(value == null) {
			return decorate(null);
		}
		return "$" + value;
	}
	
	public static String money(Integer value) {
		if(value == null) {
			return decorate(null);
		}
		return "$" + value;
	}
	
	public static String summary(KslParameterDto dto) {
			String value = "";
			if(value.isEmpty() && dto.getSubCategory() != null) {
				value = dto.getSubCategory().toString();
			}
			if(value.isEmpty() && dto.getCategory() != null) {
				value = dto.getCategory().toString();
			}
			value += " " + dto.getSearchString();
			if(dto.getMinPrice() != null && dto.getMaxPrice() != null) {
				value += " Between " + money(dto.getMinPrice()) + " - " + money(dto.getMaxPrice());
			}else if(dto.getMinPrice() != null) {
				value += " Over " + money(dto.getMinPrice());
			}else if(dto.getMaxPrice() != null) {
				value += " Under " + money(dto.getMaxPrice());
			}
			return value;
	}

}
