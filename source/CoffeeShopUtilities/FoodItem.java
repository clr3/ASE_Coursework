/**
* FoodItem data class for Coffee Shop
*/
package CoffeeShopUtilities;

import foodItemExceptions.NoCategoryFoundException;

/**
* FoodItem data class
*
* @author  Arthidevi Balavignesh
*/
public class FoodItem {

	private String itemID;
	private String name;
	private double price;
	private String description;
	private FoodCategory category;
	
	public FoodItem(String itemId,String name,double price,
			String description,FoodCategory category) {
		
		this.itemID = itemId;
		this.name = name;
		this.price = price;
		this.description = description;
		this.category = category;
		
	}
	public FoodItem() {	}

	public String getItemID() {
		return itemID;	
	}
	public void setItemID(String itemID) {
		this.itemID = itemID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public FoodCategory getCategory() {
		return category;
	}
	public void setCategory(FoodCategory category) {
		this.category = category;
	}
	
	@Override
	public String toString() {
		return "FoodItem [itemID=" + itemID + ", name=" + name + ", price=" + price + ", description=" + description
				+ ", category=" + category + "]";
	}
	
	/**Find The Food Item's Category 
	 * @ThrowsError if the category does not exist
	 * */
	public FoodCategory findCategoryFromID(String newItemID) throws NoCategoryFoundException {
		
		if(newItemID.toUpperCase().startsWith("HOT")) return FoodCategory.HOT_BEVERAGE;
		else if(newItemID.startsWith("COLD")) return FoodCategory.COLD_BEVERAGE;
		else if(newItemID.startsWith("SAND")) return FoodCategory.SANDWICH;
		else if(newItemID.startsWith("BAKE")) return FoodCategory.BAKE;
		else throw new NoCategoryFoundException();
	}

}