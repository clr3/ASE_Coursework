package CoffeeShopUtilities;
/**
 * @Author Sethu Lekshmy<sl1984@hw.ac.uk>
 * 
 * */
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Locale;

public class OrderManager {
	
	private  HashMap<String ,CustomerOrder> orderMap = new HashMap<String ,CustomerOrder>(); 

	Menu menu;
	
	public OrderManager() {
		// The order history files are loaded during the creation of Order Manager
		FileManager fm = new FileManager();
		menu = new Menu();
		try {
			ArrayList<String> orderHistories = fm.readOrderHistory();
			//Moved Method to File Manager Class
			this.orderMap = fm.buildCustomerOrdersFromOrderHistory(orderHistories,menu);
		} catch (FileNotFoundException e) {
			System.out.println ("OrderManager failed to load the order history. File not found error!");
		}
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
		orderMap.put(orderId, cusOrder);
		
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
					sb.append(fItem.getCategory().name()+","+fItem.getItemID()+","+fItem.getName()+","+foodItemCount+"\n");
				}
				sb.append(fItem.getCategory().name()+","+fItem.getItemID()+","+fItem.getName()+","+foodItemCount+"\n");
				totalOrderValue = totalOrderValue.add(BigDecimal.valueOf(fItem.getPrice()));
			}
			
		}
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
	
}
