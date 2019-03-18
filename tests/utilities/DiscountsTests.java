package utilities;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import model.Discount;
import model.FoodItem;
import model.Menu;

class DiscountsTests {

	Discount d = new Discount();

	@Test
	void test() {
		
	}
	@Test
	void test_addItemToDiscount() {
		Menu m = new Menu(true);
		
		d.setDiscountId("COMBO01");
		d.setOffer_name("Kids Combo");
		d.setDiscount_percentage(5);
		
		assertTrue(d.addItemToDiscount(m.getFoodItemById("HOT1")));
		assertTrue(d.addItemToDiscount(m.getFoodItemById("HOT2")));
		
		ArrayList<FoodItem> discountsList = d.getItem_list();
		
		assertTrue(d.containsItemID("HOT1"));
	}
}
