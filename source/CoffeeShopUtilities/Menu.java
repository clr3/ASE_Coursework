package CoffeeShopUtilities;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;

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
	
	public   Menu() {
		
		FileManager fm = new FileManager();
		try {
			ArrayList<String> menu_csv_list = fm.read_data_by_line("csvFiles/menu_coffeeShop.csv");
			//System.out.println(menu_csv_list);
			for (int counter = 0; counter < menu_csv_list.size(); counter++) { 		      
		         // System.out.println(menu_csv_list.get(counter)); 	
		        FoodItem  foodObj = fm.create_foodItem_fromCSV(menu_csv_list.get(counter));
		        addFoodItems(foodObj.getCategory(),foodObj);
		         
		      }   		

			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoCategoryFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoItemIDException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoItemNameFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

	private  EnumMap<FoodCategory ,HashMap<String , FoodItem>> menu = new EnumMap<FoodCategory,HashMap<String , FoodItem>>(FoodCategory.class);    
	private String discounts;


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
