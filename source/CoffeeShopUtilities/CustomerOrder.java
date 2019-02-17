package CoffeeShopUtilities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

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
	//TODO
	//Implement the methods defined in class diagram

}
