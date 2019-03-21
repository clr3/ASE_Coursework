package views;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;

import model.CustomerOrder;
import model.FoodItem;
import queueExceptions.QueueEmptyException;
import service.OrderManager;
import service.ServingStaff;
import service.StaffManager;
import utilities.Logger;

public class StaffGUI {
	
	private JPanel ordersQueuePanel = new JPanel();
	private JPanel workingOrdersPanel = new JPanel();
	
	private OrderManager orderManager = OrderManager.getInstance(); 
		
	//private JButton acceptOrder = new JButton("Accept Next Order");
	/** Start Serving Button will start the threads.
	 * - Show the Next order being worked on and a timer of how long left there is.
	 *  */
	
	private JButton startServe = new JButton("Start Serving");
	private JButton processTimeButton = new JButton("Process Timer");
	
	//private ArrayList<CustomerOrder> ordersForDisplay = new ArrayList<CustomerOrder>();
	
	private HashMap<ServingStaff,CustomerOrder> workingOrders = new HashMap<ServingStaff ,CustomerOrder>();
	
	private StaffManager smgr;
	private ArrayList<ServingStaff> staffList;
	JFrame s = new JFrame();

	
/**INITIALIZE StaffGui */
	public StaffGUI(StaffManager sm) {
		//this.orderManager = o;
		//this.orderManager.staffGui = this;
		this.smgr = sm;
		staffList = smgr.getStaffList();
		
		createPage();
	}
	
	 public void createPage() {
		 	
	        s.add(new JSeparator(SwingConstants.VERTICAL));
 
	        ordersQueue();	//Adds panel to mainPane
	        
	        s.add(orderButtons(),BorderLayout.CENTER); 
	        
	        updateStaffView();	//adds panel to pane
	        
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
	private JPanel orderButtons() {
		JPanel buttonPanel = new JPanel();
		//buttonPanel.add(acceptOrder);
		buttonPanel.add(startServe);
		buttonPanel.add(processTimeButton);
		
		return buttonPanel;
	}
	
	public synchronized void reRenderQueue() {
		Logger.getInstance().log("re render queue");
		ordersQueue();
	}

	/**
	 * @North
	 * Show the Orders that haven been worked on.
	 * Shows the OrderID, Customer ID and total number of items in the order 
	 * */
	private synchronized void ordersQueue() {
		removePanel(ordersQueuePanel);
		ordersQueuePanel = new JPanel();
		Logger.getInstance().log(" "+orderManager.getAllOrdersOnOrderQueue().size());
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
			view.setSize(600, 10);
		
			view.setLeftComponent(orderLabel);
			view.setRightComponent(itemsLabel);
			view.setVisible(true);
			
			ordersQueuePanel.add(view);
		}
		
		ordersQueuePanel.setVisible(true);
        s.add(ordersQueuePanel,BorderLayout.NORTH); 
        s.revalidate();
		Logger.getInstance().log(" painted");
	}
	
	/**In this case, a single member of staff
	 * Will be able to take the next order.
	 * @South
	 * */
	public synchronized void workingOrders() {
		removePanel(workingOrdersPanel);
		workingOrdersPanel = new JPanel();
		s.revalidate();
        s.repaint();
	
		
		workingOrdersPanel.setLayout(new GridLayout(staffList.size(), 1));
	
		workingOrdersPanel.add(workingOrds());
		
		workingOrdersPanel.setVisible(true);
		workingOrdersPanel.validate();
        

		s.add(workingOrdersPanel,BorderLayout.SOUTH) ;
		s.revalidate();
	}
	
	/**
	 * Checks the list of working orders and creates a display from it 
	 * */
	private synchronized JPanel workingOrds() {
		JPanel p = new JPanel();
		int size = staffList.size();
		
		p.setLayout(new GridLayout(size,1));
		
		if(size == 0) return p; //Will return an empty panel 
		
			
		for(ServingStaff ss: staffList) {
			CustomerOrder o = workingOrders.get(ss);
			System.out.println(ss.getName() + " is procesing :" + o.getOrderId());
			if(o != null) p.add(oneOrderDisplay(o, ss));
		}
		
		
		return p;
		
	}
	private synchronized JPanel oneOrderDisplay(CustomerOrder o, ServingStaff s) {
		JPanel p = new JPanel();
		p.setLayout(new BorderLayout());
		//Display Staff Name 
		
		//Display Customers Name
		JLabel orderLabel = new JLabel(s.getName() +" is working on order for " + o.getCustomerId() + ": " );
		orderLabel.setAlignmentX(orderLabel.LEFT_ALIGNMENT);
		
		//Display Order items and Total Price 
		JLabel itemsLabel = new JLabel(updateOrderText(o));
		itemsLabel.setAlignmentX(itemsLabel.RIGHT_ALIGNMENT);
		
		p.add(orderLabel, BorderLayout.NORTH);
		p.add(itemsLabel, BorderLayout.CENTER);
		
		return p;
	}
	/**Item Number, name and price is shown 
	 * */
	private String updateOrderText(CustomerOrder o) {
		ArrayList<FoodItem> items = o.getOrderItems();
		StringBuffer orderItems = new StringBuffer();
		
		for(FoodItem i: items) {		
			orderItems.append(i.getName() + " > " + i.getPrice() + " \n" );
		}
		orderItems.append("Total: " + o.getFinalBillAmount());
		return orderItems.toString();
		
	}
	/*
	public void addOrderButtonActionLIstener(ActionListener al) {
		acceptOrder.addActionListener(al);
	}*/

	
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
	
	public void addStartServeActionListener(ActionListener al) {
		startServe.addActionListener(al);
	}
	public void addProcessTimeActionListener(ActionListener al) {
		processTimeButton.addActionListener(al);
	}
	/**	
	 * Accept next order from the queue and return it
	 *CustomerOrder is added to workingOrders Hashmap 
	 *	Update Staff View
	 * @throws QueueEmptyException 
 * */	
	public CustomerOrder acceptNextOrder(ServingStaff f) throws QueueEmptyException {	
		CustomerOrder next = orderManager.acceptNextOrder();
		this.workingOrders.put(f, next);	
		updateStaffView();
		return next;
	}
	
	public void acceptNextOrderFinished(ServingStaff f, CustomerOrder o) throws QueueEmptyException {	
		this.workingOrders.remove(f, o);	
		updateStaffView();
	}
	
	public void updateStaffView() {		
		reRenderQueue();
		workingOrders();	
		
	}
}
