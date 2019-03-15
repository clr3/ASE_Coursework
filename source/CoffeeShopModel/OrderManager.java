package CoffeeShopModel;
import java.awt.List;
/**
 * @Author Sethu Lekshmy<sl1984@hw.ac.uk>
 * 
 * @CommentsByCristy
 * 
 * in order to manage the orders, I think we need a way to createNeOrders (with an order Number,
 * maybe pass it along from the GUI to submitNewOrder(String orderId, CustomerOrder cusOrder) )
 * 
 *
 * 
 * also I think we need a method to write the order to CSV and remove from the file
 * */
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Queue;

public class OrderManager {
	
	private  HashMap<String ,CustomerOrder> orderMap = new HashMap<String ,CustomerOrder>(); 
	private FileManager fm = new FileManager();
	Menu menu;
	private ArrayList<CustomerOrder> ordersForDisplay = new ArrayList<CustomerOrder>();

	
	public OrderManager() {
		// The order history files are loaded during the creation of Order Manager
		
		menu = new Menu();
		menu.importMenuData();
		
		//Moved Method to File Manager Class
		//this.orderMap = fm.buildCustomerOrdersFromOrderHistory(menu);
		
		// Crsty - After uncommenting the above line, delete the below code (try catch block), and
		// the methods buildCustomerOrdersFromOrderHistory and getFoodItem
		// Run the OrderManagerUnit Tests and verify that order_summary.csv file is created
		try {
			ArrayList<String> orderHistories = fm.readOrderHistory();
			this.orderMap = buildCustomerOrdersFromOrderHistory(orderHistories);
		} catch (FileNotFoundException e) {
			System.out.println ("OrderManager failed to load the order history. File not found error!");
		}
		
	}
	
public  HashMap <String, CustomerOrder> buildCustomerOrdersFromOrderHistory(ArrayList<String> orderHistories) throws FileNotFoundException{
		
		HashMap <String, CustomerOrder> orderMap = new HashMap <String, CustomerOrder>();
		
		for (String order: orderHistories){
			String[] item = order.split(";");
			
			CustomerOrder custOrder;
			
			FoodItem fItem = getFoodItem(item[2]);
			
			ArrayList<FoodItem> fItemList = new ArrayList<FoodItem>();
			fItemList.add(fItem);
			
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date date = null;
			try {
				date = format.parse(item[3]);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//build the CustomerOrder if its a new order. If order exists, then add/append the order item to existing CustomerOrder
			if (orderMap.containsKey(item[0])) {
				custOrder = orderMap.get(item[0]);
				ArrayList<FoodItem> currentFoodItemList = custOrder.getOrderItems();
				currentFoodItemList.add(fItem);
				custOrder.setOrderItems(currentFoodItemList);
			} else {
				custOrder = new CustomerOrder(item[0], item[1], fItemList, new BigDecimal(0), date);
				orderMap.put(item[0], custOrder);
			}
			
		}
		return orderMap;
	}

	private FoodItem getFoodItem (String foodItemId) {
		FoodItem fItem = null;
		EnumMap<FoodCategory ,HashMap<String , FoodItem>> menuEnumMap = menu.getMenu();
		Collection<HashMap<String , FoodItem>> menuMapList = menuEnumMap.values();
		
		for (HashMap<String , FoodItem> menuMap : menuMapList) {
			if (menuMap.containsKey(foodItemId)) {
				fItem = menuMap.get(foodItemId);
			}
			
		}
		return fItem;
	}
	

	
	/**
	 * 
	 * This method adds a new Customer adder to the existing Order Map
	 * 
	 * 
	 * @Params String orderId, CustomerOrder cusOrder
	 * @Returns void
	 * 
	 * */
	public void submitNewOrder(String orderId, CustomerOrder cusOrder) {
		ordersForDisplay.add(cusOrder);
		orderMap.put(orderId, cusOrder);
		
	}
	
	/**
	 * 
	 * This method adds a new Customer adder to the existing Order Map
	 * 
	 * 
	 * @Params String orderId, CustomerOrder cusOrder
	 * @Returns void
	 * 
	 * */
	public void addNewOrder(HashMap<String, Integer> cart, double price) {
		
		ArrayList<FoodItem> foodItemList = new ArrayList<FoodItem>();
		for (String foodItemId : cart.keySet()) {
			for (int i = 0; i < cart.get(foodItemId);i++) {
			foodItemList.add(getFoodItem(foodItemId));
			}
		}
		
		CustomerOrder co = new CustomerOrder("1000", "5000", foodItemList, new BigDecimal(price), new Date());
		orderMap.put("1000", co);
		
	}
	

	/**
	 * 
	 * This method generates a summary of the Order Report, including historical and new orders.
	 * The report is written to a csv file "order_summary.csv"
	 * 
	 * 
	 * @Params null
	 * @Returns void
	 * 
	 * */
	public void writeReports() {
		String report = generateReports();
		FileManager fm = new FileManager();
		fm.writeToFile("order_summary.csv", report);
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
	public String generateReports() {
		
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
	 * 
	 * @Params 
	 * @Returns HashMap<FoodItem, Integer>
	 * 
	 * */
	private HashMap<FoodItem, Integer> getFoodItemCount () {
		
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
	 * Getter method for returning the orderMao
	 * */
	public HashMap<String, CustomerOrder> getOrderMap() {
		return orderMap;
	}
	/**@Edits Cristina Rivera
	 * Created the FileManager at the beginning to manage orders to/from the file.
	 * Write the order into CSV File
	 * Remove CutomerOrder From HashMap
	 * 
	 * */
	public CustomerOrder acceptNextOrder() {
	
		Iterator<CustomerOrder> ite = ordersForDisplay.iterator();
		CustomerOrder o = ite.next();
		
		ordersForDisplay.remove(o);
		fm.write_Order_toCSV(o);
		orderMap.remove(o.getCustomerId());
		return o;
	}
	public ArrayList<CustomerOrder> getOrdersForDisplay(){
		return ordersForDisplay;
	}
	
	
}
