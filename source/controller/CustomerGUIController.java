package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import model.*;
import service.OrderManager;
import views.*;

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

}
