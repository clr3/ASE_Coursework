package CoffeeShopUtilitiesTests;
/**
 * @Author Cristina Rivera 	<clr3@hw.ac.uk>
 * 
 * */
import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

import CoffeeShopUtilities.CustomerOrder;
import CoffeeShopUtilities.FileManager;
import CoffeeShopUtilities.FoodItem;
import foodItemExceptions.NoCategoryFoundException;
import foodItemExceptions.NoItemIDException;
import foodItemExceptions.NoItemNameFoundException;

class FileManagerTests {

	@Test
	void testread_data_by_line() throws NoCategoryFoundException, NoItemIDException, NoItemNameFoundException, FileNotFoundException {
		FileManager f = new FileManager();
		
		f.read_data_by_line("csvTestFiles/menu_correctItem.csv" );
		
	}
	
	@Test
	void testcreate_foodItem_fromCSV() throws NoCategoryFoundException, NoItemIDException, NoItemNameFoundException {
		FileManager f = new FileManager();
		FoodItem item = f.create_foodItem_fromCSV("HOT1;Americano;2.70;\"Espresso with hot water\"");
		assertEquals("HOT1",item.getItemID());
	}
	
	
}
