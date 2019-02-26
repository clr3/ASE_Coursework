package CoffeeShopGUI;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import CoffeeShopUtilities.CustomerOrder;
import CoffeeShopUtilities.FoodItem;
import java.math.BigDecimal;
/**
 * This class creates the main frame and serves as the primary interface
 * We employ inheritance to absorb the features of JFrame while adding our own
 * We split the frame into one horizontal panel on the right for displaying items ordered
 * A main/center panel for displaying all the menu items
 * A lower panel for placing orders/clearing orders
 * MainFrame amongst others has a menureader as an IV, menu items, and the receipt panel
 * @author Armand Tene
 *
 * @edits Cristina Rivera
 * Create a Jfrme that shows the order and the total.
 *
 */
public class CustomerOrdergui extends JDialog {

	private BigDecimal totalCost;

	
	private JPanel ordersPanel;
	

	private JPanel receipt;
  
	private JPanel centerPanel;
	private JTextField orderPrice;
	private ArrayList <FoodItem> itemsOrdered;
	private String itemInformation;
	


	private CustomerOrder order;

	public CustomerOrdergui() {
		/**
		 * Initializing IVs 
		 */
		totalCost = new BigDecimal(0);
		itemInformation = "";
		
		itemsOrdered = new ArrayList<FoodItem>();
}
	

	public CustomerOrdergui(CustomerOrder c) {
		this.order= c;

	}
	
	/**
	 * We use a border layout here
	 * We split the pane horizontally with the ordered items on the right and item buttons to the left
	 * We get the panels for the frame and put it in the divided pane
	 */
	/**
	*@edits cristina Rivera / I don`t think this is necesary
	*/
	public void show_order() {
		/**
		 * Initializing 
		 */
	
		setSize(500,400);
		setLocation(400,400);
		setName("My Order");
		setBackground(Color.WHITE);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
	public void create() {
		JPanel mainPanel = (JPanel) getContentPane();
		//JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, getItemButtons(), getReceipt());
		
		setLayout(new BorderLayout());
		add(show_order_items(), BorderLayout.PAGE_START);
		add(discount_message("Discunt Dislplayed Here Passed By the Menu"), BorderLayout.CENTER);
		add(total_cost());
		add(option_buttons());
		
	}

    

	/**
	 * Receipt panel deals with the current order
	 * Specify all the dimensions and colors
	 * We add a scroll pane here too incase the order is very long
	 * The textfield is constantly updated with the current price
	 * We set the textfield.setEditable to false so that it cannot be altered by the user
	 * Place order and Clear Order buttons are added here with their respective ActionListeners
	 *
	 *
	 */
	/**private JPanel getReceipt() {
		
		receipt = new JPanel();
		JLabel label = new JLabel("Receipt:");
		receipt.setLayout(new BorderLayout());
		
		JPanel lowerPanel = new JPanel();
		lowerPanel.setLayout(new BorderLayout());
		
		receipt.add(lowerPanel,BorderLayout.SOUTH);
		receipt.add(label, BorderLayout.NORTH);
		
		centerPanel = new JPanel();
		centerPanel.setLayout(new GridLayout(0,1));
		
		orderItems = new JTextPane();
		centerPanel.add(orderItems);
		
		orderItems.setEditable(false);
		
		JScrollPane centerPanelScroller = new JScrollPane(centerPanel);
		receipt.add(centerPanelScroller, BorderLayout.CENTER);
		
		orderPrice = new JTextField(20);
		orderPrice.setText("Total Cost = $ " + order.getFinalBillAmount().toString());
		orderPrice.setEditable(false);
		
		JButton placeOrder = new JButton("Place Order");
		JButton clearOrder = new JButton("Clear Order");
		
		placeOrder.setPreferredSize(new Dimension(30,50));
		clearOrder.setPreferredSize(new Dimension(30,50));
		
		centerPanel.setBackground(Color.LIGHT_GRAY);
		placeOrder.setForeground(Color.BLUE);
		clearOrder.setForeground(Color.RED);
		
		placeOrder.setFont(new Font ("Times New Roman", Font.BOLD,40));
		clearOrder.setFont(new Font ("Times New Roman", Font.BOLD,40));
		
		clearOrder.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				/*
				 * private method that clears all content
				 *
				delete();
				
			}
			
		});
		
		placeOrder.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				/*
				 * We call menu read and log our order. Also display an option pane to notify.
				 * If there is no order, then an option pane will notify a user that there is not one
				 * 
				 *
				try {
					if (!orderPrice.getText().equals("Total Cost = $0.00")) {
						menuRead.logOrder(itemsOrdered, totalCost);
						JOptionPane.showMessageDialog(getContentPane(), "Order has been sent to kitchen", "Order has been logged", JOptionPane.INFORMATION_MESSAGE);
						delete();
					}
					else {
						JOptionPane.showMessageDialog(null,"No items ordered", "Place order", JOptionPane.ERROR_MESSAGE);
					}
					

				if (!orderPrice.getText().equals("Total Cost = $0.00")) {
					//menuRead.logOrder(itemsOrdered, totalCost);
					JOptionPane.showMessageDialog(getContentPane(), "Order has been sent to kitchen", "Order has been logged", JOptionPane.INFORMATION_MESSAGE);
					delete();

				}
				else {
					JOptionPane.showMessageDialog(null,"No items ordered", "Place order", JOptionPane.ERROR_MESSAGE);
				}
				
			}
			
		});
    
		/*
		 * Adding to the panel
		 *
		lowerPanel.add(orderPrice, BorderLayout.NORTH);
		lowerPanel.add(placeOrder, BorderLayout.CENTER);
		lowerPanel.add(clearOrder, BorderLayout.SOUTH);
		lowerPanel.setBackground(Color.LIGHT_GRAY);
		receipt.setBackground(Color.WHITE);
		return receipt;
		
	}*/
	
	
	/**
	 * @author Cristina Rivera
	 * Have a Panel that can show all the items in the order 
	 * Name of the item in the left and 
	 */
	public JPanel show_order_items() {
		revalidate();
		repaint();
		ordersPanel = new JPanel();
		
		
		ArrayList<FoodItem> items = order.getOrderItems();
		
		ordersPanel.setLayout(new BoxLayout(ordersPanel, BoxLayout.X_AXIS));
			
		JPanel food = new JPanel();
		JPanel cost = new JPanel();
		food.setLayout(new BoxLayout(food, BoxLayout.Y_AXIS));
		cost.setLayout(new BoxLayout(cost, BoxLayout.Y_AXIS));

		
		
		ordersPanel.setBackground(Color.LIGHT_GRAY);
		
		if(items.isEmpty()) {
			food.add(new JLabel("Nothing Added to the Cart Yet..."));
		}else{
			for(FoodItem item: items) {
		
				food.add(new JLabel(item.getName()));
						
				cost.add(new JLabel(" => " + Double.toString(item.getPrice())));
			}
		}
		ordersPanel.add(food);
		ordersPanel.add(cost);
		ordersPanel.setOpaque(true);
		
		return ordersPanel;
		
	}
	
	/**
	 * 
	 * @Param message showing the discount that will be applied. 
	 * */	
	public JLabel discount_message(String discount) {
		JLabel d = new JLabel(discount);
		return d;
	}
	/**It is required that the finalBillAmount in the customer order has been updated 
	 * */
	private JLabel total_cost() {
		JLabel costText = new JLabel();
		costText.setText("Total Cost = $" + order.getFinalBillAmount());
	
		return costText;
	}
	/**
	 * Has 2 Buttons, one for confirming the order and one for canceling (going back to menu)
	 * */
	private JPanel option_buttons() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		JButton cancel = new JButton("Back to Menu");
		JButton accept = new JButton ("Continue");
		
		
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Close the window and let the customer change the order
				setVisible(false);
				dispose();
			}
			
		});
		
		accept.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				accept.setVisible(false);
				/*
				 * Could add a dialogue thanking the user or showing customer order
				 * 
				 * 
				 * */
			}
			
		});
		
		panel.add(cancel);
		panel.add(accept);
		
		return panel;
	}
	
	/** 
	 * Constantly updates the order panel based on commands
	 * @param itemButton
	 *
	private void refreshPanel(final Menu itemButton) {
		String item = itemButton.getName();
		BigDecimal itemPrice = itemButton.getCost();
		 itemInformation += "\n" + item + "\n" + itemPrice + "\n";
		 orderItems.setText(itemInformation);
		 itemsOrdered.add(itemButton);
		
		totalCost = totalCost.add(itemPrice) ;
		orderPrice.setText("Total cost = $" + totalCost);

	}*/
	

	}
