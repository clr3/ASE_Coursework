package CoffeeShopUtilities;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map.Entry;

import foodItemExceptions.NoCategoryFoundException;
import foodItemExceptions.NoItemIDException;
import foodItemExceptions.NoItemNameFoundException;
import foodItemExceptions.NoPriceFoundException;

public class Menu {
	
	private  EnumMap<FoodCategory ,HashMap<String , FoodItem>> menu = new EnumMap<FoodCategory,HashMap<String , FoodItem>>(FoodCategory.class);    
	private ArrayList<Discount> discounts = new ArrayList<Discount>();
	private int enum_no=5;
	
	public Menu() {
		
		FileManager fm = new FileManager();
		HashMap<String , FoodItem> items = new HashMap<String , FoodItem>();
	
		try {
			
			items = fm.create_menu();
	
			//Iterate the menu items 
			for(HashMap.Entry<String , FoodItem> item: items.entrySet()) {	
						
				addFoodItems(item.getValue().getCategory(), item.getValue());		
			}
			
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	private void createDiscount() {
		Discount discount_obj = new Discount();
		discount_obj.setDiscountId("COMBO01");
		discount_obj.setOffer_name("Kids Combo");
		discount_obj.getItem_list().add("HOT1");
		discount_obj.getItem_list().add("HOT2");
		discount_obj.setDiscount_percentage(5);
		discounts.add(discount_obj);
		
		Discount discount_obj1 = new Discount();
		discount_obj.setDiscountId("COMBO02");
		discount_obj1.setOffer_name("Saver Menu");
		discount_obj1.getItem_list().add("HOT2");
		discount_obj1.getItem_list().add("BAKE1");
		discount_obj1.setDiscount_percentage(10);
		discounts.add(discount_obj1);

		Discount discount_obj2 = new Discount();
		discount_obj.setDiscountId("COMBO03");
		discount_obj2.setOffer_name("BREAKFAST");
		discount_obj2.getItem_list().add("English Breakfast");
		discount_obj2.getItem_list().add("BAKE");
		discount_obj2.setDiscount_percentage(25);
		discounts.add(discount_obj2);
	}

	public ArrayList<Discount> getDiscounts() {
		return discounts;
	}

	public void setDiscounts(ArrayList<Discount> discounts) {
		this.discounts = discounts;
	}

	public EnumMap<FoodCategory, HashMap<String , FoodItem>> getMenu() {
		return menu;
	}

	public void setMenu(EnumMap<FoodCategory, HashMap<String , FoodItem>> menu) {
		this.menu = menu;
	}

	public void addFoodItems(FoodCategory category,FoodItem food) {
		if (menu.containsKey(category)) {
			HashMap<String , FoodItem> f1 = menu.get(category);
			f1.put(food.getItemID(),food);
		    //System.out.println("Exist");
		} else {
			HashMap<String , FoodItem> food_list = new HashMap<String , FoodItem>();
			food_list.put(food.getItemID(),food);
			
			menu.put(category,food_list);
			//System.out.println(" Not Exist");
		    
		}
	}
	
	public HashMap<String , FoodItem> getFoodItemsByCategory(String categoryName) {
		if(menu.containsKey(FoodCategory.valueOf(categoryName))) {
			return menu.get(FoodCategory.valueOf(categoryName));
		}
		return null;
	}

	@Override
	public String toString() {
		return "Menu [menu=" + menu + "]";
	}
	
}
