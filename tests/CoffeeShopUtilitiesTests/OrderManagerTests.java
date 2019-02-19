package CoffeeShopUtilitiesTests;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

import CoffeeShopUtilities.CustomerOrder;
import CoffeeShopUtilities.FoodCategory;
import CoffeeShopUtilities.FoodItem;
import CoffeeShopUtilities.OrderManager;

class OrderManagerTests {
	
	@Test 
	void testCompleteOrder() { 
		String orderId= "999"; FoodItem fi = new
		FoodItem("HOT99", "Hot Coffee", 10.0, "New Coffee",
	    FoodCategory.HOT_BEVERAGE); ArrayList<FoodItem> fList = new
	    ArrayList<FoodItem>(); fList.add(fi); CustomerOrder co = new CustomerOrder("999", "10", fList, new BigDecimal(10), new Date());
	  
	    OrderManager om = new OrderManager(); om.submitNewOrder(orderId, co) ;
	  
	    HashMap<String, CustomerOrder> order = om.getOrderMap();
	  
	    assertTrue(order.containsKey("999"));
	  
	}
	  
	@Test void testWriteReports() { 
		String orderId= "999"; 
		FoodItem fi = new FoodItem("HOT99", "Hot Coffee", 10.0, "New Coffee",
	    FoodCategory.HOT_BEVERAGE); 
		ArrayList<FoodItem> fList = new
	    ArrayList<FoodItem>(); 
		fList.add(fi); 
		
		CustomerOrder co = new CustomerOrder("999", "10", fList, new BigDecimal(10), new Date());
	  
	    OrderManager om = new OrderManager(); 
	    om.submitNewOrder(orderId, co);
	  
	    String report = om.generateReports();
	    om.writeReports();// This will write the summary report. Path csvFiles/order_summary.csv
	    
	    assertTrue(report.contains("Total Order Value = 34.09"));
	    
	}
	 

}
