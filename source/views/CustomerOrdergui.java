package views;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;

import controller.MenuController;
import model.CustomerOrder;
import model.FoodItem;

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
@SuppressWarnings("serial")
public class CustomerOrdergui extends JDialog {
	
	private JPanel ordersPanel;
  

	private JButton cancel = new JButton("Back to Menu");
	private JButton accept = new JButton ("Place Order Now");
	

	private CustomerOrder order;



	public CustomerOrdergui(CustomerOrder c) {
		this.order= c;
	}
	
	/**
	 * Show the Dialog Box
	 * 
	 * We use a border layout here
	 * We split the pane horizontally with the ordered items on the right and item buttons to the left
	 * We get the panels for the frame and put it in the divided pane
	 */
	public void show_order() {
		/**
		 * Initializing 
		 */
	
		setSize(300,200);
		setLocation(400,400);
		setName("My Order");
		setBackground(Color.WHITE);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		create();
		setVisible(true);
	}
	/**
	 * @Return CustomerOrder related to this gui
	 * */
	public CustomerOrder getOrder() {
		return this.order;
	}
	public void create() {		
		setLayout(new BorderLayout());
		add(show_order_items(), BorderLayout.PAGE_START);
		add(discount_message("Discunt Dislplayed Here Passed By the Menu"), BorderLayout.CENTER);
		add(total_cost());
		add(option_buttons());
		
	}

   
	
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
		
		
		
		panel.add(cancel);
		panel.add(accept);
		
		return panel;
	}
	
	public void addPlaceNewOrderActionListener(ActionListener al) {
		accept.addActionListener(al);
	}
	
	
	public void addBackToMenuActionListener(ActionListener al) {
		cancel.addActionListener(al);
	}
	
	/**
	 * Accept Button will become not visible
	 * 
	 * JDialog message should change to say: Thank you for your order.
	 * */
	public void acceptButtonClicked(MenuController menu) {
		accept.setVisible(false);
		JLabel thankyou = new JLabel("Thank you for your Order");

		JOptionPane.showMessageDialog(this, thankyou, "", JOptionPane.PLAIN_MESSAGE);
		this.setVisible(false);
		menu.hideMenuPage();
	}
	
	public void closeGui() {
		this.setVisible(false);
	}
	}
