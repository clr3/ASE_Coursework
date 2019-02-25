/**
* Menu class for Coffee Shop
*/
package CoffeeShopUtilities;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map.Entry;

import discountExceptions.NoDiscountFoodItemsException;
import discountExceptions.NoDiscountIdException;
import discountExceptions.NoDiscountNameException;
import discountExceptions.NoDiscountPercentageException;
import foodItemExceptions.NoCategoryFoundException;
import foodItemExceptions.NoItemIDException;
import foodItemExceptions.NoItemNameFoundException;
import foodItemExceptions.NoPriceFoundException;

/**
 * Menu class for Coffee Shop, Stores and processes menu data
 *
 * @author Arthidevi Balavignesh
 * @version 1.0
 */
public class Menu {

	private EnumMap<FoodCategory, HashMap<String, FoodItem>> menu = new EnumMap<FoodCategory, HashMap<String, FoodItem>>(
			FoodCategory.class);
	private ArrayList<Discount> discounts = new ArrayList<Discount>();

	private int enum_no=5;
	
    /** 
     * Constructor for Menu Class
     * 
     */
	public Menu() {}


	/**
	 * Constructor for Menu Class
	 * 
	 */
	public void importMenuData() {

		FileManager fm = new FileManager();
		HashMap<String , FoodItem> items = new HashMap<String , FoodItem>();
	
		try {
			
			items = fm.create_menu();
	
			//Iterate the menu items 
			for(HashMap.Entry<String , FoodItem> item: items.entrySet()) {	
						
				addFoodItems(item.getValue().getCategory(), item.getValue());		
			}
			
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		createDiscount();
		addDiscountToMenu();


		createDiscount();
		addDiscountToMenu();		
	}

	/**
	 * Creates discount objects from csv
	 * 
	 *
	private void createDiscount() {
		
		FileManager fm = new FileManager();
		ArrayList<String> discounts_csv_list;
		try {
			//discounts_csv_list = fm.read_data_by_line("csvFiles/discounts.csv");
			
			for (int counter = 0; counter < discounts_csv_list.size(); counter++) {
				Discount discountObj;
				discountObj = getDiscountObj(discounts_csv_list.get(counter));
				discounts.add(discountObj);
			}
		} catch (FileNotFoundException | NoDiscountIdException | NoDiscountNameException | NoDiscountPercentageException
				| NoDiscountFoodItemsException e) {
			e.printStackTrace();
		}
		
	}
*/
	private void createDiscount() {
		
		FileManager fm = new FileManager();
		try {
			discounts = fm.createDiscountsFromFile();
		} catch (FileNotFoundException e) {
			System.out.println("Discounts File Nor Found");
			e.printStackTrace();
		}
		
	}
/**	*
	public Discount getDiscountObj(String discountLine) throws NoDiscountIdException, NoDiscountNameException,
			NoDiscountPercentageException, NoDiscountFoodItemsException {
		String[] discountItem = new String[4];
		if (discountLine.contains(",")) {
			discountItem = discountLine.split(",");
		}
		if (discountItem[0].isEmpty())
			throw new NoDiscountIdException();
		if (discountItem[1].isEmpty())
			throw new NoDiscountNameException();
		if (discountItem[2].isEmpty())
			throw new NoDiscountPercentageException();
		if (discountItem[3].isEmpty())
			throw new NoDiscountFoodItemsException();

		Discount discount_obj = new Discount();
		discount_obj.setDiscountId(discountItem[0]);
		discount_obj.setOffer_name(discountItem[1]);
		String[] foodItems = discountItem[3].split(":");
		for (int i = 0; i < foodItems.length; i++) {
			discount_obj.getItem_list().add(foodItems[i]);
		}
		try {
			discount_obj.setDiscount_percentage(Integer.parseInt(discountItem[2]));
		} catch (NumberFormatException e) {
			throw new NoDiscountPercentageException();
		}
		discounts.add(discount_obj);
		return discount_obj;
	}
*/
	public void addDiscountToMenu() {
		HashMap<String, FoodItem> discountsList = new HashMap<String, FoodItem>();
		for (Discount discount : discounts) {
			FoodItem fi = new FoodItem();
			fi.setName(discount.getOffer_name());
			Double comboPrice = getComboPrice(discount);
			fi.setPrice(comboPrice);
			fi.setItemID(discount.getDiscountId());
			fi.setDescription(getComboDetails(discount));
			fi.setCategory(FoodCategory.COMBO);
			discountsList.put(discount.getDiscountId(), fi);
		}
		menu.put(FoodCategory.COMBO, discountsList);
	}

	private Double getComboPrice(Discount discount) {
		Double totalPrice = 0d;
		for (FoodItem foodItemId : discount.getItem_list()) {
			FoodItem fi = foodItemId;
			if (fi != null) {
				totalPrice += fi.getPrice();
			}
		}
		Double discountPercent = (double) (discount.getDiscount_percentage() / 100d);
		Double discountRate = totalPrice * discountPercent;
		return totalPrice - discountRate;
	}

	private String getComboDetails(Discount discount) {
		ArrayList<String> comboList = new ArrayList<String>();

		for (FoodItem foodItemId : discount.getItem_list()) {
			FoodItem fi = foodItemId;
			if (fi != null) {
				comboList.add(fi.getName());
			}
		}
		String comboDetails = "( " + String.join(" + ", comboList) + " )";
		return comboDetails;
	}

	/**
	 * Return discounts
	 * 
	 * @return ArrayList<Discount>
	 */
	public ArrayList<Discount> getDiscounts() {
		return discounts;
	}

	/**
	 * Set discounts
	 * 
	 * @param ArrayList<Discount> discounts
	 */
	public void setDiscounts(ArrayList<Discount> discounts) {
		this.discounts = discounts;
	}

	/**
	 * Get discounts
	 * 
	 * @return EnumMap<FoodCategory, HashMap<String , FoodItem>> menu
	 */
	public EnumMap<FoodCategory, HashMap<String, FoodItem>> getMenu() {
		return menu;
	}

	/**
	 * Set discounts
	 * 
	 * @param EnumMap<FoodCategory, HashMap<String , FoodItem>> menu
	 */
	public void setMenu(EnumMap<FoodCategory, HashMap<String, FoodItem>> menu) {
		this.menu = menu;
	}

	/**
	 * Add FoodItem object to the menu property
	 * 
	 * @param FoodCategory category
	 * @param FoodItem     food
	 */
	public void addFoodItems(FoodCategory category, FoodItem food) {
		if (menu.containsKey(category)) {
			HashMap<String, FoodItem> f1 = menu.get(category);
			f1.put(food.getItemID(), food);
		} else {
			HashMap<String, FoodItem> food_list = new HashMap<String, FoodItem>();
			food_list.put(food.getItemID(), food);
			menu.put(category, food_list);
		}
	}

	/**
	 * Return the fooditems list by category
	 * 
	 * @return HashMap<String , FoodItem>
	 */
	public HashMap<String, FoodItem> getFoodItemsByCategory(String categoryName) {
		if (menu.containsKey(FoodCategory.valueOf(categoryName))) {
			return menu.get(FoodCategory.valueOf(categoryName));
		}
		return null;
	}

	public FoodItem getFoodItemById(String itemId) {
		for (Entry<FoodCategory, HashMap<String, FoodItem>> entry : menu.entrySet()) {
			if (entry.getValue().containsKey(itemId)) {
				return entry.getValue().get(itemId);
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return "Menu [menu=" + menu + "]";
	}

}
