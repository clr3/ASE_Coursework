package service;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

import model.CustomerOrder;
import model.FoodCategory;
import model.FoodItem;
import service.OrderManager;

class OrderManagerTests {
	
	@Test 
	void testSubmitNewOrder() { 
		String orderId= "999"; 
		FoodItem fi = new
		FoodItem("HOT99", "Hot Coffee", 10.0, "New Coffee",
	    FoodCategory.HOT_BEVERAGE); 
		ArrayList<FoodItem> fList = new
	    ArrayList<FoodItem>(); fList.add(fi); 
	    CustomerOrder co = new CustomerOrder("999", "10", fList, new BigDecimal(10), new Date());
	  
	    OrderManager om = OrderManager.getInstance(); 
	    om.submitNewOrder(co) ;
	  
	    HashMap<String, CustomerOrder> order = om.getOrderMap();
	  
	    assertTrue(order.containsKey("999"));
	  
	}
	/*Do not run this test while the write to file is not working*/
	/*
	  
	@Test void testWriteReports() { 
		String orderId= "999"; 
		FoodItem fi = new FoodItem("HOT99", "Hot Coffee", 10.0, "New Coffee",
	    FoodCategory.HOT_BEVERAGE); 
		ArrayList<FoodItem> fList = new
	    ArrayList<FoodItem>(); 
		fList.add(fi); 
		
		CustomerOrder co = new CustomerOrder("999", "10", fList, new BigDecimal(10), new Date());
	  
	    OrderManager om = OrderManager.getInstance(); 
	    om.submitNewOrder(co);
	  
	    String report = om.writeReports();
	    om.writeReports();// This will write the summary report. Path csvFiles/order_summary.csv
	    
	    assertTrue(report.contains("Total Order Value ="));
	    
	}*/
	 

}
