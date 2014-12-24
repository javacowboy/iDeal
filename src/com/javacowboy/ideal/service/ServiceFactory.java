package com.javacowboy.ideal.service;

public class ServiceFactory {
	
	static ResultsService resultsService;
	
	public static ResultsService getResultsService() {
		if(resultsService == null) {
			resultsService = new ResultsService();
		}
		return resultsService;
	}

}
