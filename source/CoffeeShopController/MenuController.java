package CoffeeShopController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JLabel;

import CoffeeShopGUI.MenuGUI;
import CoffeeShopModel.FoodCategory;
import CoffeeShopModel.FoodItem;
import CoffeeShopModel.Menu;
import CoffeeShopModel.OrderManager;

public class MenuController {
	public MenuGUI menuGUI; 
	public Menu menu_obj;
	public OrderManager om;
	
	public MenuController(Menu menu_obj, OrderManager om) {
		this.menu_obj = menu_obj;
		this.om = om;
		this.initView(menu_obj,om);
	}
	
	public void initView(Menu menu_obj, OrderManager om) {	
		menuGUI = new MenuGUI(menu_obj, om, this);
		menuGUI.createPage();

	}
	
	public void showMenuPage() {
		menuGUI.showMenuPage();
	}
	
	public ActionListener categoryActionListener(FoodCategory categoryName) {
		return new ActionListener() {
	        @Override
	         public void actionPerformed(ActionEvent e) {
	        	menuGUI.addFoodItems(categoryName.toString());
	         }
	    };
	}
	
	/**
	 * Shows first category fooditems by default
	 */
    public void showFirstCategory() {
        Map.Entry<FoodCategory, HashMap<String , FoodItem>> entry = menu_obj.getMenu().entrySet().iterator().next();
        FoodCategory key = entry.getKey();		
        menuGUI.addFoodItems(key.toString());
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
    
	public ActionListener minusButtonActionListener(FoodCategory category, String itemKey,FoodItem itemValue, JLabel itemCountLabel,JLabel itemCartPriceLabel) {
	 return new ActionListener() {
        @Override
         public void actionPerformed(ActionEvent e) {
        	menuGUI.removeItemFromCart(category.toString(), itemKey, itemValue, itemCountLabel, itemCartPriceLabel);
         }
	 };
	}
	
	public ActionListener plusButtonActionListener(FoodCategory category, String itemKey,FoodItem itemValue, JLabel itemCountLabel,JLabel itemCartPriceLabel) {
		return new ActionListener() {
	        @Override
	         public void actionPerformed(ActionEvent e) {
	        	menuGUI.addItemToCart(category.toString(), itemKey, itemValue, itemCountLabel, itemCartPriceLabel);
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
