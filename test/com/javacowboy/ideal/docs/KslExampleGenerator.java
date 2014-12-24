package com.javacowboy.ideal.docs;

import java.util.ArrayList;
import java.util.List;

import com.javacowboy.ideal.Constants;
import com.javacowboy.ideal.model.dao.UserDao;
import com.javacowboy.ideal.model.dto.ksl.KslParameterDto;
import com.javacowboy.ideal.model.dto.ksl.KslParameterDto.Category;
import com.javacowboy.ideal.model.dto.ksl.KslParameterDto.SubCategory;

public class KslExampleGenerator {
	
	public static void main (String[] args) {

        List<KslParameterDto> list = new ArrayList<KslParameterDto>();
        KslParameterDto dto = new KslParameterDto();
        dto.setDescription("A simple search that looks for any item in subcategory NIGHTSTANDS under $20");
        dto.setSubCategory(KslParameterDto.SubCategory.NIGHTSTANDS);
        dto.setMaxPrice(20);
        list.add(dto);
        
        dto = new KslParameterDto();
        dto.setDescription("Search subcategory HANDGUNS for keywords Kimber and Custom under $700.  titleMustMatchKeywords is set to true to weed out descriptions like I will trade my Glock for a Kimber Custom");
        dto.setSubCategory(KslParameterDto.SubCategory.HANDGUNS);
        dto.setKeywords(new String[]{"Kimber", "Custom"});
        dto.setTitleMustMatchKeywords(true);
        dto.setMaxPrice(700);
        list.add(dto);
        
        dto = new KslParameterDto();
        dto.setDescription("Search category (not subcategory) FIREARMS for a 1911 under $500.  This may return handguns and holsters for a 1911.");
        dto.setCategory(Category.FIREARMS);
        dto.setKeywords(new String[] {"1911"});
        dto.setMaxPrice(500);
        list.add(dto);
        
        dto = new KslParameterDto();
        dto.setDescription("Search the subcategory HANDGUNS for 1911 to weed out holsters.  Also ignore a 1911 that is a .22 or 9mm.  And ignore descriptions like I will trade my gun for a 1911.");
        dto.setSubCategory(KslParameterDto.SubCategory.HANDGUNS);
        dto.setKeywords(new String[]{"1911"});
        dto.setIgnoreWords(new String[] {"1911-22", "22LR", "9mm"});
        dto.setTitleMustMatchKeywords(true);
        dto.setMaxPrice(500);
        list.add(dto);

        dto = new KslParameterDto();
        dto.setDescription("Another example of searching a subcategory based on keywords and price.");
        dto.setSubCategory(KslParameterDto.SubCategory.RIFLES);
        dto.setKeywords(new String[]{"Remington", "700", "scope"});
        dto.setMaxPrice(500);
        list.add(dto);
        
        
        dto = new KslParameterDto();
        dto.setDescription("Search for any mountain bike within 25 miles from Lehi (84043).");
        dto.setSubCategory(SubCategory.BIKES_MOUNTAIN);
        dto.setDistance("84043", 25);
        list.add(dto);
        
        UserDao dao = new UserDao();
        dao.toXml(Constants.kslConfigDir, Constants.kslExampleFile, list);
        //dao.toXml(Constants.DOC_DIR, KSL_EXAMPLE_FILE, list);
	}
}
