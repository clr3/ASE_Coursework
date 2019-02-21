package CoffeeShopUtilities;
import java.io.*;

public class menuTestMain {

	public void main123(String[] args) {
		// TODO Auto-generated method stub
		
		Menu menu_item = new Menu();
		
		
		
		FoodItem food1 = new FoodItem("COLD001","Tea",5,"masala_tea",FoodCategory.COLD_BEVERAGE);
		menu_item.addFoodItems(FoodCategory.COLD_BEVERAGE, food1);
		FoodItem food2 = new FoodItem("HOT001","Coffee",15,"Costa",FoodCategory.HOT_BEVERAGE);
		menu_item.addFoodItems(FoodCategory.HOT_BEVERAGE, food2);
		
		FoodItem food3 = new FoodItem("SAND01","Pizza",10,"Veg_pizza",FoodCategory.SANDWICH);
		menu_item.addFoodItems(FoodCategory.SANDWICH, food3);
		
		FoodItem food4 = new FoodItem("BAKE001","Pie",12,"Strawberry",FoodCategory.BAKE);
		menu_item.addFoodItems(FoodCategory.BAKE, food4);
		
		
	
	System.out.println(menu_item);
	}

}

