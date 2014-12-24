package com.javacowboy.ideal.service;

import com.javacowboy.ideal.Constants;
import com.javacowboy.ideal.model.dao.UserDao;
import com.javacowboy.ideal.model.dto.ksl.KslParameterDto;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class UserService extends Service {

    UserDao userDao = new UserDao();

    public List<KslParameterDto> getKslParameters(){
    	List<KslParameterDto> list = new ArrayList<KslParameterDto>();
    	File paramDir = new File(Constants.userKslDir);
    	logger.info("Looking for KSL search parameters in: " + paramDir.getPath());
    	if(paramDir.isDirectory()) {
    		for(File file : paramDir.listFiles()) {
    			if(file.getName().toLowerCase().endsWith(".xml") && !file.getName().equals(Constants.kslExampleFile)) {
    				logger.info("Adding parameters from: " + file.getName());
    				list.addAll(userDao.getKslParameters(file));
    			}
    		}
    	}
        return list; 
    }
}
