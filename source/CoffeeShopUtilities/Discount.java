package CoffeeShopUtilities;

import java.util.ArrayList;

public class Discount {
	private String discountId;
	private String offer_name;
	private ArrayList<String>  item_list = new ArrayList <String>();
	private int discount_percentage ;
	
	public String getOffer_name() {
		return offer_name;
	}
	public void setOffer_name(String offer_name) {
		this.offer_name = offer_name;
	}
	public ArrayList<String> getItem_list() {
		return item_list;
	}
	public void setItem_list(ArrayList<String> item_list) {
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
}
