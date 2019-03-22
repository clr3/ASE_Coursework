/**
 * 
 */
package service;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.CustomerOrder;
import model.FoodCategory;
import model.FoodItem;
import service.OrderManager;
import service.StaffManager;
import service.queue.OrderQueue;

/**
 * @Author Sethu Lekshmy<sl1984@hw.ac.uk>
 * 
 * 
 * */
class StaffManagerTest {

	OrderQueue oq;
	OrderManager oMgr;
	StaffManager sMgr;
	static CustomerOrder order;
	static CustomerOrder order1;
	static CustomerOrder order2;
	static CustomerOrder order3;
	static CustomerOrder order4;
	static List<CustomerOrder> orders;
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		order = new CustomerOrder();
		order.setCustomerId("CUS100");
		order.setOrderId("100");
		FoodItem fItem = new FoodItem("HOT001","Americano", 2.30, "Delicious Dark Coffee", FoodCategory.HOT_BEVERAGE);
		order.addItem(fItem);
		
		order1 = new CustomerOrder();
		order1.setCustomerId("CUS101");
		order1.setOrderId("101");
		FoodItem fItem1 = new FoodItem("HOT002","Latte", 2.50, "Latte", FoodCategory.HOT_BEVERAGE);
		order1.addItem(fItem1);
		
		order2 = new CustomerOrder();
		order2.setCustomerId("CUS102");
		order2.setOrderId("102");
		FoodItem fItem2 = new FoodItem("HOT001","Americano", 2.30, "Delicious Dark Coffee", FoodCategory.HOT_BEVERAGE);
		order2.addItem(fItem2);
		
		order3 = new CustomerOrder();
		order3.setCustomerId("CUS103");
		order3.setOrderId("103");
		FoodItem fItem3 = new FoodItem("HOT002","Latte", 2.50, "Latte", FoodCategory.HOT_BEVERAGE);
		order3.addItem(fItem3);
		
		order4 = new CustomerOrder();
		order4.setCustomerId("CUS104");
		order4.setOrderId("104");
		FoodItem fItem4 = new FoodItem("HOT002","Latte", 2.50, "Latte", FoodCategory.HOT_BEVERAGE);
		order4.addItem(fItem4);
		
		
		
		orders = new ArrayList<CustomerOrder>();
		orders.add(order);
		orders.add(order1);
		orders.add(order2);
		orders.add(order3);
		orders.add(order4);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		oq = new OrderQueue();
		oMgr = new OrderManager();
		oMgr.staffGui = new StaffGUIMock(oMgr, sMgr, null);
		oMgr.submitNewOrder("100", order);
		oMgr.submitNewOrder("101", order1);
		oMgr.submitNewOrder("102", order2);
		oMgr.submitNewOrder("103", order3);
		oMgr.submitNewOrder("104", order4);
		sMgr = new StaffManager(oMgr);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
		oq.deleteOrdersFromQueue();
	}

	/**
	 * Test method for {@link service.StaffManager#manageServingStaff()}.
	 */
	@Test
	void testManageServingStaff() {
		oq.enqueueAll(orders);
		assertEquals(8, oMgr.getAllOrdersOnOrderQueue().size());
		sMgr.manageServingStaff();
		try {
			Thread.sleep(240100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(0, oMgr.getAllOrdersOnOrderQueue().size());
		
	}
	
	@SuppressWarnings("unused")
	private void callStubExternalService() {
		System.out.println("UI rendering");
	}

}
