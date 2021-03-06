package service;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.util.HashMap;

import org.junit.*;
import org.junit.jupiter.api.Test;

import customerOrderExceptions.NoOrderIdException;
import customerOrderExceptions.noCustomerIdException;
import customerOrderExceptions.noOrderItemException;
import customerOrderExceptions.noTimestampException;
import discountExceptions.NoDiscountFoodItemsException;
import discountExceptions.NoDiscountIdException;
import discountExceptions.NoDiscountNameException;
import discountExceptions.NoDiscountPercentageException;
import foodItemExceptions.NoCategoryFoundException;
import foodItemExceptions.NoItemIDException;
import foodItemExceptions.NoItemNameFoundException;
import foodItemExceptions.NoPriceFoundException;
import model.CustomerOrder;
import model.Discount;
import model.FoodCategory;
import model.FoodItem;
import model.Menu;
import service.FileManager;
import service.OrderManager;

class FileManagerTest {

	
		FileManager f = new FileManager();	

	
	
	@Test
	void testread_data_by_line() throws NoCategoryFoundException, NoItemIDException, NoItemNameFoundException, FileNotFoundException {
		f.read_data_by_line("csvTestFiles/menuTests/menu_correctItem.csv" );
		
	}
	
/**Food Item Tests 
Can't test because it's private

	@Test
	void testcreate_foodItem_fromCSV() throws NoCategoryFoundException, NoItemIDException, NoItemNameFoundException, NoPriceFoundException {
		FileManager f = new FileManager();
		FoodItem item = f.create_foodItem_fromCSV("HOT1,Americano,2.70,\"Espresso with hot water\",HOT_BEVERAGE");
		assertEquals("HOT1",item.getItemID());
	}
	
	
	//Item should be created without a description
	@Test 
	void testcreate_foodItem_noDescription() throws FileNotFoundException, NoCategoryFoundException, NoItemIDException, NoItemNameFoundException, NoPriceFoundException {
		FileManager f = new FileManager();
		FoodItem item = f.create_foodItem_fromCSV("HOT1,Americano,2.70,,HOT_BEVERAGE");
		assertEquals("HOT1",item.getItemID());
	}
	//No Item ID will show an error and not create an item
	@Test
	void testcreate_foodItem_noItemID() throws NoCategoryFoundException, NoItemNameFoundException, NoPriceFoundException {
		try {
			FileManager f = new FileManager();
			f.create_foodItem_fromCSV(",Americano,2.70,\"Espresso with hot water\",HOT_BEVERAGE");
		}catch(NoItemIDException e){
			assertEquals("Missing item ID",e.getMessage());
		}		
		
	}
	//No category should create a category based on the item ID
	@Test
	void testcreate_foodItem_noCategory() throws NoCategoryFoundException, NoItemNameFoundException, NoPriceFoundException, NoItemIDException {
		FileManager f = new FileManager();
		FoodItem item = f.create_foodItem_fromCSV("HOT1;Americano;2.70;\"Espresso with hot water\";");
		assertEquals("HOT1",item.getItemID());		
		
	}

*/
	
/**Menu from CSV Tests */

	//
	@Test
	void testcreate_menu() throws NoCategoryFoundException, NoItemIDException, NoItemNameFoundException, FileNotFoundException {
		HashMap<String, FoodItem> menu = f.create_menu("csvTestFiles/menuTests/menu_correctItem.csv");
		assertTrue(menu.containsKey("HOT1"));
	}
	
	//Item should be created even if the id is written in lower case
	//Menu should have 2 Items
	@Test 
	void testcreate_menu_lowerCaseItemID() throws FileNotFoundException {
		HashMap<String, FoodItem> menu = f.create_menu("csvTestFiles/menuTests/menu_LowCaseItemID.csv");
		assertTrue(menu.containsKey("HOT1"));
		assertEquals(2,menu.size());
	}
	
	//Item should be created without a description
	@Test 
	void testcreate_menu_noDescription() throws FileNotFoundException {
		HashMap<String, FoodItem> menu = f.create_menu("csvTestFiles/menuTests/menu_noDescription.csv");
		assertTrue(menu.containsKey("HOT1"));
	}
	
	//Item should not be created without ItemID
	//Menu should be created with valid entries
	@Test 
	void testcreate_menu_noitemID() throws FileNotFoundException {
		HashMap<String, FoodItem> menu = f.create_menu("csvTestFiles/menuTests/menu_noItemID.csv");
		assertTrue(menu.containsKey("HOT2"));
	}

	//No category should create a category based on the item ID
	@Test
	void testcreate_menu_noCategory() throws NoCategoryFoundException, NoItemIDException, NoItemNameFoundException, FileNotFoundException {
		HashMap<String, FoodItem> menu = f.create_menu("csvTestFiles/menuTests/menu_noCategory.csv");
		assertEquals(FoodCategory.HOT_BEVERAGE,menu.get("HOT1").getCategory());
	}
	
	//No category should create a category based on the item ID
	@Test
	void testCreateMenu() throws NoCategoryFoundException, NoItemIDException, NoItemNameFoundException, FileNotFoundException {
		HashMap<String, FoodItem> menu = f.create_menu();
		assertEquals(FoodCategory.HOT_BEVERAGE,menu.get("HOT1").getCategory());
	}

/**Create Order From CSV Line tests
 * @throws noOrderItemException 
 * @throws NoOrderIdException 
 * @throws noTimestampException 
 * @throws noCustomerIdException 
	
	@Test
	void test__CustomerOrder_from_string() throws noCustomerIdException, noTimestampException, NoOrderIdException, noOrderItemException {
		FileManager f = new FileManager();
		
		Menu menu_item = new Menu();
		
		
		
		FoodItem food1 = new FoodItem("COLD001","Tea",5,"masala_tea",FoodCategory.COLD_BEVERAGE);
		menu_item.addFoodItems(FoodCategory.COLD_BEVERAGE, food1);
		FoodItem food2 = new FoodItem("HOT001","Coffee",15,"Costa",FoodCategory.HOT_BEVERAGE);
		menu_item.addFoodItems(FoodCategory.HOT_BEVERAGE, food2);
		
		FoodItem food3 = new FoodItem("SAND01","Pizza",10,"Veg_pizza",FoodCategory.SANDWICH);
		menu_item.addFoodItems(FoodCategory.SANDWICH, food3);
		
		FoodItem food4 = new FoodItem("BAKE001","Pie",12,"Strawberry",FoodCategory.BAKE);
		menu_item.addFoodItems(FoodCategory.BAKE, food4);
		
		CustomerOrder o = f.create_CustomerOrder_from_string("100;5210;HOT001;2019-01-31", menu_item);
		assertEquals("5210", o.getCustomerId());
	}
	*/
/**Order Manager Tests*/
	@Test
	void testBuildCustomerOrdersFromOrderHistory() {
		Menu menu = Menu.getInstance();
		
		HashMap<String, CustomerOrder> order = f.buildCustomerOrdersFromOrderHistory(menu);
		assertTrue(order.containsKey("100"));
		assertTrue(order.containsKey("222"));
		assertTrue(order.containsKey("332"));
		
		assertEquals(7,order.get("100").totalItemsCount());
		assertEquals(5,order.get("222").totalItemsCount());
		assertEquals(4,order.get("332").totalItemsCount());

		
	}
	
	@Test
	void testBuildCustomerOrdersFromOrderHistory_noPastOrders() {
			
		OrderManager om = OrderManager.getInstance();
		HashMap<String, CustomerOrder> order = om.getOrderMap();
		
		assertTrue(order.containsKey("100"));
		assertTrue(order.containsKey("222"));
		assertTrue(order.containsKey("332"));
		
		assertEquals(7,order.get("100").totalItemsCount());
		assertEquals(5,order.get("222").totalItemsCount());
		assertEquals(4,order.get("332").totalItemsCount());
		
		assertEquals(3,om.getAllOrdersOnOrderQueue().size());

	}
	
	
/**Discounts tests
 * @throws NoDiscountFoodItemsException 
 * @throws NoDiscountNameException 
 * @throws NoDiscountIdException 
 * @throws NoDiscountPercentageException */
	@Test
	void testcreateDiscountFromString() throws NoDiscountPercentageException, NoDiscountIdException, NoDiscountNameException, NoDiscountFoodItemsException {
		FileManager f = new FileManager();
		//Menu menu = Menu.getInstance();
		
		Discount d = f.createDiscountFromString("COMBO01,Kids Combo,5,HOT1:HOT2:");
		
		
		assertTrue(d.getDiscountId().contains("COMBO01"));
		assertEquals("Kids Combo",d.getOffer_name());
		assertTrue(d.getDiscount_percentage() == 5);
		
		assertTrue(d.containsItemID("HOT1"));
		assertTrue(d.containsItemID("HOT2"));

	
	}
	
}