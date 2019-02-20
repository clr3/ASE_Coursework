package CoffeeShopGUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import CoffeeShopUtilities.Menu;
import CoffeeShopUtilities.OrderManager;


/**
 * @Author Cristina Rivera 	<clr3@hw.ac.uk>
 * 
 * JPanel which provides access to: 
 *  				menu (for customers)
 * 					staff view 
 * */

public class StartPageGUI extends JPanel{
	
	private GridBagConstraints constraints = new GridBagConstraints();
	
	private JFrame frame;
	private JButton staffButton;
	private JButton customerButton;
	private JButton exitButton;
	private MenuGUI menu_gui;
	private OrderManager om;
	
	/**Initialise */
	public StartPageGUI() {}
  
	/**Initialise the buttons */
	public StartPageGUI(Menu menu_obj, JFrame frame) {
		this.frame = frame;
		menu_gui = new MenuGUI(menu_obj);
		setLayout(new GridBagLayout());
		create_staff_button();
		create_customer_button();
		createExitButton();
		
		
		int x, y;
		constraints.gridheight= 3;  //Span 3 rows
		constraints.gridwidth = 5;  //Span 5 columns
		constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.anchor = GridBagConstraints.BELOW_BASELINE;
       
		add_component(customerButton, x = 0, y = 0);
		
		constraints.weightx = 0.5;
        constraints.weighty = 0.5;
        constraints.fill = GridBagConstraints.EAST;
		constraints.gridheight = 1; //set back
		constraints.gridwidth = 1;
		add_component(staffButton, x = 3, y = 6);
		
		constraints.weightx = 0.5;
        constraints.weighty = 0.5;
        constraints.fill = GridBagConstraints.WEST;
		constraints.gridheight = 1; //set back
		constraints.gridwidth = 1;
		add_component(exitButton, x = 6, y = 6);
				
				
	}
	
	/**
	 * As seen in this tutorial: 
	 * https://www.oreilly.com/library/view/learning-java-4th/9781449372477/ch19s06.html#learnjava3-CHP-19-FIG-7
	 * */
	public void add_component(Component component,int x , int y) {
		constraints.gridx = x;
		constraints.gridy = y;
		add(component, constraints);

	}
	
	/**
	 * Create Button for showing the menu
	 * */
	public void create_customer_button() {
		customerButton = new JButton("View Menu");
		customerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Open Menu Page 
				//stem.out.println("Menu");
				//Create a Menu Page every time the button is clicked
				MenuGUI menu_gui = new MenuGUI(new Menu());
				menu_gui.showMenuPage();
			}
			
		});

	}
	/**
	 * Create Button for staff view
	 * */
	public void create_staff_button() {
		staffButton = new JButton("Staff");
		staffButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Open staff View
				System.out.println("Staff");

			}
			
		});

	}
	
	/**
	 * Create Button to exist the app
	 * */
	public void createExitButton() {
		exitButton = new JButton("Exit");
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// print summary report to summary_report.csv
				om = new OrderManager();
				om.writeReports();
				System.out.println("Order summary report is saved to summary_report.csv");
				frame.setVisible(false);

			}
			
		});

	}
	
	
}
