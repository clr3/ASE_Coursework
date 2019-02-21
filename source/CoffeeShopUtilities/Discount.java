/**
* Discount data class for CoffeeShop App
*/
package CoffeeShopUtilities;

import java.util.ArrayList;

/**
* Discount data class for CoffeeShop App
*
* @author  Arthidevi Balavignesh
*/
public class Discount {
	private String discountId;
	private String offer_name;
	private ArrayList<FoodItem>  item_list = new ArrayList <FoodItem>();
	private int discount_percentage ;
	
	public Discount() {
		
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
		this.item_list = item_list;
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
	/**
	 * @Param List of items from the customer order
	 * 
	 * Compares the order items to this discount. If it's possible
	 * */
	public boolean orderContainsItemsForDiscount(ArrayList<FoodItem> orderItems) {
		
		return orderItems.containsAll(item_list);
		
		
	}
	
}
