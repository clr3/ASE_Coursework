package views;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import model.CustomerOrder;
import model.FoodCategory;
import model.FoodItem;
import service.OrderManager;
import service.StaffManager;
import utilities.Logger;

public class StaffGUI {
	
	private JPanel ordersQueuePanel = new JPanel();
	private JPanel workingOrdersPanel = new JPanel();
	private OrderManager orderManager;
	private StaffManager smanager;
	private JButton acceptOrder = new JButton("Accept Next Order");
	private JButton startServe = new JButton("Start Serving");
	private JButton processTimeButton = new JButton("Process Timer");

	private ArrayList<CustomerOrder> ordersForDisplay = new ArrayList<CustomerOrder>();
	private ArrayList<CustomerOrder> workingOrders = new ArrayList<CustomerOrder>();
	JFrame s = new JFrame();
	private TimerGUI timeGui;

	public StaffGUI(OrderManager o, StaffManager sm, TimerGUI timerGui) {
		this.orderManager = o;
		this.orderManager.staffGui = this;
		this.smanager = sm;
		this.timeGui = timerGui;
		createPage();
	}
	
	 public void createPage() {
		 	startServe.addActionListener(this.smanager.serveActionListener());
	        s.add(new JSeparator(SwingConstants.VERTICAL));
	        processTimeButton.addActionListener(this.smanager.showTimerPageActionListener(this.orderManager));
	        s.add(acceptOrderButton(),BorderLayout.CENTER);  
	        s.add(workingOrders(),BorderLayout.SOUTH);
	        //s.add(createProcessTimePanel(),BorderLayout.SOUTH);
	        ordersQueue();
	       // s.add(comp)
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
		buttonPanel.add(startServe);
		buttonPanel.add(processTimeButton);
		return buttonPanel;
	}
	
	public synchronized void reRenderQueue() {
		ordersQueue();
	}

	/**
	 * Show the Orders that haven been worked on.
	 * Shows the OrderID, Customer ID and total number of items in the order 
	 * */
	private synchronized void ordersQueue() {
		removePanel(ordersQueuePanel);
		ordersQueuePanel = new JPanel();
		s.revalidate();
        s.repaint();
		ordersQueuePanel.setLayout(new BoxLayout(ordersQueuePanel, BoxLayout.Y_AXIS));
		
		JLabel ordersCount = new JLabel("There are currently >" +orderManager.getAllOrdersOnOrderQueue().size()+ "< people waiting on the queque:");
		ordersQueuePanel.add(ordersCount);
		
		for(CustomerOrder order: orderManager.getAllOrdersOnOrderQueue()) {
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
        s.add(ordersQueuePanel,BorderLayout.NORTH); 
        s.revalidate();
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
	
	/**
	 * Removes the fooditems panel
	 *
	 */
    private void removePanel(JPanel tempPanel) {
        Component[] componentList = tempPanel.getComponents();
        for(Component c : componentList){
            tempPanel.remove(c);
        }
        tempPanel.revalidate();
        tempPanel.repaint();
        s.remove(tempPanel);
    }
}
