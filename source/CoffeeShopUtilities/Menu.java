package CoffeeShopUtilities;
import java.util.ArrayList;
import java.util.HashMap;

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
	private  HashMap<FoodCategory ,ArrayList<FoodItem>> menu = new HashMap<FoodCategory,ArrayList<FoodItem>>();    
	private String discounts;


	public String getDiscounts() {
		return discounts;
	}

	public void setDiscounts(String discounts) {
		this.discounts = discounts;
	}

		

	public HashMap<FoodCategory, ArrayList<FoodItem>> getMenu() {
		return menu;
	}

	public void setMenu(HashMap<FoodCategory, ArrayList<FoodItem>> menu) {
		this.menu = menu;
	}

	public void addFoodItems(FoodCategory category,FoodItem food) {
		if (menu.containsKey(category)) {
			ArrayList<FoodItem> f1 = menu.get(category);
			f1.add(food);
		    System.out.println("Exist");
		} else {
			ArrayList<FoodItem> food_list = new ArrayList<FoodItem>();
			food_list.add(food);
			
			menu.put(category,food_list);
			System.out.println(" Not Exist");
		    
		}
		
	
	}

	@Override
	public String toString() {
		return "Menu [menu=" + menu + "]";
	}
	

}
