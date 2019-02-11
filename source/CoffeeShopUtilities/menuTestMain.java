package CoffeeShopUtilities;


public class menuTestMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Menu menu_item = new Menu();
		
		
		FoodItem food1 = new FoodItem("B001","Tea",5,"masala_tea",FoodCategory.BEVERAGES);
		menu_item.addFoodItems(FoodCategory.BEVERAGES, food1);
		FoodItem food2 = new FoodItem("B001","Coffee",15,"Costa",FoodCategory.BEVERAGES);
		menu_item.addFoodItems(FoodCategory.BEVERAGES, food2);
		
		FoodItem food3 = new FoodItem("MC01","Pizza",10,"Veg_pizza",FoodCategory.MAIN_COURSE);
		menu_item.addFoodItems(FoodCategory.MAIN_COURSE, food3);
		
		FoodItem food4 = new FoodItem("D001","Ice_Cream",12,"Strawberry",FoodCategory.DESSERTS);
		menu_item.addFoodItems(FoodCategory.DESSERTS, food4);
		
		
	
	System.out.println(menu_item);
	}

	
}
