package CoffeeShopUtilitiesTests;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.*;

import org.junit.jupiter.api.Test;

import CoffeeShopUtilities.CustomerOrder;
import CoffeeShopUtilities.FoodCategory;
import CoffeeShopUtilities.FoodItem;

class customerOrderTests {

	@Test
	void test_addItem() {
		CustomerOrder o = new CustomerOrder();
		FoodItem f = new FoodItem("HOT001"," Americano", 2.30, "Delicious Dark Coffee", FoodCategory.HOT_BEVERAGE);
		o.addItem(f);

		assertEquals(new BigDecimal(2.30),o.getFinalBillAmount());
	}
	@Test
	void test_setOrderItems() {
		CustomerOrder o = new CustomerOrder();
		FoodItem f1 = new FoodItem("HOT001"," Americano", 2.30, "Delicious Dark Coffee", FoodCategory.HOT_BEVERAGE);
		FoodItem f2 = new FoodItem("HOT002"," Hot Chocolte", 3.00, "Delicious Dark Coffee", FoodCategory.HOT_BEVERAGE);
		
		ArrayList<FoodItem> list = new ArrayList<FoodItem>();
		list.add(f1);
		list.add(f2);
		
		
		o.setOrderItems(list);
		assertTrue(o.getOrderItems().contains(f1));
	}
	@Test
	void test_setOrderItems_repeats() {
		CustomerOrder o = new CustomerOrder();
		FoodItem f1 = new FoodItem("HOT001"," Americano", 2.30, "Delicious Dark Coffee", FoodCategory.HOT_BEVERAGE);
		FoodItem f2 = new FoodItem("HOT002"," Hot Chocolte", 3.00, "Delicious Dark Coffee", FoodCategory.HOT_BEVERAGE);

		ArrayList<FoodItem> list = new ArrayList<FoodItem>();
		list.add(f1);
		list.add(f2);
		list.add(f2);
		
		
		o.setOrderItems(list);
		assertTrue(o.getOrderItems().contains(f1));
		assertTrue(o.getOrderItems().contains(f2));

		assertTrue(o.getOrderItems().contains(f1));
	}
	@Test
	void test_removeItem() {
		CustomerOrder o = new CustomerOrder();
		FoodItem f1 = new FoodItem("HOT001"," Americano", 2.30, "Delicious Dark Coffee", FoodCategory.HOT_BEVERAGE);
		FoodItem f2 = new FoodItem("HOT002"," Hot Chocolte", 3.00, "Delicious Dark Coffee", FoodCategory.HOT_BEVERAGE);

		ArrayList<FoodItem> list = new ArrayList<FoodItem>();
		list.add(f1);
		list.add(f2);
		list.add(f2);
		
		
		o.setOrderItems(list);
		
		o.removeItem(f2);
		assertEquals(new BigDecimal(5.30),o.getFinalBillAmount());
	}
	
	@Test 
	void test_clearOrder() {
		CustomerOrder o = new CustomerOrder();
		FoodItem f1 = new FoodItem("HOT001"," Americano", 2.30, "Delicious Dark Coffee", FoodCategory.HOT_BEVERAGE);
		FoodItem f2 = new FoodItem("HOT002"," Hot Chocolte", 3.00, "Delicious Dark Coffee", FoodCategory.HOT_BEVERAGE);

		ArrayList<FoodItem> list = new ArrayList<FoodItem>();
		list.add(f1);
		list.add(f2);
		list.add(f2);
		
		
		o.setOrderItems(list);		
		o.removeItem(f2);
		assertEquals(new BigDecimal(5.30),o.getFinalBillAmount());
		
		o.clearOrder();
		assertTrue(o.getOrderItems().isEmpty());
	}

}
