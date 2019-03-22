/**
* Discount data class for CoffeeShop App
*/
package model;

import java.util.*;

/**
* Discount data class for CoffeeShop App
*
* @author  Arthidevi Balavignesh
*/
public class Discount {
	private String discountId;
	private String offer_name;
	private ArrayList<FoodItem> item_list;
	private int discount_percentage ;
	
	
	public Discount() {
		discountId = "";
		offer_name = "";
		item_list = new ArrayList <FoodItem>();
		discount_percentage = 0 ;
		
	}
	
	public String getOffer_name() {
		return offer_name;
	}
	public void setOffer_name(String offer_name) {
		this.offer_name = offer_name;
	}
	public ArrayList<FoodItem> getItem_list() {
		return item_list;
	}
	public void setItem_list(ArrayList<FoodItem> item_list) {
		this.item_list.addAll(item_list);
	}
	public int getDiscount_percentage() {
		return discount_percentage;
	}
	public void setDiscount_percentage(int discount_percentage) {
		this.discount_percentage = discount_percentage;
	}
	public String getDiscountId() {
		return discountId;
	}
	public void setDiscountId(String discountId) {
		this.discountId = discountId;
	}
	public boolean containsItemID(String foodItemID) {
		
		if (!item_list.isEmpty()) { 
					
		for(int i = 0; i < item_list.size(); i++) {
			String foodinDiscount = item_list.get(i).getItemID();
			
			if(foodinDiscount.equals(foodItemID)) {
				return true;
				}
			}
				
		}
		
		return false;
	}
	/** Method for file manager to add item by item into the discount ArrayList*/
	public boolean addItemToDiscount(FoodItem i) {
		return item_list.add(i);
		
	}

	/**
	 * @Param List of items from the customer order
	 * 
	 * Compares the order items to this discount. If it's possible
	 * */
	public boolean orderContainsItemsForDiscount(ArrayList<FoodItem> orderItems) {
		
		return orderItems.containsAll(item_list);
		
	}
	
}
