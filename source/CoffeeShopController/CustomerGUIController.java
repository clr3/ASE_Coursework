package CoffeeShopController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import CoffeeShopGUI.*;
import CoffeeShopModel.*;

public class CustomerGUIController {

	CustomerOrdergui orderGui;
	CustomerOrder order;
	OrderManager orderManager; //Used to add the order to the orderManager
	
	FoodItem newItem;
	FoodItem itemToRemove;
	FoodCategory itemCategory;
	private ArrayList<FoodItem> labels = new ArrayList<FoodItem>();
	
/**
 * Use this class to connect the MENU_GUI with the CustomerOrderGui and Order Manager
 * 
 * The CustomerOrderGUI sill be shown as soon as this class is created 
 * */	
public CustomerGUIController(OrderManager om, CustomerOrdergui o) {
	
		this.orderGui = o;
		this.orderManager = om;
		this.order = orderGui.getOrder();
		
		orderGui.show_order();
		
		
		o.addPlaceNewOrderActionListener(AcceptOrder());
		
	
		
	}

	/**
	 * When the button is clicked, the Customer Order Should be added to the order manager 
	 * */
	public ActionListener AcceptOrder() {
		return new ActionListener() {
		        @Override
		         public void actionPerformed(ActionEvent e) {
		        	orderManager.submitNewOrder(order.getOrderId(), order);
		        	orderGui.acceptButtonClicked();
		         }
		};
	}

	/**Remove Item from the Order
	 * Update the display to show the new prices and amounts
	 * 
	public class RemoveItem implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(!order.getOrderItems().isEmpty()) {
				order.removeItem(itemToRemove);
				System.out.println("+" + itemToRemove.getName());

			}
			menuGui.updateOrder(order);
			menuGui.updateItemsDisplay(itemCategory);
		}
	}*/

	/**Remove All Items from the Order
	public class ResetOrder implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(!order.getOrderItems().isEmpty()) {
				order.clearOrder();
				System.out.println("Reset");
			}
			menuGui.updateOrder(order);
			menuGui.updateItemsDisplay(itemCategory);
		}
	}
	*/
}
