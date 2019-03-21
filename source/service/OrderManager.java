package service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Iterator;

import model.CustomerOrder;
import model.FoodCategory;
import model.FoodItem;
import model.Menu;
import queueExceptions.QueueEmptyException;
import service.queue.DeliveryQueue;
import service.queue.OrderQueue;
import utilities.Logger;
import views.StaffGUI;

/**@Edits Cristina Rivera
 * Singleton Pattern in OrderManager 
 * Synchronized 
 * */
/**
 * Creates a queue from the past orders stored in the csv file.
 * 
 * Allows to add and remove items from the queue.
 * 
 * NO GUIs should be connected to this class
 * 
 * */
public class OrderManager {
	
	int newCustomerCount = 100;
	int newCustomerOrderCount = 100;

	
	private  HashMap<String ,CustomerOrder> orderMap = new HashMap<String ,CustomerOrder>(); 
	
	
	private FileManager fm = new FileManager();
	Menu menu = Menu.getInstance();
	//private ArrayList<CustomerOrder> ordersForStaffDisplay = new ArrayList<CustomerOrder>();
	//public StaffGUI staffGui;
	
	private DeliveryQueue deliveryQ = new DeliveryQueue(); //Holds the CustomerOrders that are being processed
	private  HashMap<ServingStaff ,CustomerOrder> servingStaffMap = new HashMap<ServingStaff ,CustomerOrder>(); 

	
	//Using the OrderQueue Instead of the HASHMAP of customerOrders
	private static OrderManager om = new OrderManager();
	private OrderQueue orderQ = new OrderQueue();	//Holds Orders as they are added
	
	private HashMap<FoodCategory, Integer> processTimeMap = new HashMap<FoodCategory, Integer>();

	private OrderManager() {
		// The order history files are loaded during the creation of Order Manager
		//THe FileManager is in charge of reading the files and returning the according data structure
		
			this.orderMap = fm.buildCustomerOrdersFromOrderHistory(menu);
			preparePastOrdersList();
		
	}
	
	public static OrderManager getInstance() {
		
		return om;
	}
	/**
	 * Adds the past orders to the list the staff will be able to view
	 * */
	private synchronized void preparePastOrdersList() {
		
		for(CustomerOrder o: orderMap.values()) {
			orderQ.enqueue(o); // Existing orders are added to Queue
			
		}
	}
/**
 * @Return CustomerOrder with a new CustomerID and orderID.
 * 
 * This can be used with the menu to add and remove items before adding to the queue
 * */
	public CustomerOrder createNewOrder() {
		CustomerOrder o = new CustomerOrder();
		o.setCustomerId("NEW"+newCustomerCount++); // New Customer ID created
		o.setOrderId("ID"+newCustomerOrderCount++); //create an order id
		return o;
	}

	/**
	 * 
	 * This method adds a new CustomerOrder to the end of the queue
	 * 
	 * @Params String orderId, CustomerOrder cusOrder
	 * @Returns void
	 * 
	 * */
	public synchronized void submitNewOrder(CustomerOrder customerOrder) {
		
		CustomerOrder cusOrder = customerOrder;
		orderMap.put(cusOrder.getOrderId(), cusOrder);
		orderQ.enqueue(cusOrder ); // New orders are added to Queuue
		//this.staffGui.reRenderQueue();
	}
	

	/**
	 * 
	 * This method generates a summary of the Order Report, including historical and new orders.
	 * The report is written to a csv file "order_summary.csv"
	 * 
	 * 
	 * @Params null
	 * @Returns String with the report for testing
	 * 
	 * */
	public synchronized String writeReports() {
		String report = generateReports();
		FileManager fm = new FileManager();
		fm.writeToFile("order_summary.csv", report);
		return report;
	}
	
	/**
	 * 
	 * This method generates a summary of the Order Report, including historical and new orders.
	 * The report is written to a csv file "order_summary.csv"
	 * 
	 * 
	 * @Params null
	 * @Returns String
	 * 
	 * */
	private String generateReports() {
		
		StringBuilder sb = new StringBuilder("Food Category, Item Id, Item Name, Order Count\n");
		//StringBuilder sb = new StringBuilder("Item Id, Item Name, Order Count\n");
		
		
		BigDecimal totalOrderValue = new BigDecimal(0);
		EnumMap<FoodCategory ,HashMap<String , FoodItem>> menuEnumMap = menu.getMenu();
		Collection<HashMap<String , FoodItem>> menuMapList = menuEnumMap.values();
		
		HashMap<FoodItem, Integer> foodItemCountMap =  getFoodItemCount();
		
		for (HashMap<String , FoodItem> menuMap : menuMapList) {
			
			Collection<FoodItem> foodItems = menuMap.values();
			
			for (FoodItem fItem : foodItems) {
				Integer foodItemCount = 0;
				if (foodItemCountMap.containsKey(fItem)) {
					foodItemCount = foodItemCountMap.get(fItem);
				}
				sb.append(fItem.getCategory().name()+","+fItem.getItemID()+","+fItem.getName()+","+foodItemCount+"\n");
				//sb.append(fItem.getItemID()+","+fItem.getName()+","+foodItemCount+"\n");
				totalOrderValue = totalOrderValue.add(BigDecimal.valueOf(fItem.getPrice()));
				
			}
			
		}
		// add new customer oder price to total from historical order
		Collection<CustomerOrder> custOrders = orderMap.values();
		for (CustomerOrder cOrder: custOrders) {
			totalOrderValue = totalOrderValue.add(cOrder.getFinalBillAmount());
		}
		totalOrderValue = totalOrderValue.setScale(2, RoundingMode.CEILING);
		sb.append("\n\nTotal Order Value = "+totalOrderValue);
		return sb.toString();	
	}
	

	
	/**
	 * 
	 * This method returns the total count of FoodItems ordered by customers
	 * 
	 * @Returns HashMap<FoodItem, Integer>
	 * 
	 * */
	private synchronized HashMap<FoodItem, Integer> getFoodItemCount () {
		
		HashMap<FoodItem, Integer> foodItemCountMap = new HashMap<FoodItem, Integer>();
		
		Collection<CustomerOrder> custOrders = orderMap.values();
		for (CustomerOrder cOrder: custOrders) {
			ArrayList<FoodItem> foodItems = cOrder.getOrderItems();
			for (FoodItem fItem: foodItems) {
				if (foodItemCountMap.containsKey(fItem)) {
					Integer counter = foodItemCountMap.get(fItem);
					counter++;
					foodItemCountMap.replace(fItem, counter);		
				} else {
					foodItemCountMap.put(fItem, 1);	
				}
				
			}
		}
		return foodItemCountMap;
	}
	
	/**
	 * 
	 * Getter method for returning the orderMap
	 * 
	 * */
	public synchronized HashMap<String, CustomerOrder> getOrderMap() {
		return orderMap;
	}
	/**@throws QueueEmptyException 
	 * @Edits Cristina Rivera
	 * Created the FileManager at the beginning to manage orders to/from the file.
	 * 
	 * Remove Next CutomerOrder From Queue
	 * Add the order to delivery queue
	 * Return the next order to be written in the file
	 * 
	 * */
	public synchronized CustomerOrder acceptNextOrder() throws QueueEmptyException {
		Logger.getInstance().log("Next order processed");
		System.out.println("Next order processed");
		
		CustomerOrder o = fetchOrderFromQueue(); 	//De-queue next CustomreOrder
		addProcessedOrderToDeliveryQueue(o);
		
		orderMap.remove(o.getCustomerId());
		
		return o;
	}
	
	
	/**
	 * 
	 * Get all Customer Orders on Order Queue
	 * 
	 * */
	public synchronized ArrayList<CustomerOrder> getAllOrdersOnOrderQueue(){
		return  orderQ.viewAllOrders();
	}
	
	/**
	 * 
	 * Returns the  Customer Order from Queue
	 * @throws QueueEmptyException 
	 * 
	 * */
	private synchronized CustomerOrder fetchOrderFromQueue() throws QueueEmptyException{
		CustomerOrder coa = orderQ.dequeue();
		//this.staffGui.reRenderQueue();
		return coa;
	}
	
/**NON of this Delivery Queue is necessary*/
	/**
	 * 
	 * Adds a processed order to Delivery Queue.
	 * 
	 * */
	public void addProcessedOrderToDeliveryQueue(CustomerOrder order){
		deliveryQ.enqueue(order);
	}
	
	/**
	 * 
	 * Get all Customer Orders on Delivery Queue
	 * 
	 * */
	public synchronized ArrayList<CustomerOrder> getAllOrdersOnDeliveryQueue(){
		ArrayList<CustomerOrder> coaList = deliveryQ.viewAllOrders();
		return coaList;
	}
	
	/**
	 * 
	 * Get all Customer Orders currently processed by staff
	 * 
	 * */
	public synchronized HashMap<ServingStaff, CustomerOrder> getOrdersUnderProcessingByStaff(){
		return servingStaffMap;
	}
	
	/**
	 * 
	 * Update current Orders processing by staff
	 * 
	 * 
	 * */
	public synchronized void updateOrdersUnderProcessingByStaff(ServingStaff staffName, CustomerOrder order){
		servingStaffMap.put(staffName, order);
		//ordersForStaffDisplay.add(order);
	}
	/**
	 * 
	 * Update current Orders processing by staff
	 * 
	 * An Hashmap will be given to do the display
	 * 
	 * */
	public synchronized void updateRemoveOrdersUnderProcessingByStaff(ServingStaff staffName, CustomerOrder order){
		servingStaffMap.remove(staffName, order);
		//ordersForStaffDisplay.add(order);
	}
	/**
	 * @throws QueueEmptyException 
	 * 
	 * */
	
	public synchronized CustomerOrder processNextOrderForDelivery() throws QueueEmptyException {
		CustomerOrder o = deliveryQ.dequeue();
		return o;
	}
	public synchronized void staffOrderCompleted(String staffName,CustomerOrder o) {
		servingStaffMap.remove(staffName, o);
		//ordersForStaffDisplay.remove(o);
	}
	private void setDefaultProcessTime() {
		processTimeMap.put(FoodCategory.HOT_BEVERAGE, 3000);
		processTimeMap.put(FoodCategory.COLD_BEVERAGE, 3000);
		processTimeMap.put(FoodCategory.BAKE, 5000);
		processTimeMap.put(FoodCategory.SANDWICH, 8000);
		processTimeMap.put(FoodCategory.BEVERAGE, 4000);
		processTimeMap.put(FoodCategory.MEALS, 9000);
	}
	
	public void setProcessTime(TimerGUI timerPage, FoodCategory fc) {
		String timeStr = timerPage.processTimeInputMapList.get(fc).getText();
		int time = Integer.parseInt(timeStr);
		int timeInMilliSec = time * 1000;
		//FoodCategory fcType = FoodCategory.valueOf(fc);
		if(processTimeMap.containsKey(fc)) {
			processTimeMap.replace(fc, timeInMilliSec);
		}
	}
	
	public HashMap<FoodCategory, Integer> getAllProcessTime() {
		return processTimeMap;
	}


}
