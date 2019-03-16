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
	
	/**
	 * Show the Orders that haven been worked on.
	 * Shows the OrderID, Customer ID and total numer of items in the order 
	 * */
	private JPanel ordersQueue() {
		
		
		ordersQueuePanel = new JPanel();
		ordersQueuePanel.setLayout(new BoxLayout(ordersQueuePanel, BoxLayout.Y_AXIS));
		
		JLabel ordersCount = new JLabel("There are curresntly >" +orderManager.getOrdersForDisplay().size()+ "< people waiting on the queque");
		ordersQueuePanel.add(ordersCount);
		
		for(CustomerOrder order: orderManager.getOrdersForDisplay()) {
			// OrderNumber 
			//000 -> For Customer1 :
			JLabel orderLabel = new JLabel(order.getOrderId() + " -> For " + 
					order.getCustomerId() + ": " );
			orderLabel.setAlignmentX(orderLabel.LEFT_ALIGNMENT);
			
			//GEt Items Count and display
			JLabel itemsLabel = new JLabel(order.getOrderItems().size() + " Items");
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
