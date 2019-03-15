package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JLabel;

import model.FoodCategory;
import model.FoodItem;
import model.Menu;
import view.MenuGUI;

public class MenuController {
	public MenuGUI menuGUI; 
	public Menu menu_obj;
	public OrderController om;
	
	public MenuController(Menu menu_obj, OrderController om) {
		this.menu_obj = menu_obj;
		this.om = om;
		this.initView(menu_obj,om);
	}
	
	public void initView(Menu menu_obj, OrderController om) {	
		menuGUI = new MenuGUI(menu_obj, om, this);
		menuGUI.createPage();

	}
	
	public void showMenuPage() {
		menuGUI.showMenuPage();
	}
	
	public ActionListener categoryActionListener(String categoryName) {
		return new ActionListener() {
	        @Override
	         public void actionPerformed(ActionEvent e) {
	        	menuGUI.addFoodItems(categoryName);
	         }
	    };
	}
	
	/**
	 * Shows first category fooditems by default
	 */
    public void showFirstCategory() {
        Map.Entry<FoodCategory, HashMap<String , FoodItem>> entry = menu_obj.getMenu().entrySet().iterator().next();
        String key = entry.getKey().toString();
        menuGUI.addFoodItems(key);
    }
	
	public ActionListener resetButtonActionListener() {
		return new ActionListener() {
		        @Override
		         public void actionPerformed(ActionEvent e) {
		        	menuGUI.cart.clear();
		        	menuGUI.totalCost = 0;
		        	showFirstCategory();
		         }
		};
	}
	
	public ActionListener orderButtonActionListener() {
	  return new ActionListener() {
            @Override
             public void actionPerformed(ActionEvent e) {
            	om.addNewOrder(menuGUI.cart, menuGUI.totalCost);
             }
        };
	}
	
	public ActionListener closeButtonActionListener() {
		return new ActionListener() {
	        @Override
	         public void actionPerformed(ActionEvent e) {
	        	menuGUI.hideMenuPage();
	         }
	    };
	}
    
	public ActionListener minusButtonActionListener(String category, String itemKey,FoodItem itemValue, JLabel itemCountLabel,JLabel itemCartPriceLabel) {
	 return new ActionListener() {
        @Override
         public void actionPerformed(ActionEvent e) {
        	menuGUI.removeItemFromCart(category, itemKey, itemValue, itemCountLabel, itemCartPriceLabel);
         }
	 };
	}
	
	public ActionListener plusButtonActionListener(String category, String itemKey,FoodItem itemValue, JLabel itemCountLabel,JLabel itemCartPriceLabel) {
		return new ActionListener() {
	        @Override
	         public void actionPerformed(ActionEvent e) {
	        	menuGUI.addItemToCart(category, itemKey, itemValue, itemCountLabel, itemCartPriceLabel);
	         }
	    };
	}
	
	/**
	 * Returns per item total price
	 *
	 * @return Double item total price
	 */
    public double getItemCartPrice(String itemKey, Double itemPrice) {
        double itemCost = 0;
        if (menuGUI.cart.containsKey(itemKey)) {
            int count =  menuGUI.cart.get(itemKey).intValue();
            itemCost = itemPrice * count; 
        }
        return itemCost;
    }
    
	/**
	 * Get item count from the cart
	 *
	 * @return String item count as string
	 */
    public String getItemCountFromCart(String itemKey) {
        int count = 0;
        if (menuGUI.cart.containsKey(itemKey)) {
            count = menuGUI.cart.get(itemKey).intValue();
        }
        return Integer.toString(count);
    }
    
    public int addItemToCart(String itemKey) {
        int count = 0;
        if (menuGUI.cart.containsKey(itemKey)) {
            count = menuGUI.cart.get(itemKey).intValue();
            count++;
            menuGUI.cart.put(itemKey, count);
        } else {
            count = 1;
            menuGUI.cart.put(itemKey, count);
        }
        return count;
    }
    
    public int removeItemFromCart(String itemKey) {
    	int count = 0;
        count = menuGUI.cart.get(itemKey).intValue();
        count--;
        if(count == 0) {
        	menuGUI.cart.remove(itemKey);
        } else {
        	menuGUI.cart.put(itemKey, count);
        }
        return count;
    }
}
