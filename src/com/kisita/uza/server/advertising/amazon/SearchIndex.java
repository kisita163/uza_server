package com.kisita.uza.server.advertising.amazon;

public final class SearchIndex {
	// Index
	public static String ALL                  =  "All";
	public static String BABY                 =  "Baby";
	public static String BEAUTY               =  "Beauty";
	public static String BLENDED              =  "Blended";
	public static String BOOKS                =  "Books";
	public static String CLASSICAL            =  "Classical";
	public static String DVD                  =  "DVD";
	public static String ELECTRONICS          =  "Electronics";
	public static String FOREIGN_BOOKS        =  "ForeignBooks";
	public static String GIFT_CARDS           =  "GiftCards";
	public static String HEALTH_PERSONAL_CARE =  "HealthPersonalCare";
	public static String JEWELRY              =  "Jewelry";
	public static String KINDLE_STORE         =  "KindleStore";
	public static String KITCHEN              =  "Kitchen";
	public static String LAWN_AND_GARDEN      =  "LawnAndGarden";
	public static String LIGHTING             =  "Lighting";
	public static String LUGGAGE              =  "Luggage";
	public static String SHOES                =  "Shoes";
	public static String TOYS                 =  "Toys";
	public static String VIDEOGAMES           =  "VideoGames";
	public static String WATCHES              =  "Watches";
	
	//Uza Categories
	public static String MEN                  =  "Men";
	public static String WOMEN                =  "Women";
	public static String KIDS                 =  "Kids";
	public static String UZA_ELECTRONICS      =  "Electronic";
	public static String HOME                 =  "Home";
	
	// Uza types
	public static String VIDEO_GAMES          =  "Video games";
	public static String SHOES_BAGS           =  "Shoes & Bags";
	public static String WATCHES_ACCESSORIES  =  "Watches & Accessories";
	public static String PERFUMES_BEAUTY      =  "Perfumes & Beauty";
	public static String CLOTHING             =  "Clothing";
	public static String BATHING_SKIN         =  "Bathing  & Skin care";
	public static String TOYS_ACCESSORIES     =  "Toys & Accessories";
	
	public static String ELECTRONICS_HOME     =  "Home";
	public static String PHONE_ACCESSORIES    =  "Phones & Accessories";
	public static String COMPUTERS_TABLETS    =  "Computers & Tablets";
	
	public static String LIVING_DINING        =  "Living & Dining room";
	public static String BEDROOM              =  "Bedroom";
	public static String KITCHEN_BATH         =  "Kitchen & Bath room";
	public static String GARDEN               =  "Garden";
	
	
	
	public static String [] indexes           = {ALL,BABY,BEAUTY,BLENDED,BOOKS,CLASSICAL,DVD,ELECTRONICS,FOREIGN_BOOKS,GIFT_CARDS,HEALTH_PERSONAL_CARE,JEWELRY,KINDLE_STORE,KITCHEN,
		LAWN_AND_GARDEN,LIGHTING,LUGGAGE,SHOES,TOYS,VIDEOGAMES,WATCHES};
	public static String [] categories        = {MEN,WOMEN,KIDS,UZA_ELECTRONICS,HOME};
	
	public static String [] men_women_types   = {CLOTHING,SHOES_BAGS,WATCHES_ACCESSORIES,PERFUMES_BEAUTY};
	
	public static String [] kids_types        = {CLOTHING,SHOES_BAGS,BATHING_SKIN,TOYS_ACCESSORIES};
	
	public static String [] electronics_types = {VIDEO_GAMES,ELECTRONICS_HOME,PHONE_ACCESSORIES,COMPUTERS_TABLETS};
	
	public static String [] home_types        = {LIVING_DINING,BEDROOM,KITCHEN_BATH,GARDEN};
}
