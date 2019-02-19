package CoffeeShopUtilitiesTests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

import CoffeeShopUtilities.CustomerOrder;
import CoffeeShopUtilities.FileManager;
import CoffeeShopUtilities.FoodCategory;
import CoffeeShopUtilities.FoodItem;
import CoffeeShopUtilities.Menu;
import CoffeeShopUtilities.OrderManager;
import customerOrderExceptions.NoOrderIdException;
import customerOrderExceptions.noCustomerIdException;
import customerOrderExceptions.noOrderItemException;
import customerOrderExceptions.noTimestampException;
import foodItemExceptions.NoCategoryFoundException;
import foodItemExceptions.NoItemIDException;
import foodItemExceptions.NoItemNameFoundException;
import foodItemExceptions.NoPriceFoundException;

class FileManagerTest {

	
	@Test
	void testread_data_by_line() throws NoCategoryFoundException, NoItemIDException, NoItemNameFoundException, FileNotFoundException {
		FileManager f = new FileManager();
		
		f.read_data_by_line("csvTestFiles/foodItemTests/menu_correctItem.csv" );
		
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
		FileManager f = new FileManager();
		HashMap<String, FoodItem> menu = f.create_menu("csvTestFiles/foodItemTests/menu_correctItem.csv");
		assertTrue(menu.containsKey("HOT1"));
	}
	
	//Item should be created even if the id is written in lower case
	//Menu should have 2 Items
	@Test 
	void testcreate_menu_lowerCaseItemID() throws FileNotFoundException {
		FileManager f = new FileManager();
		HashMap<String, FoodItem> menu = f.create_menu("csvTestFiles/foodItemTests/menu_LowCaseItemID.csv");
		assertTrue(menu.containsKey("HOT1"));
		assertEquals(2,menu.size());
	}
	
	//Item should be created without a description
	@Test 
	void testcreate_menu_noDescription() throws FileNotFoundException {
		FileManager f = new FileManager();
		HashMap<String, FoodItem> menu = f.create_menu("csvTestFiles/foodItemTests/menu_noDescription.csv");
		assertTrue(menu.containsKey("HOT1"));
	}
	
	//Item should not be created without ItemID
	//Menu should be created with valid entries
	@Test 
	void testcreate_menu_noitemID() throws FileNotFoundException {
		FileManager f = new FileManager();
		HashMap<String, FoodItem> menu = f.create_menu("csvTestFiles/foodItemTests/menu_noItemID.csv");
		assertTrue(menu.containsKey("HOT2"));
	}

	//No category should create a category based on the item ID
	@Test
	void testcreate_menu_noCategory() throws NoCategoryFoundException, NoItemIDException, NoItemNameFoundException, FileNotFoundException {
		FileManager f = new FileManager();
		HashMap<String, FoodItem> menu = f.create_menu("csvTestFiles/foodItemTests/menu_noCategory.csv");
		assertEquals(FoodCategory.HOT_BEVERAGE,menu.get("HOT1").getCategory());
	}
	
	//No category should create a category based on the item ID
	@Test
	void testCreateMenu() throws NoCategoryFoundException, NoItemIDException, NoItemNameFoundException, FileNotFoundException {
		FileManager f = new FileManager();
		HashMap<String, FoodItem> menu = f.create_menu();
		assertEquals(FoodCategory.HOT_BEVERAGE,menu.get("HOT1").getCategory());
	}

/**Create Order From CSV Line tests
 * @throws noOrderItemException 
 * @throws NoOrderIdException 
 * @throws noTimestampException 
 * @throws noCustomerIdException */
	@Test
	void test__read_orderHistory() throws noCustomerIdException, noTimestampException, NoOrderIdException, noOrderItemException {
		FileManager f = new FileManager();
		Menu menu = new Menu();
		CustomerOrder o = f.create_CustomerOrder_from_string("100;5210;HOT1;2019-01-31", menu);
		assertEquals("5210", o.getOrderId());
	}
	
/**Order Manager Tests*/
	@Test
	void testBuildCustomerOrdersFromOrderHistory() {
		FileManager f = new FileManager();
		Menu menu = new Menu();
		HashMap<String, CustomerOrder> order = f.buildCustomerOrdersFromOrderHistory(menu);
		assertTrue(order.containsKey("100"));
	}
	
	@Test
	void testBuildCustomerOrdersFromOrderHistory_noPastOrders() {
			
		OrderManager om = new OrderManager();
		HashMap<String, CustomerOrder> order = om.getOrderMap();
		assertTrue(order.containsKey("100"));
	
	}
}