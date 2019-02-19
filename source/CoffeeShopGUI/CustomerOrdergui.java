package CoffeeShopGUI;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import CoffeeShopUtilities.CustomerOrder;
import CoffeeShopUtilities.FoodItem;
import CoffeeShopUtilities.Menu;

import java.io.FileNotFoundException;
import java.io.File;
import java.io.IOException;
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
public class CustomerOrdergui extends JFrame {

	private BigDecimal totalCost;
	
	private JPanel ordersPanel;
	
	private JPanel centerPanel;
	private JTextField orderPrice;
	private ArrayList <FoodItem> itemsOrdered;
	private JTextPane orderItems;
	private String itemInformation;
	
	private CustomerOrder order;
	
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
		setTitle("My Order");
		setBackground(Color.WHITE);
		//setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		
		JPanel mainPanel = (JPanel) getContentPane();
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, getItemButtons(), getReceipt());
		
		splitPane.setDividerLocation(780);
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(splitPane, BorderLayout.CENTER);
		
		
	}

	
	/**
	 * Receipt panel deals with the current order
	 * Specify all the dimensions and colors
	 * We add a scroll pane here too incase the order is very long
	 * The textfield is constantly updated with the current price
	 * We set the textfield.setEditable to false so that it cannot be altered by the user
	 * Place order and Clear Order buttons are added here with their respective ActionListeners
	 * @return
	 */
	private JPanel getReceipt() {
		
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
				/**
				 * private method that clears all content
				 */
				delete();
				
			}
			
		});
		
		placeOrder.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				/**
				 * We call menu read and log our order. Also display an option pane to notify.
				 * If there is no order, then an option pane will notify a user that there is not one
				 * 
				 */
				try {
					if (!orderPrice.getText().equals("Total Cost = $0.00")) {
						menuRead.logOrder(itemsOrdered, totalCost);
						JOptionPane.showMessageDialog(getContentPane(), "Order has been sent to kitchen", "Order has been logged", JOptionPane.INFORMATION_MESSAGE);
						delete();
					}
					else {
						JOptionPane.showMessageDialog(null,"No items ordered", "Place order", JOptionPane.ERROR_MESSAGE);
					}
					
				}
				 catch (IOException g) {
					
					 JOptionPane.showMessageDialog(null, "Error! Program terminated"
							 , " Error", JOptionPane.ERROR_MESSAGE);
				}
				
			}
			
		});
		/**
		 * Adding to the panel
		 */
		lowerPanel.add(orderPrice, BorderLayout.NORTH);
		lowerPanel.add(placeOrder, BorderLayout.CENTER);
		lowerPanel.add(clearOrder, BorderLayout.SOUTH);
		lowerPanel.setBackground(Color.LIGHT_GRAY);
		receipt.setBackground(Color.WHITE);
		return receipt;
		
	}
	
	/**
	 * @author Cristina Rivera
	 * Have a Panel that can show all the items in the order 
	 * Name of the item in the left and 
	 */
	private JPanel show_order_items() {
		 
		removePanel(ordersPanel);

		ordersPanel = new JPanel();
		 revalidate();
		 repaint();
		ArrayList<FoodItem> items = order.getOrderItems();
		
		ordersPanel.setLayout(new GridLayout(0,2));
				
		centerPanel.add(orderItems);
		
		orderItems.setEditable(false);
		ordersPanel.setBackground(Color.LIGHT_GRAY);
		for(FoodItem item: items) {
			JLabel itemLabel = new JLabel(item.getName());
			JLabel priceLabel = new JLabel(" => " + Double.toString(item.getPrice()));
			ordersPanel.add(itemLabel, BorderLayout.LINE_START);
			ordersPanel.add(priceLabel, BorderLayout.LINE_END);
		}
		
	
		return ordersPanel;
		
	}
	
	
	private void delete() {
		
		orderPrice.setText("Total Cost = $0.00");
		totalCost = new BigDecimal(0);
		itemsOrdered.clear();
		itemInformation = "";
		orderItems.setText(null);
		
	}
	/** 
	 * Constantly updates the order panel based on commands
	 * @param itemButton
	 */
	private void refreshPanel(final Menu itemButton) {
		String item = itemButton.getName();
		BigDecimal itemPrice = itemButton.getCost();
		 itemInformation += "\n" + item + "\n" + itemPrice + "\n";
		 orderItems.setText(itemInformation);
		 itemsOrdered.add(itemButton);
		
		totalCost = totalCost.add(itemPrice) ;
		orderPrice.setText("Total cost = $" + totalCost);
	}
	}
