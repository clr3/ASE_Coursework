package service.queues;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import CoffeeShopModel.CustomerOrder;
import CoffeeShopModel.FoodCategory;
import CoffeeShopModel.FoodItem;
import queueExceptions.QueueEmptyException;
import service.queue.OrderQueue;

class OrderQueueTest {

	OrderQueue oq;
	static CustomerOrder order;
	static CustomerOrder order1;
	static List<CustomerOrder> orders;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		order = new CustomerOrder();
		order.setCustomerId("CUS100");
		FoodItem fItem = new FoodItem("HOT001","Americano", 2.30, "Delicious Dark Coffee", FoodCategory.HOT_BEVERAGE);
		order.addItem(fItem);
		
		order1 = new CustomerOrder();
		order1.setCustomerId("CUS101");
		FoodItem fItem1 = new FoodItem("HOT002","Latte", 2.50, "Latte", FoodCategory.HOT_BEVERAGE);
		order1.addItem(fItem1);
		
		orders = new ArrayList<CustomerOrder>();
		orders.add(order);
		orders.add(order1);
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		OrderQueue oq = new OrderQueue();
	}

	@AfterEach
	void tearDown() throws Exception {
		oq.deleteOrdersFromQueue();
	}

	@Test
	void testEnqueue() {
		OrderQueue oq = new OrderQueue();
		oq.enqueue(order);
		assertEquals(1, oq.countOrdersInQueue());
	}

	@Test
	void testEnqueueAll() {
		oq.enqueueAll(orders);
		assertEquals(2, oq.countOrdersInQueue());
	}

	@Test
	void testDequeue() {
		oq.enqueue(order);
		CustomerOrder order = null;
		try {
			order = oq.dequeue();
		} catch (QueueEmptyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals("CUS100", order.getCustomerId().toString());
	}

}
