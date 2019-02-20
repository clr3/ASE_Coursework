package CoffeeShopUtilities;

/**
 * @Edits Cristina Rivera
 * */





import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.EnumMap;
import java.util.HashMap;

public class CustomerOrder {
		
	private String orderId;
	private String customerId;
	private ArrayList<FoodItem> orderItems;
	private BigDecimal finalBillAmount; // Bill amount after discount
	private BigDecimal totalBillAmount; // Bill amount before discount
	private Date timestamp;
	
	private String foodItem = null;
	
	public CustomerOrder(String orderId, String customerId, ArrayList<FoodItem> orderItems, BigDecimal finalBillAmount, Date timestamp) {
		super();
		this.orderId = orderId;
		this.customerId = customerId;
		this.orderItems = orderItems;
		this.timestamp = timestamp;
		this.finalBillAmount = finalBillAmount;
	}
	
	public CustomerOrder(String orderId, String customerId) {
		this.orderId = orderId;
		this.customerId = customerId;
		
		// Create an empty ArrayList
		this.orderItems = new ArrayList<FoodItem>();
		//Create time stamp for today
		this.timestamp = new Date();
		this.finalBillAmount = getTotalBill();
	}
	
	public CustomerOrder() {
		// Create an empty ArrayList
		this.orderItems = new ArrayList<FoodItem>();
		//Create time stamp for today
		this.timestamp = new Date();
		this.finalBillAmount = getTotalBill();
	}

	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public void setOrderItems(ArrayList<FoodItem> orderItems) {
		this.orderItems = orderItems;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public ArrayList<FoodItem> getOrderItems() {
		return orderItems;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	
	public BigDecimal getFinalBillAmount() {
		return finalBillAmount;
	}

	public void setFinalBillAmount(BigDecimal finalBillAmount) {
		this.finalBillAmount = finalBillAmount;
	}
	public void setFinalBillAmount() {
		this.finalBillAmount = getTotalBill();
	}
	/**
	 * @Author Sethu 
	 * This method returns the FoodItem for the given foodItemId
	 * 
	 * 
	 * @Params String foodItemId
	 * @Returns void
	 * 
	 * */
	FoodItem getFoodItem(String foodItemId, Menu menu) {
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
	 * @Author Cristina
	 * Add a single Item to the order */
	public void addItem(FoodItem foodID){
		orderItems.add(foodID);
	}
	/**
	 * @Author Cristina 
	 * Remove an item from the order*/
	public void removeItem(FoodItem foodID) {
		if(!orderItems.isEmpty()) {
			//Remove first instance from the list
			if(orderItems.contains(foodID)) { 	orderItems.remove(foodID);}
		}
	}
	
	/**
	 * @Author Cristina 
	 * Calculates the total cost from the food Items in the order
	 * @Return BigDecimal with the total of all items
	 */
	public BigDecimal getTotalBill() {
		if(!orderItems.isEmpty()) {
			double total = 0;
			//add the prices from the items 
			for(FoodItem f: orderItems) {
				total += f.getPrice();
			}
			return new BigDecimal(total);
		}else {return new BigDecimal(0);}
	}
	
	/**
	 * Empty the array list if the order it reset 
	 * */
	public void clearOrder() {
		orderItems.clear();
	}

}
