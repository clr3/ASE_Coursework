package CoffeeShopUtilities;
import java.util.ArrayList;
import java.util.HashMap;

public class Menu {
	
	//HashMap<Integer, String> menu = new HashMap<Integer, String>();
	
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
