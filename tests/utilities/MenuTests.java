package utilities;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import discountExceptions.NoDiscountFoodItemsException;
import discountExceptions.NoDiscountIdException;
import discountExceptions.NoDiscountNameException;
import discountExceptions.NoDiscountPercentageException;
import foodItemExceptions.NoCategoryFoundException;
import foodItemExceptions.NoItemIDException;
import foodItemExceptions.NoItemNameFoundException;
import foodItemExceptions.NoPriceFoundException;
import model.Discount;
import model.FoodCategory;
import model.FoodItem;
import model.Menu;
import service.FileManager;


/**
 * This class should not be used to test the FIleManager, 
 * it should be used to test the MENU specific functions and calculations
 * 
 * 
 * */
public class MenuTests {
	private FileManager fm = new FileManager();
	/**
	@Test 
	void testCsvtoDiscount1() {
		Menu menuObj = new Menu();
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
		Menu menuObj = new Menu();
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
		Menu menuObj = new Menu();
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
		Menu menuObj = new Menu();
		Discount discObj = new Discount();
		try {
			discObj = menuObj.getDiscountObj("COMBO01,Kids Combo,5,HOT1:HOT2");
		} catch (NoDiscountIdException | NoDiscountNameException | NoDiscountPercentageException
				| NoDiscountFoodItemsException e) {
			e.printStackTrace();
		}
	    assertTrue(discObj.getItem_list().size() == 2);
	}
	
	*/
	/**
	 * This TEST IS THE SAME AS IN THE FILE MANAGER
	 *
	 * @Test
	void testFoodItemObjCreation() {
		Menu menuObj = new Menu();
		FoodItem foodObj,foodObj1;
		try {
			foodObj = fm.create_foodItem_fromCSV("HOT1,Americano,2.7,Espresso with hot water,HOT_BEVERAGE");
			foodObj1 = fm.create_foodItem_fromCSV("HOT2,Hot Chocolate,2.3,Milk with cocoa and sugar (marshmallows),HOT_BEVERAGE");
			menuObj.addFoodItems(FoodCategory.HOT_BEVERAGE, foodObj);
			menuObj.addFoodItems(FoodCategory.HOT_BEVERAGE, foodObj1);
		} catch (NoCategoryFoundException | NoItemIDException | NoItemNameFoundException | NoPriceFoundException e) {
			e.printStackTrace();
		}
		assertTrue(menuObj.getFoodItemsByCategory("HOT_BEVERAGE").size() == 2);
	}*/
	/**
	@Test
	void testDiscountCalculation() {
		Menu menuObj = new Menu();
		FoodItem foodObj,foodObj1;
		try {
			foodObj = fm.create_foodItem_fromCSV("HOT1,Americano,2.7,Espresso with hot water,HOT_BEVERAGE");
			foodObj1 = fm.create_foodItem_fromCSV("HOT2,Hot Chocolate,2.3,Milk with cocoa and sugar (marshmallows),HOT_BEVERAGE");
			menuObj.addFoodItems(FoodCategory.HOT_BEVERAGE, foodObj);
			menuObj.addFoodItems(FoodCategory.HOT_BEVERAGE, foodObj1);
		} catch (NoCategoryFoundException | NoItemIDException | NoItemNameFoundException | NoPriceFoundException e) {
			e.printStackTrace();
		}

		Discount discObj = new Discount();
		try {
			discObj = menuObj.getDiscountObj("COMBO01,Kids Combo,5,HOT1:HOT2");
		} catch (NoDiscountIdException | NoDiscountNameException | NoDiscountPercentageException
				| NoDiscountFoodItemsException e) {
			e.printStackTrace();
		}
		menuObj.getDiscounts().add(discObj);
		menuObj.addDiscountToMenu();

		FoodItem fi = menuObj.getFoodItemById("COMBO01");
		assertTrue(fi.getPrice() == 4.75);
	}
	*/
	
}
