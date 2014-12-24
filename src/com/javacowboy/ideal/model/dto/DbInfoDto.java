package com.javacowboy.ideal.model.dto;

import java.io.Serializable;

public class DbInfoDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	protected Long adId;
	protected int viewCount = 0;
	protected Float price;
	
	//methods
	public int incrementViewCount() {
		viewCount++;
		return getViewCount();
	}
	
	//getters and setters
	public int getViewCount() {
		return viewCount;
	}

	public Long getAdId() {
		return adId;
	}

	public void setAdId(Long adId) {
		this.adId = adId;
	}

	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}
	
	public Float getPrice() {
		return price;
	}
	
	public void setPrice(Float price) {
		this.price = price;
	}
}