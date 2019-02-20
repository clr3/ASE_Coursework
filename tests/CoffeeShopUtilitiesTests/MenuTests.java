package CoffeeShopUtilitiesTests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

import CoffeeShopUtilities.CustomerOrder;
import CoffeeShopUtilities.Discount;
import CoffeeShopUtilities.FoodCategory;
import CoffeeShopUtilities.FoodItem;
import CoffeeShopUtilities.Menu;
import CoffeeShopUtilities.OrderManager;
import discountExceptions.NoDiscountFoodItemsException;
import discountExceptions.NoDiscountIdException;
import discountExceptions.NoDiscountNameException;
import discountExceptions.NoDiscountPercentageException;

public class MenuTests {
	private Menu menuObj = new Menu();
	
	@Test 
	void testCsvtoDiscount1() { 
		Discount discObj = new Discount();
		try {
			discObj = menuObj.getDiscountObj("COMBO01,Kids Combo,5,HOT1:HOT2");
		} catch (NoDiscountIdException | NoDiscountNameException | NoDiscountPercentageException
				| NoDiscountFoodItemsException e) {
			e.printStackTrace();
		}
	    assertTrue(discObj.getDiscountId().equals("COMBO01"));
	}
	
	
	@Test 
	void testCsvtoDiscount2() { 
		Discount discObj = new Discount();
		try {
			discObj = menuObj.getDiscountObj("COMBO01,Kids Combo,5,HOT1:HOT2");
		} catch (NoDiscountIdException | NoDiscountNameException | NoDiscountPercentageException
				| NoDiscountFoodItemsException e) {
			e.printStackTrace();
		}
	    assertTrue(discObj.getOffer_name().equals("Kids Combo"));
	}
	
	@Test 
	void testCsvtoDiscount3() { 
		Discount discObj = new Discount();
		try {
			discObj = menuObj.getDiscountObj("COMBO01,Kids Combo,5,HOT1:HOT2");
		} catch (NoDiscountIdException | NoDiscountNameException | NoDiscountPercentageException
				| NoDiscountFoodItemsException e) {
			e.printStackTrace();
		}
	    assertTrue(discObj.getDiscount_percentage() == 5);
	}
	
	@Test 
	void testCsvtoDiscount4() { 
		Discount discObj = new Discount();
		try {
			discObj = menuObj.getDiscountObj("COMBO01,Kids Combo,5,HOT1:HOT2");
		} catch (NoDiscountIdException | NoDiscountNameException | NoDiscountPercentageException
				| NoDiscountFoodItemsException e) {
			e.printStackTrace();
		}
	    assertTrue(discObj.getItem_list().size() == 2);
	}
	
	

}
