package model;

/**
 * @Edits Cristina Rivera
 * 
 * 
 * IMPORTANT
 * Set FinalBillAmount with help from the menu. 
 * Update the final price after discount have been calculated. 
 * 
 * @SugestionsByCristy
 * 	Have a discounts class that can work for both menu and customerOrder
 * 	
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
	private Integer priority = -1; // All orders are given a low priority by default
	
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
		this.orderId = "NoID";
		this.customerId = "NoID";
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
		this.orderItems.addAll(orderItems);
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
	/**
	 * @Return BigDecimal amount to be payed after discount was applied
	 * */
	public BigDecimal getFinalBillAmount() {
		return finalBillAmount;
	}
	/**
	 * Set the final price after discount has been applied
	 * */
	public void setFinalBillAmount(BigDecimal finalBillAmount) {
		this.finalBillAmount = finalBillAmount;
	}
	
	/**
	 * 
	 * Use this method to apply the discount..
	 * @Paramam Discount
	 * */
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

	 /** @Author Cristina
	 * Add a single Item to the order */

	public void addItem(FoodItem foodID){
		this.orderItems.add(foodID);
		setFinalBillAmount();
	}
	/**
	 * @Author Cristina 
	 * Remove an item from the order*/
	public void removeItem(FoodItem foodID) {
		if(!orderItems.isEmpty()) {
			//Remove first instance from the list
			if(orderItems.contains(foodID)) { 	this.orderItems.remove(foodID);}
			//recalculate total
			setFinalBillAmount();
		}
	}
	
	/**
	 * @Author Cristina 
	 * Calculates the total cost from the food Items in the order
	 * @Return BigDecimal with the total of all items
	 */
	private BigDecimal getTotalBill() {
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
	/**Returns the number time the foodItem is in the orders
	 * */
	public int totalItemsCount() {
		
		return orderItems.size();
	}
	/**
	 * Method will return and foat
	 * @returns double total price of single item in the order
	 * */
	public double totalItemPrice(FoodItem f) {
		int count = 0;
		if (this.orderItems.contains(f)) {
	    	for(int i = 0; i < orderItems.size(); i++ ) {
	    		FoodItem item = orderItems.get(i);
	    		if(item.equals(f)) {
	    			count += item.getPrice();
	    		}
	    	}
	    }
		
		return count;
	}
	/**
	 * Method will return an int
	 * @returns int total count of single item in the order
	 * */
	public int countItem(FoodItem f) {
		int count = 0;
		if (this.orderItems.contains(f)) {
	    	for(int i = 0; i < orderItems.size(); i++ ) {
	    		FoodItem item = orderItems.get(i);
	    		if(item.equals(f)) {
	    			count ++;
	    		}
	    	}
	    }
		
		return count;
	}
	
	/**
	 * 
	 * @returns FoodItem that was first added to the list
	 * */
	public FoodItem getFirstItem() {
	    	return orderItems.get(0);
	    
		
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	
	public void printOrderInfo() {
		System.out.println("OrderID: "+this.orderId +" \n");
		System.out.println("CustomerID: "+this.customerId +" \n");
		System.out.println("Items #: " +this.totalItemsCount() +" \n");

	}
}
