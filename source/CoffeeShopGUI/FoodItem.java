package CoffeeShopGUI;

public class FoodItem {

	private String itemID;
	private String name;
	private double price;
	private String description;
	private FoodCategory category;
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
}