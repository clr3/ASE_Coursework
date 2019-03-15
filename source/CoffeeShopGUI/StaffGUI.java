package CoffeeShopGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

import CoffeeShopModel.CustomerOrder;
import CoffeeShopModel.FoodItem;
import CoffeeShopModel.OrderManager;

public class StaffGUI extends JPanel{
	
	private JPanel ordersQueuePanel = new JPanel();
	private OrderManager orders;
	private JButton acceptOrder = new JButton("Accept Next Order");
	private ArrayList<CustomerOrder> ordersForDisplay = new ArrayList<CustomerOrder>();
	
	JFrame s = new JFrame();

	public StaffGUI(OrderManager o) {
		this.orders = o;
		
		s.add(ordersQueue());
		s.add(acceptOrderButton());
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
		
		
		acceptOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// remove the order from the list and write to csv 
				System.out.println("Staff");
				orders.acceptNextOrder();
			}
		});
			
			
		return buttonPanel;
	}
	
	private JPanel ordersQueue() {
		
		
		ordersQueuePanel = new JPanel();
		ordersQueuePanel.setLayout(new BoxLayout(ordersQueuePanel, BoxLayout.Y_AXIS));
		
		for(CustomerOrder order: orders.getOrdersForDisplay()) {
			JLabel orderLabel = new JLabel(order.getOrderId() + " -> For " + 
					order.getCustomerId() + ": " );
			orderLabel.setAlignmentX(LEFT_ALIGNMENT);
			ArrayList<FoodItem> items = order.getOrderItems();
			String orderItems = "";
			for(FoodItem i: items) {
				orderItems +=  i.getName() + " > " + i.getPrice() + " \n" ;
			}
			orderItems += order.getFinalBillAmount();
			JLabel itemsLabel = new JLabel(orderItems);
			itemsLabel.setAlignmentX(RIGHT_ALIGNMENT);
			
			ordersQueuePanel.add(orderLabel);
			ordersQueuePanel.add(itemsLabel);
			
		}
		ordersQueuePanel.validate();
		
		return ordersQueuePanel;
	}
	
	

}
