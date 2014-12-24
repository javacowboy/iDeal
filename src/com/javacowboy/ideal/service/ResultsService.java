package com.javacowboy.ideal.service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.javacowboy.ideal.model.dao.ResultsDao;
import com.javacowboy.ideal.model.dto.DbInfoDto;
import com.javacowboy.ideal.model.dto.ksl.KslResultDto;

public class ResultsService extends Service {
	
	ResultsDao resultsDao = new ResultsDao();
	
	public void save(List<KslResultDto> list) {
		resultsDao.save(list);
	}
	
	public Map<Long, DbInfoDto> get() {
		return resultsDao.get();
	}

	public void removeViewedItems(List<KslResultDto> results, int maxViews) {
		if(results != null) {
			Map<Long, DbInfoDto> db = get();
			Iterator<KslResultDto> it = results.iterator();
			while(it.hasNext()) {
				KslResultDto result = it.next();
				DbInfoDto dbInfo = db.get(result.getAdId());
				/*
				 * Remove if:
				 *  1. It has been found before and
				 *  2. It has been viewed more than the config setting and
				 *  3. The price has not changed
				 */
				if(dbInfo != null && dbInfo.getViewCount() > maxViews) {
					if(dbInfo.getPrice() != null && !dbInfo.getPrice().equals(result.getPrice())) {
						logger.info("Price change.  was: " + dbInfo.getPrice() + " now: " + result.getPrice());
						result.setOriginalPrice(dbInfo.getPrice());
						continue;
					}
					logger.info("Removing already viewed: " + result.toString());
					it.remove();
				}
			}
		}
	}

}
