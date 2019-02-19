/**
* Menu class for Coffee Shop
*/
package CoffeeShopUtilities;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;

import foodItemExceptions.NoCategoryFoundException;
import foodItemExceptions.NoItemIDException;
import foodItemExceptions.NoItemNameFoundException;
import foodItemExceptions.NoPriceFoundException;

/**
* Menu class for Coffee Shop, Stores and processes menu data
*
* @author  Arthidevi Balavignesh
* @version 1.0
*/
public class Menu {
	
	private  EnumMap<FoodCategory ,HashMap<String , FoodItem>> menu = new EnumMap<FoodCategory,HashMap<String , FoodItem>>(FoodCategory.class);    
	private ArrayList<Discount> discounts = new ArrayList<Discount>();
	
    /** 
     * Constructor for Menu Class
     * 
     */
	public Menu() {
		FileManager fm = new FileManager();
		try {
			ArrayList<String> menu_csv_list = fm.read_data_by_line("csvFiles/menu_coffeeShop.csv");
			//System.out.println(menu_csv_list);
			for (int counter = 0; counter < menu_csv_list.size(); counter++) { 		      
		         // System.out.println(menu_csv_list.get(counter)); 	
		        FoodItem  foodObj = fm.create_foodItem_fromCSV(menu_csv_list.get(counter));
		        addFoodItems(foodObj.getCategory(),foodObj);
		      } 
		} catch (FileNotFoundException | NoCategoryFoundException | NoItemIDException | NoItemNameFoundException | NoPriceFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
    /** 
     * Creates mock discount objects
     * 
     */
	private void createDiscount() {
		Discount discount_obj = new Discount();
		discount_obj.setDiscountId("COMBO01");
		discount_obj.setOffer_name("Kids Combo");
		discount_obj.getItem_list().add("HOT1");
		discount_obj.getItem_list().add("HOT2");
		discount_obj.setDiscount_percentage(5);
		discounts.add(discount_obj);
		
		Discount discount_obj1 = new Discount();
		discount_obj.setDiscountId("COMBO02");
		discount_obj1.setOffer_name("Saver Menu");
		discount_obj1.getItem_list().add("HOT2");
		discount_obj1.getItem_list().add("BAKE1");
		discount_obj1.setDiscount_percentage(10);
		discounts.add(discount_obj1);

		Discount discount_obj2 = new Discount();
		discount_obj.setDiscountId("COMBO03");
		discount_obj2.setOffer_name("BREAKFAST");
		discount_obj2.getItem_list().add("English Breakfast");
		discount_obj2.getItem_list().add("BAKE");
		discount_obj2.setDiscount_percentage(25);
		discounts.add(discount_obj2);
	}

    /** 
     * Return discounts 
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
	public EnumMap<FoodCategory, HashMap<String , FoodItem>> getMenu() {
		return menu;
	}

    /** 
     * Set discounts 
     *     
     * @param EnumMap<FoodCategory, HashMap<String , FoodItem>> menu
     */
	public void setMenu(EnumMap<FoodCategory, HashMap<String , FoodItem>> menu) {
		this.menu = menu;
	}

    /** 
     * Add FoodItem object to the menu property
     * @param FoodCategory category
     * @param FoodItem food
     */
	public void addFoodItems(FoodCategory category,FoodItem food) {
		if (menu.containsKey(category)) {
			HashMap<String , FoodItem> f1 = menu.get(category);
			f1.put(food.getItemID(),food);
		} else {
			HashMap<String , FoodItem> food_list = new HashMap<String , FoodItem>();
			food_list.put(food.getItemID(),food);
			menu.put(category,food_list);
		}
	}
	
    /** 
     * Return the fooditems list by category
     * @return HashMap<String , FoodItem>
     */
	public HashMap<String , FoodItem> getFoodItemsByCategory(String categoryName) {
		if(menu.containsKey(FoodCategory.valueOf(categoryName))) {
			return menu.get(FoodCategory.valueOf(categoryName));
		}
		return null;
	}

	@Override
	public String toString() {
		return "Menu [menu=" + menu + "]";
	}
	
}
