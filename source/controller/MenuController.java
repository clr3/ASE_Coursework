package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JLabel;

import model.CustomerOrder;
import model.FoodCategory;
import model.FoodItem;
import model.Menu;
import service.OrderManager;
import views.CustomerOrdergui;
import views.MenuGUI;



/**
 * CUSTOMER ORDER CONTROLLER
 * OrderManager is not needed in this class once we can use it with the singleton pattern
 * */
public class MenuController {
	public MenuGUI menuGUI;
	public Menu menu_obj;
	private CustomerOrder myOrder;
	private OrderManager orderMan;
	private MenuController thisController;
	
	public MenuController(Menu menu_obj, CustomerOrder o, OrderManager om) {
		this.menu_obj = menu_obj;
		this.myOrder = o;
		this.orderMan = om;
		this.initView(menu_obj);
		this.thisController = this;
	}
	
	public void initView(Menu menu_obj) {
		this.menuGUI = new MenuGUI(Menu.getInstance(), this);
		menuGUI.createPage();

	}
	
	public void showMenuPage() {
		menuGUI.showMenuPage();
	}
	public void hideMenuPage() {
		menuGUI.hideMenuPage();
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
	
    /**
     * Empty the Customer Order 
     * */
	public ActionListener resetButtonActionListener() {
		return new ActionListener() {
		        @Override
		         public void actionPerformed(ActionEvent e) {
		        	menuGUI.cart.clear();
		        	menuGUI.totalCost = 0;
		        	showFirstCategory();
		        	myOrder.clearOrder();
		         }
		};
	}
	/**
	 * MenuGUI should not be in charge of adding the oders to the order manager
	 * 
	 * It should create a customerOrder
	 * CustomerOrder will be added to order manager from CustomerOrderGUI
	 * */
	public ActionListener orderButtonActionListener() {
	  return new ActionListener() {
            @Override
             public void actionPerformed(ActionEvent e) {
            	CustomerGUIController popUpCustOrder = new CustomerGUIController(orderMan,new CustomerOrdergui(myOrder), thisController);
            	//popUpCustOrder.setMenuGui(menuGUI);
            	popUpCustOrder.showOrder();
            	//om.submitNewOrder(menuGUI.cart, menuGUI.totalCost);
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
    
	/**
	 * Remove the selected item from the Customer Order 
	 * */	
	
	
	public ActionListener minusButtonActionListener(FoodCategory category, String itemKey,FoodItem itemValue, JLabel itemCountLabel,JLabel itemCartPriceLabel) {
	 return new ActionListener() {
        @Override
         public void actionPerformed(ActionEvent e) {
        	myOrder.removeItem( menuGUI.removeItemFromCart(category.toString(), itemKey, itemValue, itemCountLabel, itemCartPriceLabel));
         }
	 };
	}
	
	public ActionListener plusButtonActionListener(FoodCategory category, String itemKey,FoodItem itemValue, JLabel itemCountLabel,JLabel itemCartPriceLabel) {
		return new ActionListener() {
	        @Override
	         public void actionPerformed(ActionEvent e) {
	        	myOrder.addItem(menuGUI.addItemToCart(category.toString(), itemKey, itemValue, itemCountLabel, itemCartPriceLabel));
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
