package CoffeeShopUtilities;
/**
 * @Author Sethu Lekshmy<sl1984@hw.ac.uk>
 * 
 * */
import java.io.FileNotFoundException;
import java.math.BigDecimal;
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
			this.orderMap = buildCustomerOrdersFromOrderHistory(orderHistories);
		} catch (FileNotFoundException e) {
			System.out.println ("OrderManager failed to load the order history. File not found error!");
		}
	}
	
	/**
	 * 
	 * This method reads the order_history.csv file and builds CustomerOrder objects. 
	 * The method returns a HashMap with key as OrderId and value as CustomerOrder. Each CustomerOder object containing 
	 * one of more FoodItems.
	 * 
	 * @throws FileNotFoundException 
	 * 
	 * @Params String order_history csv file
	 * @Returns ArrayList<CustomerOrder>
	 * 
	 * */
	public  HashMap <String, CustomerOrder> buildCustomerOrdersFromOrderHistory(ArrayList<String> orderHistories) throws FileNotFoundException{
		
		HashMap <String, CustomerOrder> orderMap = new HashMap <String, CustomerOrder>();
		
		for (String order: orderHistories){
			String[] item = order.split(";");
			
			CustomerOrder custOrder;
			
			FoodItem fItem = getFoodItem(item[2]);
			
			ArrayList<FoodItem> fItemList = new ArrayList<FoodItem>();
			fItemList.add(fItem);
			
			DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-mm-dd", Locale.ENGLISH);
			Date date = (Date) format.parse(item[3]);
			
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

	
	/**
	 * 
	 * This method adds a new Customer adder to the existing Order Map
	 * 
	 * 
	 * @Params String orderId, CustomerOrder cusOrder
	 * @Returns void
	 * 
	 * */
	public void completeOrder(String orderId, CustomerOrder cusOrder) {
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
	private String generateReports() {
		
		StringBuilder sb = new StringBuilder("Food cataegory, Item Id, Item Name, Order Count");
		
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
					sb.append(fItem.getCategory().name()+","+fItem.getItemID()+","+fItem.getName()+","+foodItemCount);
				}
				totalOrderValue = totalOrderValue.add(BigDecimal.valueOf(fItem.getPrice()));
			}
			
		}
		
		return sb.toString();	
	}
	
	/**
	 * 
	 * This method returns the FoodItem for the given foodItemId
	 * 
	 * 
	 * @Params String foodItemId
	 * @Returns void
	 * 
	 * */
	private FoodItem getFoodItem (String foodItemId) {
		FoodItem fItem = null;
		EnumMap<FoodCategory ,HashMap<String , FoodItem>> menuEnumMap = menu.getMenu();
		Collection<HashMap<String , FoodItem>> menuMapList = menuEnumMap.values();
		for (HashMap<String , FoodItem> menuMap : menuMapList) {
			if (menuMap.containsKey(foodItemId)) {
				fItem = menuMap.get(foodItemId);
			} else {
				System.out.println ("No FoodItem found from Menu for the given food Item Id "+ foodItemId);
			}
			
		}
		return fItem;
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
