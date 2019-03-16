package CoffeeShopGUI;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

import CoffeeShopModel.CustomerOrder;
import CoffeeShopModel.FoodItem;
import CoffeeShopModel.OrderManager;

public class StaffGUI {
	
	private JPanel ordersQueuePanel = new JPanel();
	private OrderManager orderManager;
	private JButton acceptOrder = new JButton("Accept Next Order");
	private ArrayList<CustomerOrder> ordersForDisplay = new ArrayList<CustomerOrder>();
	
	JFrame s = new JFrame();

	public StaffGUI(OrderManager o) {
		this.orderManager = o;
		
		createPage();
	}
	
	 public void createPage() {
	        JPanel jp = new JPanel();
	        jp.add(ordersQueue());
	          
	        s.add(jp,BorderLayout.NORTH);  
	        s.add(acceptOrderButton(),BorderLayout.CENTER);  

	        s.setSize(600,400);  
	        s.setVisible(false); 
	        s.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	    }
	
	public void showStaffView() {
		s.setVisible(true);
	}
	/**
	 * Button to accept The Next Order from the file Manager
	 * @Return JPanel containing a button
	 * */
	private JPanel acceptOrderButton() {
		JPanel buttonPanel = new JPanel();
		
		buttonPanel.add(acceptOrder);	
		return buttonPanel;
	}
	
	private JPanel ordersQueue() {
		
		
		ordersQueuePanel = new JPanel();
		ordersQueuePanel.setLayout(new BoxLayout(ordersQueuePanel, BoxLayout.Y_AXIS));
		
		for(CustomerOrder order: orderManager.getOrdersForDisplay()) {
			// OrderNumber 
			//000 -> For Customer1 :
			JLabel orderLabel = new JLabel(order.getOrderId() + " -> For " + 
					order.getCustomerId() + ": " );
			orderLabel.setAlignmentX(orderLabel.LEFT_ALIGNMENT);
			ArrayList<FoodItem> items = order.getOrderItems();
			String orderItems = "";
			//Show the food item name and price
			//Hot Chocolate > 3.20
			for(FoodItem i: items) {
				orderItems +=  i.getName() + " > " + i.getPrice() + " \n" ;
			}
			//Final Bill Amount
			orderItems += order.getFinalBillAmount();
			JLabel itemsLabel = new JLabel(orderItems);
			itemsLabel.setAlignmentX(itemsLabel.RIGHT_ALIGNMENT);
			
			ordersQueuePanel.add(orderLabel);
			ordersQueuePanel.add(itemsLabel);
			
		}
		ordersQueuePanel.setVisible(true);
		ordersQueuePanel.validate();
		
		return ordersQueuePanel;
	}
	
	
	public void addOrderButtonActionLIstener(ActionListener al) {
		acceptOrder.addActionListener(al);
	}
	

}
