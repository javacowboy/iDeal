package com.javacowboy.ideal.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.javacowboy.ideal.model.dto.ksl.KslParameterDto;
import com.javacowboy.ideal.model.dto.ksl.KslResultDto;

public class KslService extends Service {
	
	//a GET param that indicates how many results per page
	public static final String KSL_CLASSIFIED_URL = "http://www.ksl.com/classifieds/search/";
	public static final String PRIVATE_SELLER = "Private";
	public static final int RESULTS_PER_PAGE = 48;
	
    public List<KslResultDto> getResults(List<KslParameterDto> params){
    	List<KslResultDto> list = new ArrayList<KslResultDto>();
    	if(params != null) {
	        for(KslParameterDto dto : params){
	        	list.addAll(getResults(dto));
	        }
    	}
        return list;
    }
    
    /**
     * Get the search page html and parse it for results
     * @param param
     * @return
     */
    protected List<KslResultDto> getResults(KslParameterDto param) {
    	String url = getSearchUrl(param);
    	logger.info("Searching for: " + param.toString());
    	logger.info(url);
    	String resultPageHtml = getUrlHtml(url);
    	try {
    		return parseHtml(param, resultPageHtml);
    	}catch (Exception e) {
			return null;
		}
    }
    
    private List<KslResultDto> parseHtml(KslParameterDto param, String html) throws IOException {
    	List<KslResultDto> resultList = new ArrayList<KslResultDto>();
    	//charset iso-8859-1
		Document doc = Jsoup.parse(html);
    	//Document doc = Jsoup.parse(getSearchUrl(param));
		//Document doc = Jsoup.connect(getSearchUrl(param)).timeout(5000).get();
		Elements results = doc.getElementsByClass(TagClass.RESULTS_CONTAINER.getName());
		if(results != null && results.first() != null) {
    		for(Element result : results.first().getElementsByClass(TagClass.RESULT_CONTAINER.getName())) {
    			KslResultDto resultDto = getResultDto(param, result);
    			if(param.isTitleMustMatchKeywords() && !param.containsKeywords(resultDto.getTitle())) {
    				logger.info("Skipped result: " + resultDto.getTitle());
    				continue;
    			}
    			if(param.hasIgnoreWords() && param.containsIgnoreWords(resultDto.getTitle())) {
    				logger.info("Ignore result: " + resultDto.getTitle());
    				continue;
    			}
   				resultList.add(resultDto);
    			logger.info("Added result: " + resultList.get(resultList.size() - 1));
    		}
    	}
    	return resultList;
	}
    
    private String getUrlHtml(String urlString) {
    	StringBuilder html = new StringBuilder();
    	try {
	    	URL url = new URL(urlString);
	        BufferedReader in = new BufferedReader(
	        new InputStreamReader(url.openStream()));
	
	        String inputLine;
	        while ((inputLine = in.readLine()) != null) {
	        	html.append(inputLine);
	        }
	        in.close();
    	}catch (Exception e) {
			logger.severe("Error getting page html. " + e.getMessage());
		}
        return html.toString();
	}
    
    protected String getSearchUrl(KslParameterDto dto) {

    	StringBuilder builder = new StringBuilder();
    	builder.append(KSL_CLASSIFIED_URL);
//    	if(dto.getSubCategory() != null) {
//    		appendIfNotEmpty(builder, QueryKey.CATEGORY.key(), dto.getSubCategory().getCategory().getId());
//    		appendIfNotEmpty(builder, QueryKey.SUBCATEGORY.key(), dto.getSubCategory().getId());
//    	}
//    	if(dto.getCategory() != null) {
//    		appendIfNotEmpty(builder, QueryKey.CATEGORY.key(), dto.getCategory().getId());
//    	}
    	appendIfNotEmpty(builder, QueryKey.SELLER_TYPE.key(), PRIVATE_SELLER);
//    	appendIfNotEmpty(builder, QueryKey.NUMBER_RESULTS.key(), RESULTS_PER_PAGE);
    	appendIfNotEmpty(builder, QueryKey.KEYWORDS.key(), dto.getSearchString().replace(" ", "+"));
    	appendIfNotEmpty(builder, QueryKey.PRICE_LOW.key(), dto.getMinPrice());
    	appendIfNotEmpty(builder, QueryKey.PRICE_HIGH.key(), dto.getMaxPrice());
    	if(dto.getDistance() != null) {
	    	appendIfNotEmpty(builder, QueryKey.ZIPCODE.key(), dto.getDistance().getZipCode());
	    	appendIfNotEmpty(builder, QueryKey.MILES_FROM_ZIPCODE.key(), dto.getDistance().getMiles());
    	}
    	return builder.toString().replace("/&", "/?");
    }
    
    private void appendIfNotEmpty(StringBuilder builder, String key, Object value) {
		if(value != null && !value.toString().isEmpty()) {
			builder.append("&")
			.append(key)
			.append("=")
			.append(value.toString());
		}
	}

	protected KslResultDto getResultDto(KslParameterDto param, Element result) {
		KslResultDto dto = new KslResultDto();
		dto.setParameterDto(param);
		dto.setAdId(parseAdId(result));
		dto.setAdHref(parseAdHref(result));
		dto.setImageHref(parseImageHref(result));
		dto.setTitle(parseTitle(result));
		dto.setTime(parseTime(result));
		dto.setLocation(parseLocation(result));
		dto.setDescription(parseDescription(result));
		dto.setPrice(parsePrice(result));
		return dto;
	}
    
	protected Long parseAdId(Element result) {
		// <div class="listing" data-item-id="42486770" ...>
		return getLongValue(result.attr(TagClass.AD_ID.getName()));
	}

	protected String parseAdHref(Element result) {
    	Element element = result.getElementsByClass(TagClass.AD_IMAGE.getName()).first();
		if(element != null) {
			Element anchor = element.getElementsByTag("a").first();
			return anchor.attr("href");
		}
		return null;
	}

	protected String parseImageHref(Element result) {
		Element element = result.getElementsByClass(TagClass.AD_IMAGE.getName()).first();
		if(element != null) {
			Element anchor = element.getElementsByTag("img").first();
			return anchor.attr("src");
		}
		return null;
	}

	protected String parseTitle(Element result) {
		Element element = result.getElementsByClass(TagClass.AD_TITLE.getName()).first();
		return getStringValue(element);
	}

	protected String parseTime(Element result) {
		String time = parseRawTime(result);
		if(time != null) {
			time = time.replace("|", "").trim();
		}
		return time;
	}
	
	protected String parseLocation(Element result) {
		Element element = result.getElementsByClass(TagClass.AD_LOCATION.getName()).first();
		return getStringValue(element);
	}
	
	protected String parseRawTime(Element result) {
		Element element = result.getElementsByClass(TagClass.AD_TIME.getName()).first();
		return getStringValue(element);
	}

	protected String parseDescription(Element result) {
		Element element = result.getElementsByClass(TagClass.AD_DESC.getName()).first();
		return getStringValue(element);
	}

	protected Float parsePrice(Element result) {
		Element element = result.getElementsByClass(TagClass.AD_PRICE.getName()).first();
		return getFloatValue(element);
	}
	
	protected String getStringValue(Element element) {
    	if(element == null) {
    		return null;
    	}
    	return element.text();
    }
    
	protected Float getFloatValue(Element element) {
    	if(element == null) {
    		return null;
    	}
    	String value = getStringValue(element);
    	value = value.replace("$", "");
    	return getFloatValue(value);
    }
    
    protected Float getFloatValue(String value) {
    	try {
    		value = value.replace(",", "");
    		return Float.parseFloat(value);
    	}catch (NumberFormatException e) {
			logger.warning("Could not convert to a Float: " + value);
			return null;
		}
    }
    
    protected Long getLongValue(String value) {
    	try {
    		value = value.replace(",", "");
    		return Long.parseLong(value);
    	}catch (NumberFormatException e) {
    		logger.warning("Could not convert to a Long: " + value);
    		return null;
    	}
    }

	/**
     * Url search query keys
     */
    protected enum QueryKey {
    	/* %5B%5D is [] (open and close brackets)
    	 *  http://www.ksl.com/classifieds/search/
    	 * 	?keyword=Swarovski+Binocular
    	 * 	&category%5B%5D=Hunting+and+Fishing
    	 * 	&subCategory%5B%5D=Scopes+and+Optics
    	 * 	&priceFrom=1
    	 * 	&priceTo=1000
    	 *  &sellerType%5B%5D=Private
    	 * 	&city=
    	 * 	&state=UT
    	 * 	&zip=84043
    	 * 	&miles=25
    	 * 	&sort=4
    	 * 
    	 *  Per page changes now after results are back.  See if we can still pass it as initial request.
    	 *  ?perPage=24
    	 *  
    	 */
    	CATEGORY("category%5B%5D"),
    	KEYWORDS("keyword"),
    	MILES_FROM_ZIPCODE("miles"),
    	NUMBER_RESULTS("perPage"),
    	PRICE_LOW("priceFrom"),
    	PRICE_HIGH("priceTo"),
    	SELLER_TYPE("sellerType%5B%5D"),
    	SUBCATEGORY("subCategory%5B%5D"),
    	ZIPCODE("zip");
    	
    	private String key;
    	private QueryKey(String key) {
    		this.key = key;
    	}
		public String key() {
			return key;
		}
    }
    
    /**
     * For searching for elements by the class attribute
     */
    protected enum TagClass {
    	//outputs
    	RESULTS_CONTAINER("listing-group"), //all results
    	RESULT_CONTAINER("listing"), //individual result
    	AD_ID("data-item-id"),
    	AD_DETAILS("detailBox"),
    	AD_TITLE("title"),
    	AD_IMAGE("photo"),
    	AD_TIME("timeOnSite"),
    	AD_DESC("description"),
    	AD_LOCATION("address"),
    	AD_PRICE("price");
    	
    	private String name;
    	private TagClass(String name) {
    		this.name = name;
    	}
		public String getName() {
			return name;
		}
    }
}
