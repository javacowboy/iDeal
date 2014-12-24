package com.javacowboy.ideal.model.dto.ksl;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class KslParameterDto {
	
	protected String description;
    protected Category category;
    protected SubCategory subCategory;
    protected String[] keywords;
    protected String[] ignoreWords;
    protected Boolean titleMustMatchKeywords;
    protected Integer minPrice;
    protected Integer maxPrice;
    protected DistanceDto distance;

    public enum Category {
    	APPLIANCES(344),
    	AUTO_PARTS(100),
    	ELECTRONICS(345),
        FIREARMS(353),
        FURNITURE(40),
        GENERAL(63),
        HOME_GARDEN(51),
        OUTDOORS_SPORTING(184),
        RECREATION_VEHICLES(142);

        int id;
        private Category(int id) {
            this.id = id;
        }
        
        public int getId() {
        	return id;
        }
    }

    public enum SubCategory {
    	//APPLIANCES
    	DISHWASHERS(Category.APPLIANCES, 212),
    	MICROWAVES(Category.APPLIANCES, 211),
    	WASHER_DRYER(Category.APPLIANCES, 47),
    	
    	//AUTO
    	TRUCK_SHELLS(Category.AUTO_PARTS, 102),
    	UTILITY_TRAILERS(Category.AUTO_PARTS, 101),
    	
    	//FIREARMS
        AMMUNITION(Category.FIREARMS, 625),
        ARCHERY(Category.FIREARMS, 214),
        BIG_GAME_RIFLES(Category.FIREARMS, 624),
        GUN_SAFES_RACKS(Category.FIREARMS, 473),
    	HANDGUNS(Category.FIREARMS, 185),
    	HANDGUN_ACCESSORIES(Category.FIREARMS, 475),
    	HOLSTERS(Category.FIREARMS, 474),
    	MUZZLELOADER(Category.FIREARMS, 622),
    	RELOADING(Category.FIREARMS, 655),
        RIFLES(Category.FIREARMS, 377),
        RIFLE_ACCESSORIES(Category.FIREARMS, 380),
        SCOPES_AND_OPTICS(Category.FIREARMS, 405),
        SHOTGUNS(Category.FIREARMS, 375),
        SHOTGUN_ACCESSORIES(Category.FIREARMS, 535),
        
        //FURNITURE
        BUFFETS_HUTCHES_CURIOS(Category.FURNITURE, 362),
        CHAIRS(Category.FURNITURE, 364),
        CHILDREN_FURNITURE(Category.FURNITURE, 557),
        COFFEE_END_TABLES(Category.FURNITURE, 368),
        DESKS(Category.FURNITURE, 361),
        DINING_TABLES(Category.FURNITURE, 365),
        DRESSERS(Category.FURNITURE, 406),
        ENTERTAINMENT_CENTERS(Category.FURNITURE, 367),
        NIGHTSTANDS(Category.FURNITURE, 369),
        PATIO(Category.FURNITURE, 238),
        PIANOS(Category.FURNITURE, 235),
        SHELVING(Category.FURNITURE, 458),
        
        //HOME_GARDEN
        CURTAINS_BLINDS_SHUTTERS(Category.HOME_GARDEN, 629),
        LAWN_MOWERS(Category.HOME_GARDEN, 438),
        LUMBER(Category.HOME_GARDEN, 636),
        PAINTS_STAINS(Category.HOME_GARDEN, 661),
        WINDOWS_DOORS(Category.HOME_GARDEN, 633),
        
        //ELECTRONICS
        NINTENDO(Category.ELECTRONICS, 478),
        XBOX(Category.ELECTRONICS, 479),
        PLAYSTATION(Category.ELECTRONICS, 480),
        CAMERA_DIGITAL(Category.ELECTRONICS, 511),
        CAMERA_ACCESSORY(Category.ELECTRONICS, 26),
        CAMCORDER(Category.ELECTRONICS, 513),
        
        //GENERAL
        GUITARS(Category.GENERAL, 64),
        KEYBOARDS(Category.GENERAL, 545),
    	
    	//OUTDOORS AND SPORTING
        BACKPACKS(Category.OUTDOORS_SPORTING, 410),
        BIKES_BMX(Category.OUTDOORS_SPORTING, 408),
        BIKES_CHILDREN(Category.OUTDOORS_SPORTING, 488),
        BIKES_MOUNTAIN(Category.OUTDOORS_SPORTING, 191),
        BIKES_ROAD(Category.OUTDOORS_SPORTING, 409),
    	FITNESS_EQUIPMENT(Category.OUTDOORS_SPORTING, 416),
    	
    	//RECREATION_VEHICLES
    	GENERATORS(Category.RECREATION_VEHICLES, 231),
    	HITCHES(Category.RECREATION_VEHICLES, 531),
    	RV_RENTALS(Category.RECREATION_VEHICLES, 237),
    	TRAVEL_TRAILER_BUMPER(Category.RECREATION_VEHICLES, 149),
    	TRAVEL_TRAILER_FIFTH(Category.RECREATION_VEHICLES, 434),
    	TRAVEL_TRAILER_TOY(Category.RECREATION_VEHICLES, 436)
    	;
        
        Category category;
        int id;

        private SubCategory(Category category, int id) {
            this.category = category;
            this.id = id;
        }

        public Category getCategory() {
            return category;
        }
        
        public int getId() {
        	return id;
        }
    }
    
    //getters and setters
    public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public Category getCategory() {
    	if(subCategory != null) {
    		return subCategory.getCategory();
    	}
        return category;
    }

	public void setCategory(Category category) {
        this.category = category;
    }

    public SubCategory getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(SubCategory subCategory) {
        this.subCategory = subCategory;
    }

    public String[] getKeywords() {
        return keywords;
    }

    public void setKeywords(String[] keywords) {
        this.keywords = keywords;
    }

    public String[] getIgnoreWords() {
		return ignoreWords;
	}

	public void setIgnoreWords(String[] ignoreWords) {
		this.ignoreWords = ignoreWords;
	}
	
	public boolean hasIgnoreWords() {
		return ignoreWords != null && ignoreWords.length > 0;
	}

	public boolean isTitleMustMatchKeywords() {
		if(titleMustMatchKeywords == null) {
			titleMustMatchKeywords = false;
		}
		return titleMustMatchKeywords;
	}

	public void setTitleMustMatchKeywords(boolean titleMustMatchKeywords) {
		this.titleMustMatchKeywords = titleMustMatchKeywords;
	}

	public Integer getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Integer minPrice) {
        this.minPrice = minPrice;
    }

    public Integer getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Integer maxPrice) {
        this.maxPrice = maxPrice;
    }
    
    public boolean hasMinPrice() {
		return minPrice != null;
	}

	public boolean hasMaxPrice() {
		return maxPrice != null;
	}
	
	public DistanceDto getDistance() {
		return distance;
	}

	public void setDistance(DistanceDto distance) {
		this.distance = distance;
	}

	public void setDistance(String zipCode, Integer miles) {
		DistanceDto distance = new DistanceDto();
		distance.setZipCode(zipCode);
		distance.setMiles(miles);
		setDistance(distance);
	}

	@Override
    public String toString() {
        return (new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE){}).toString();
    }

	public String getSearchString() {
		if(keywords != null) {
			StringBuilder builder = new StringBuilder();
			for(String word : keywords) {
				builder.append(word.trim()).append(" ");
			}
			return builder.toString().trim();
		}
		return "";
	}

	public boolean containsKeywords(String title) {
		if(title == null || keywords == null) {
			return false;
		}
		for(String word : keywords) {
			if(!title.toLowerCase().contains(word.toLowerCase())) {
				return false;
			}
		}
		return true;
	}

	public boolean containsIgnoreWords(String title) {
		if(!hasIgnoreWords() || title == null) {
			return false;
		}
		for(String word : ignoreWords) {
			if(title.toLowerCase().contains(word.toLowerCase())) {
				return true;
			}
		}
		return false;
	}

}
