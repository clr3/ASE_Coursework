/**
* Menu class for Coffee Shop
*/
package CoffeeShopUtilities;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map.Entry;

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
	private int enum_no=5;
	
    /** 
     * Constructor for Menu Class
     * 
     */
	public Menu() {
		
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
		discount_obj1.setDiscountId("COMBO02");
		discount_obj1.setOffer_name("Saver Menu");
		discount_obj1.getItem_list().add("HOT4");
		discount_obj1.getItem_list().add("BAKE2");
		discount_obj1.setDiscount_percentage(10);
		discounts.add(discount_obj1);

		Discount discount_obj2 = new Discount();
		discount_obj.setDiscountId("COMBO03");
		discount_obj2.setOffer_name("BREAKFAST");
		discount_obj2.getItem_list().add("HOT7");
		discount_obj2.getItem_list().add("BAKE3");
		discount_obj2.setDiscount_percentage(25);
		discounts.add(discount_obj2);
	}

	private void addDiscountToMenu() {
		HashMap<String , FoodItem> discountsList = new HashMap<String , FoodItem>();
	      for (Discount discount : discounts) { 		      
	    	  FoodItem fi = new FoodItem();
	    	  fi.setName(discount.getOffer_name());
	    	  Double comboPrice = getComboPrice(discount);
	    	  fi.setPrice(comboPrice);
	    	  fi.setItemID(discount.getDiscountId());
	    	  fi.setDescription(getComboDetails(discount));
	    	  discountsList.put(discount.getDiscountId(), fi);
	      }
	      menu.put(FoodCategory.COMBO, discountsList);
	}
	
	private Double getComboPrice(Discount discount) {
		Double totalPrice = 0d;
		for (String foodItemId :discount.getItem_list()) {
			FoodItem fi = getFoodItemById(foodItemId);
			if (fi != null) {
				totalPrice += fi.getPrice();
			}
		}
		Double discountPercent = (double)(discount.getDiscount_percentage()/100d);
		Double discountRate = totalPrice * discountPercent;
		return totalPrice - discountRate;
	}
	
	private String getComboDetails(Discount discount) {
		ArrayList<String> comboList = new ArrayList<String>();
		
		for (String foodItemId :discount.getItem_list()) {
			FoodItem fi = getFoodItemById(foodItemId);
			if (fi != null) {
				comboList.add(fi.getName());
			}
		}
		String comboDetails = "( " + String.join(" + ", comboList) + " )";
		return comboDetails;
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
	
	public FoodItem getFoodItemById(String itemId) {
		for (Entry<FoodCategory, HashMap<String, FoodItem>> entry : menu.entrySet()) {       
	          if(entry.getValue().containsKey(itemId)) {
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
