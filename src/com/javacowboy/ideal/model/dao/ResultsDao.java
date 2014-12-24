package com.javacowboy.ideal.model.dao;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.javacowboy.ideal.Constants;
import com.javacowboy.ideal.model.dto.DbInfoDto;
import com.javacowboy.ideal.model.dto.ksl.KslResultDto;
import com.javacowboy.ideal.service.FileService;

public class ResultsDao extends Dao {
	
	FileService fileService = new FileService();
	
	Map<Long, DbInfoDto> cachedDb;
	
	/**
	 * Updates the map of adId and view counts
	 * @param list
	 */
	public void save(List<KslResultDto> list) {
		//an item may have turned up as a result from more than one query, make sure we don't hide these from the user
		Map<Long, Long> currentSearchMap = new HashMap<Long, Long>();
		Map<Long, DbInfoDto> map = get(); //previous search results
		if(list == null || list.isEmpty()) {
			return;
		}
		if(map == null) {
			map = new HashMap<Long, DbInfoDto>();
		}
		for(KslResultDto result : list) {
			DbInfoDto dbInfo = map.get(result.getAdId());
			if(dbInfo == null) {
				dbInfo = new DbInfoDto();
			}
			if(currentSearchMap.get(result.getAdId()) == null) {//1st time found in this search
				dbInfo.incrementViewCount();
				dbInfo.setAdId(result.getAdId());
				dbInfo.setPrice(result.getPrice());
				map.put(result.getAdId(), dbInfo);
			}
			currentSearchMap.put(result.getAdId(), result.getAdId());
		}
		cachedDb = map;
		serialize(map);
	}
	
	/**
	 * Gets a map keyed by adId whose value is the number of times a result has shown up in searches
	 * @return
	 */
	public Map<Long, DbInfoDto> get() {
		if(cachedDb != null) {
			return cachedDb;
		}
		return deserialize();
	}
	
	private void serialize(Map<Long, DbInfoDto> map) {
		fileService.serialize(Constants.dbFile, map);
	}
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	private Map<Long, DbInfoDto> deserialize() {
		File db = new File(Constants.dbFile);
		//Flush the db every month to prevent it from getting huge
		if(db != null) {
			Date lastModified = new Date(db.lastModified());
			Date today = new Date();
			if(today.getMonth() != lastModified.getMonth()) {
				return null;
			}
		}
		return (Map<Long, DbInfoDto>)fileService.deserialize(db);
	}

}
