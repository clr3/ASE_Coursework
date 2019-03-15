package CoffeeShopController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import CoffeeShopGUI.*;
import CoffeeShopUtilities.*;

public class OrderController {

	MenuGUI menuGui;
	CustomerOrder order;
	OrderManager orderManager; //Used to add the order to the orderManager
	FoodItem newItem;
	FoodItem itemToRemove;
	FoodCategory itemCategory;
	private ArrayList<FoodItem> labels = new ArrayList<FoodItem>();
	
/**
 * Use this class to connect the MENU_GUI with the CustomerOrderGui and Order Manager
 * */	
public OrderController(MenuGUI m, CustomerOrder o, OrderManager om) {
	
		this.order = o;
		this.menuGui = m;
		this.orderManager = om;
		
		//m.setAddButtonsActionListener(new AddItem());
		//m.setRemoveButtonsActionListener(new RemoveItem());
		//m.setRemoveAllActionListener(new ResetOrder());
	
		
	}

	/**Add Item to the Order
	 * 	 
	 * * Update the display to show the new prices and amounts
	 *
	public class AddItem implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
		FoodItem i = labels.get(menuGui.getItemNo());
		order.addItem(i);
		System.out.println("+" + i.getName());
		menuGui.updateOrder(order);
		menuGui.updateItemsDisplay(itemCategory);
		}
	
	}*/

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
