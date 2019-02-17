package CoffeeShopUtilities;
/**
 * @Author Sethu Lekshmy<sl1984@hw.ac.uk>
 * 
 * */
import java.io.FileNotFoundException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Locale;

public class OrderManager {
	
	private  HashMap<String ,CustomerOrder> orderList = new HashMap<String ,CustomerOrder>(); 
	
	
	public OrderManager() {
		// The order history files are loaded during the creation of Order Manager
		FileManager fm = new FileManager();
		try {
			ArrayList<String> orderHistories = fm.readOrderHistory();
			this.orderList = buildCustomerOrdersFromOrderHistory(orderHistories);
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
		
		Menu menu = new Menu();
		
		for (String order: orderHistories){
			String[] item = order.split(";");
			
			CustomerOrder custOrder;
			
			FoodItem fItem = null;
			EnumMap<FoodCategory ,HashMap<String , FoodItem>> menuEnumMap = menu.getMenu();
			Collection<HashMap<String , FoodItem>> menuMapList = menuEnumMap.values();
			for (HashMap<String , FoodItem> menuMap : menuMapList) {
				if (menuMap.containsKey(item[2])) {
					fItem = menuMap.get(item[2]);
				}
				
			}
			
			ArrayList<FoodItem> fItemList = new ArrayList<FoodItem>();
			fItemList.add(fItem);
			
			DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-mm-dd", Locale.ENGLISH);
			Date date = (Date) format.parse(item[3]);
			
			//build the CustomerOrder if its a new order. If order exists, then add/append the order item to existing CustomerOrder
			if (orderMap.containsKey(item[0])) {
				custOrder = orderMap.get(item[0]);
				custOrder.setOrderItems(fItemList);		
			} else {
				custOrder = new CustomerOrder(item[0], item[1], fItemList, date);
				orderMap.put(item[0], custOrder);
			}
			
		}
		return orderMap;
	}


	public void completeOrder() {
		
	}
	
	public void addOrderToFile() {
		
	}

	public void generateReports() {
		
	}
}
