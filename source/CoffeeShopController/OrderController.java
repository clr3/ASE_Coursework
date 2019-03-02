package CoffeeShopController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import CoffeeShopGUI.*;
import CoffeeShopUtilities.*;

public class OrderController {

	MenuGUI menuGui;
	CustomerOrder order;
	OrderManager orderManager; //Used to add the order to the orderManager
	FoodItem newItem;
	FoodItem itemToRemove;
	
public OrderController(MenuGUI m, CustomerOrder o, OrderManager om) {
	
		this.order = o;
		this.menuGui = m;
		this.orderManager = om;
		
		m.setAddButtonsActionListener(new AddItem());
		m.setRemoveButtonsActionListener(new RemoveItem());
		m.setRemoveAllActionListener(new ResetOrder());
	
		
	}
	public void setNewItem(FoodItem f) {
		this.newItem = f;
	}
	public void serRemoveItem(FoodItem f) {
		this.itemToRemove = f ;
	}
	
	/**Add Item to the Order*/
	public class AddItem implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
		
		order.addItem(newItem);
		
		}
	
	}

	/**Remove Item from the Order*/
	public class RemoveItem implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(!order.getOrderItems().isEmpty()) {
				order.removeItem(itemToRemove);
			}
		}
	}

	/**Remove All Items from the Order*/
	public class ResetOrder implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(!order.getOrderItems().isEmpty()) {
				order.clearOrder();
			}
		}
	}
	/**
	 * Show CustomerOrderGUI JDialog
	 * */
	public class Order implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			CustomerOrdergui o = new CustomerOrdergui(order);
			o.show_order();
		}
	}
}
