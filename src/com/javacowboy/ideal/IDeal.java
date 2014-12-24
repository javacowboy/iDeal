package com.javacowboy.ideal;

import java.util.ArrayList;
import java.util.List;

import com.javacowboy.ideal.model.dto.ksl.KslResultDto;
import com.javacowboy.ideal.service.FileService;
import com.javacowboy.ideal.service.KslService;
import com.javacowboy.ideal.service.MailService;
import com.javacowboy.ideal.service.ResultsService;
import com.javacowboy.ideal.service.ServiceFactory;
import com.javacowboy.ideal.service.UserService;

public class IDeal {

	FileService fileService = new FileService();
    KslService kslService = new KslService();
    MailService mailService = new MailService();
    ResultsService resultsService = ServiceFactory.getResultsService();
    UserService userService = new UserService();

    public static void main(String[] args) {
        IDeal ideal = new IDeal();
        ideal.run();
    }

    public void run(){
        List<KslResultDto> emailResults = kslService.getResults(userService.getKslParameters());
        List<KslResultDto> htmlResults;
        if(Constants.hasEmailItemRestriction() || Constants.hasHtmlItemRestriction()) {
        	resultsService.save(emailResults);
        	//a copy in case one list is configured to remove items and the other is not
        	htmlResults = new ArrayList<KslResultDto>(emailResults);
        }else {
        	//no items will be removed
        	htmlResults = emailResults;
        }
        
        if(Constants.sendEmail) {
        	mailService.sendResultsEmail(emailResults, Constants.sendEmailAddresses);
        }
        if(Constants.htmlCreate) {
        	if(Constants.hasHtmlItemRestriction()) {
        		resultsService.removeViewedItems(htmlResults, Constants.htmlItemMaxViews);
        	}
        	fileService.save(Constants.htmlDir, Constants.htmlFilename, mailService.buildResultsHtml(htmlResults));
        }
    }
}
