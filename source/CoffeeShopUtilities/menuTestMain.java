package CoffeeShopUtilities;


public class menuTestMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Menu menu_item = new Menu();
		
		
		FoodItem food1 = new FoodItem("B001","Tea",5,"masala_tea",FoodCategory.BEVERAGE);
		menu_item.addFoodItems(FoodCategory.BEVERAGE, food1);
		FoodItem food2 = new FoodItem("B001","Coffee",15,"Costa",FoodCategory.BEVERAGE);
		menu_item.addFoodItems(FoodCategory.BEVERAGE, food2);
		
		FoodItem food3 = new FoodItem("MC01","Pizza",10,"Veg_pizza",FoodCategory.SANDWICH);
		menu_item.addFoodItems(FoodCategory.SANDWICH, food3);
		
		FoodItem food4 = new FoodItem("D001","Ice_Cream",12,"Strawberry",FoodCategory.BAKE);
		menu_item.addFoodItems(FoodCategory.BAKE, food4);
		
		
	
	System.out.println(menu_item);
	}

	
}
