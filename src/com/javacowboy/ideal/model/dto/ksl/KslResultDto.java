package com.javacowboy.ideal.model.dto.ksl;

import java.lang.reflect.Field;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class KslResultDto {
	
	public static final String ROOT_URL = "http://www.ksl.com/index.php";
	
	protected Float price;
	protected Float originalPrice; //set when db comparison happens
	protected String title;
	protected String location;
	protected String time;
	protected String imageHref;
	protected String adHref;
	protected String description;
	protected Long adId;
	//params used to search for this result
	protected KslParameterDto parameterDto;

	//getters and setters
	public Long getAdId() {
		return adId;
	}

	public void setAdId(Long adId) {
		this.adId = adId;
	}

	public String getImageHref() {
		return imageHref;
	}

	public void setImageHref(String imageHref) {
		this.imageHref = imageHref;
	}

	public String getAdHref() {
		return adHref;
	}

	public void setAdHref(String adHref) {
		if(adHref.startsWith("?")) {
			adHref = ROOT_URL + adHref;
		}
		this.adHref = adHref;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Float getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(Float originalPrice) {
		this.originalPrice = originalPrice;
	}

	public KslParameterDto getParameterDto() {
		return parameterDto;
	}

	public void setParameterDto(KslParameterDto parameterDto) {
		this.parameterDto = parameterDto;
	}

	@Override
    public String toString() {
        return (new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE){
        	@Override
        	protected boolean accept(Field field) {
        		return super.accept(field) && !field.getName().equals("parameterDto");
        	}
        }).toString();
    }
}
