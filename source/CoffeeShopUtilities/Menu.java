package CoffeeShopUtilities;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map.Entry;

import foodItemExceptions.NoCategoryFoundException;
import foodItemExceptions.NoItemIDException;
import foodItemExceptions.NoItemNameFoundException;

public class Menu {
	
	//HashMap<Integer, String> menu = new HashMap<Integer, String>();
	/**Cristy's Comment:
	 * I think that to hold the Food items, it should be a HashMap with the ItemID
	 * 	HashMap<FoodItem.ItemID,FoodItem>;
	 * 
	 * if you are using food category as identifier, we should use an enumMap:
	 * 	EnumMap<FoodCategory,HashMap<FoodItem.ItemID,FoodItem>> menu;
	 * 
	 * This can help to identify them by category and also by item 
	 * 
	 **/
	private  EnumMap<FoodCategory ,HashMap<String , FoodItem>> menu = new EnumMap<FoodCategory,HashMap<String , FoodItem>>(FoodCategory.class);    
	private String discounts;

	public   Menu() {
		FileManager fm = new FileManager();
		HashMap<String,FoodItem> unsortedMenu = null;

		try {
			unsortedMenu = fm.create_menu();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(Entry<String, FoodItem> item: unsortedMenu.entrySet()) {
			FoodItem newItem = item.getValue();
			//Sort the menu into the EnumMap here..
		}
	}


	public String getDiscounts() {
		return discounts;
	}

	public void setDiscounts(String discounts) {
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

	@Override
	public String toString() {
		return "Menu [menu=" + menu + "]";
	}
	

}
