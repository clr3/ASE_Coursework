package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import model.*;
import service.FileManager;
import service.OrderManager;
import views.*;

/***
 * 
 * The following controls are in this class:
 * 
 * 
 * 	- Accept the order and add it to the csvfile aswell as the CustomerOrders Queue 
 *	- Close Menu once the order has been made
 *
 */

public class CustomerGUIController {

	
	private OrderManager orderManager = OrderManager.getInstance(); //Used to add the order to the orderManager
	

	CustomerOrdergui orderGui; //Is attached to a CustomrOrder
	MenuGUI menuGui;
	
	CustomerOrder order;
	
	FoodItem newItem;
	FoodItem itemToRemove;
	FoodCategory itemCategory;
	private ArrayList<FoodItem> labels = new ArrayList<FoodItem>();
	
/**
 * Use this class to connect the MENU_GUI with the CustomerOrderGui and Order Manager
 * 
 * The CustomerOrderGUI sill be shown as soon as this class is created 
 * */	
	public CustomerGUIController(CustomerOrdergui o, MenuGUI m) {
	
		this.orderGui = o;
		this.menuGui = m;
		this.order = orderGui.getOrder();
		
		orderGui.show_order();
		
		orderGui.addPlaceNewOrderActionListener(AcceptOrder());
		orderGui.addBackToMenuActionListener(CancelOrder());
		
	}

	/**
	 * When the button is clicked, 
	 * -the Customer Order Should be added to the orders Queue
	 * -Show Message for accepting the order
	 * -Add order to csv file
	 * 
	 * */
	public ActionListener AcceptOrder() {
		return new ActionListener() {
		        @Override
		         public void actionPerformed(ActionEvent e) {
		        	FileManager writer = new FileManager();
		        	orderManager.submitNewOrder(order);
		        	writer.write_Order_toCSV(order);
		        	orderGui.acceptButtonClicked();
		        	menuGui.hideMenuPage();
		         }
		};
	}
	
	/**
	 * When the button is clicked, 
	 * -Close CustomerOrderGui, go back to menu
	 *
	 * */
	public ActionListener CancelOrder() {
		return new ActionListener() {
		        @Override
		         public void actionPerformed(ActionEvent e) {
		        	orderGui.closeGui();
		         }
		};
	}
}
