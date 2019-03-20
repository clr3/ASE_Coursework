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
	
	
	//private ArrayList<CustomerOrder> ordersForDisplay = new ArrayList<CustomerOrder>();
	
	private HashMap<String,CustomerOrder> workingOrders = new HashMap<String ,CustomerOrder>();
	private StaffManager smgr = new StaffManager();
	private ArrayList<ServingStaff> staffList = smgr.getStaffList();
	JFrame s = new JFrame();

	
/**INITIALIZE StaffGui */
	public StaffGUI() {
		//this.orderManager = o;
		//this.orderManager.staffGui = this;
		//this.smanager = sm;
		createPage();
	}
	
	 public void createPage() {
		 	
	        s.add(new JSeparator(SwingConstants.VERTICAL));
	        
	        s.add(acceptOrderButton(),BorderLayout.CENTER);  
	       // s.add(workingOrders(),BorderLayout.SOUTH);
	        ordersQueue();
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
		
		//buttonPanel.add(acceptOrder);
		buttonPanel.add(startServe);
		return buttonPanel;
	}
	
	public synchronized void reRenderQueue() {
		Logger.getInstance().log("re render queue");
		ordersQueue();
	}

	/**
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
	 * Checks the list of working orders and creates a display from it 
	 * */
	private JPanel workingOrds() {
		JPanel p = new JPanel();
		int size = staffList.size();
		
		p.setLayout(new FlowLayout());
		
		if(size == 0) return p; //Will return an empty panel 
			
		
		for(ServingStaff ss: staffList) {
			CustomerOrder o = workingOrders.get(ss);
			p.add(oneOrderDisplay(o, ss));
		}
		
		
		return p;
		
	}
	private JPanel oneOrderDisplay(CustomerOrder o, ServingStaff s) {
		JPanel p = new JPanel();
		//Display Staff Name 
		
		//Display Customers Name
		JLabel orderLabel = new JLabel(s.getName() +" is working on order for " + o.getCustomerId() + ": " );
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
	
}
