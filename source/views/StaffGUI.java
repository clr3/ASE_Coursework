package views;

import java.awt.BorderLayout;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

import model.CustomerOrder;
import model.FoodItem;
import service.OrderManager;

public class StaffGUI {
	
	private JPanel ordersQueuePanel = new JPanel();
	private JPanel workingOrdersPanel = new JPanel();
	private OrderManager orderManager;
	private JButton acceptOrder = new JButton("Accept Next Order");
	private ArrayList<CustomerOrder> ordersForDisplay = new ArrayList<CustomerOrder>();
	private ArrayList<CustomerOrder> workingOrders = new ArrayList<CustomerOrder>();

	JFrame s = new JFrame();

	public StaffGUI(OrderManager o) {
		this.orderManager = o;
		
		createPage();
	}
	
	 public void createPage() {
	        JPanel jp = new JPanel();
	        jp.add(ordersQueue());
	          
	        s.add(jp,BorderLayout.NORTH);  
	        s.add(new JSeparator(SwingConstants.VERTICAL));
	        
	        s.add(acceptOrderButton(),BorderLayout.CENTER);  
	        s.add(workingOrders(),BorderLayout.SOUTH);

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
	 * Shows the OrderID, Customer ID and total number of items in the order 
	 * */
	private JPanel ordersQueue() {
		
		
		ordersQueuePanel = new JPanel();
		ordersQueuePanel.setLayout(new BoxLayout(ordersQueuePanel, BoxLayout.Y_AXIS));
		
		JLabel ordersCount = new JLabel("There are curresntly >" +orderManager.getOrdersForDisplay().size()+ "< people waiting on the queque:");
		ordersQueuePanel.add(ordersCount);
		
		for(CustomerOrder order: orderManager.getOrdersForDisplay()) {
			// OrderNumber 
			//000 -> For Customer1 :
			JLabel orderLabel = new JLabel(order.getOrderId() + " -> For " + 
					order.getCustomerId() + ": " );
			//orderLabel.setAlignmentX(orderLabel.LEFT_ALIGNMENT);
			
			//GEt Items Count and display
			JLabel itemsLabel = new JLabel(order.getOrderItems().size() + " Items");
			//itemsLabel.setAlignmentX(itemsLabel.RIGHT_ALIGNMENT);
			
			JSplitPane view = new JSplitPane();
		
			view.setLeftComponent(orderLabel);
			view.setRightComponent(itemsLabel);
			view.setVisible(true);
			
			ordersQueuePanel.add(view);
		}
		
		ordersQueuePanel.setVisible(true);
		ordersQueuePanel.validate();
		
		return ordersQueuePanel;
	}
	
	/**In this case, a single member of staff
	 * Will be able to take the next order.
	 * 
	 * */
	public JPanel workingOrders() {
		workingOrdersPanel = new JPanel();
		workingOrdersPanel.setLayout(new BoxLayout(workingOrdersPanel, BoxLayout.Y_AXIS));

		
		workingOrdersPanel.add(workingOrds());
		
		workingOrdersPanel.setVisible(true);
		workingOrdersPanel.validate();
		
		return workingOrdersPanel;
	}
	
	/**
	 * Checks the list of working orders and creates a dosplay from it 
	 * */
	private JPanel workingOrds() {
		JPanel p = new JPanel();
		int size = workingOrders.size();
		
		p.setLayout(new FlowLayout());
		for(CustomerOrder o: workingOrders) {
			p.add(oneOrderDisplay(o));
		}
		
		return p;
		
	}
	private JPanel oneOrderDisplay(CustomerOrder o) {
		JPanel p = new JPanel();
		
		//Display Customers Name
		JLabel orderLabel = new JLabel("Working on order for " + o.getCustomerId() + ": " );
		orderLabel.setAlignmentX(orderLabel.LEFT_ALIGNMENT);
		
		//Display Order items and Total Price 
		ArrayList<FoodItem> items = o.getOrderItems();
		StringBuffer orderItems = new StringBuffer();
		
		for(FoodItem i: items) {
			orderItems.append(i.getName() + " > " + i.getPrice() + " \n" );
		}
		orderItems.append("Total: " + o.getFinalBillAmount());
		
		JLabel itemsLabel = new JLabel(orderItems.toString());
		itemsLabel.setAlignmentX(itemsLabel.RIGHT_ALIGNMENT);
		
		return p;
	}
	
	public void addOrderButtonActionLIstener(ActionListener al) {
		acceptOrder.addActionListener(al);
	}
	/**
	 * Might need to be moved to LOG or OM controller
	 * */
	public void acceptNextOrder() {
		this.workingOrders.add(orderManager.acceptNextOrder());
		 
	}
}
