package com.javacowboy.ideal.docs;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import com.javacowboy.ideal.Constants;
import com.javacowboy.ideal.model.dto.ksl.KslParameterDto.Category;
import com.javacowboy.ideal.model.dto.ksl.KslParameterDto.SubCategory;
import com.javacowboy.ideal.service.FileService;

/**
 * A class that generates a document on the possible Categories and Subcategories supported in the application.
 * @author matthew
 *
 */
public class CategoryGenerator {
	
	public static void main (String[] args) {
		StringBuilder builder = new StringBuilder();
		builder.append("A list of the currently supported Ksl categories and subcategories.");
		newLine(builder);
		newLine(builder);
		
		Map<String, Set<String>> categoryMap = buildCategoryMap();
		for(String category : categoryMap.keySet()) {
			builder.append(category);
			newLine(builder);
			Set<String> subcategories = categoryMap.get(category);
			for(String sub : subcategories) {
				tab(builder);
				builder.append(sub);
				newLine(builder);
			}
			newLine(builder);
		}
		
		FileService fileService = new FileService();
		fileService.save(Constants.docDir, Constants.docCategoryFilename, builder.toString());
	}
	
	private static Map<String, Set<String>> buildCategoryMap() {
		Map<String, Set<String>> map = new TreeMap<String, Set<String>>();
		//create all categories that have subcategories first
		for(SubCategory sub : SubCategory.values()) {
			Set<String> subcategories = map.get(sub.getCategory().toString());
			if(subcategories == null) {
				subcategories = new TreeSet<String>();
				map.put(sub.getCategory().toString(), subcategories);
			}
			subcategories.add(sub.toString());
		}
		//create any categories that don't have subcategories
		for(Category category : Category.values()) {
			if(map.get(category.toString()) == null) {
				map.put(category.toString(), new TreeSet<String>());
			}
		}
		return map;
	}

	private static void newLine(StringBuilder builder) {
		builder.append("\r\n");
	}
	
	private static void tab(StringBuilder builder) {
		builder.append("\t");
	}

}
