package CoffeeShopUtilities;

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
	
	public CustomerOrder() {
		// TODO Auto-generated constructor stub
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
	public ArrayList<FoodItem> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(ArrayList<FoodItem> orderItems) {
		this.orderItems = orderItems;
	}
	public Date getTimestamp() {
		return timestamp;
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
	/*Will need a method to convert the String to Item using the menu
	 * */
	public void addItem(String foodID){
		this.foodItem = foodID;
	}
	public String getItem(){
		return this.foodItem;
	}
	/**
	 * @author Sethu Lekshmy<sl1984@hw.ac.uk>
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
				//System.out.println ("FoodItem found from Menu for the given food Item Id "+ foodItemId +" :FoodItem: "+fItem.getName());
			}
			
		}
		return fItem;
	}
	//TODO
	//Implement the methods defined in class diagram

}
