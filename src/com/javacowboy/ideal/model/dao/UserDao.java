package com.javacowboy.ideal.model.dao;

import java.io.File;
import java.util.List;

import com.javacowboy.ideal.model.dto.ksl.KslParameterDto;
import com.javacowboy.ideal.service.FileService;
import com.thoughtworks.xstream.XStream;

public class UserDao extends Dao{
	
	FileService fileService = new FileService();
	XStream xStream = new XStream();
	
	public UserDao() {
		xStream.alias("ksl", KslParameterDto.class);
	}

    @SuppressWarnings("unchecked")
	public List<KslParameterDto> getKslParameters(File xmlFile){
    	return (List<KslParameterDto>)fromXml(xmlFile);
    }

	public void toXml(String dir, String filename, List<KslParameterDto> list) {
		String xml = xStream.toXML(list);
		fileService.save(new File(dir, filename), xml);
	}
	
	public Object fromXml(File xmlFile) {
		return xStream.fromXML(xmlFile);
	}
	
	
}
