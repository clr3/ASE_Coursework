package CoffeeShopGUI;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import CoffeeShopController.StartPageController;
import CoffeeShopModel.CustomerOrder;
import CoffeeShopModel.Menu;
import CoffeeShopModel.OrderManager;
import CoffeeShopController.MenuController;


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
	
	private JButton staffButton = new JButton("Staff");
	private JButton customerButton = new JButton("View Menu");;
	private JButton exitButton = new JButton("Exit");
	private MenuGUI menu_gui;
	private StartPageController controller = new StartPageController(this);
	
	
	//private MenuController menuController;
	private OrderManager om;
	

	/**Initialise */
	public StartPageGUI() {}
  
	/**Initialise the buttons */
	public StartPageGUI(Menu menu_obj, JFrame frame) {
		//start(menu_obj, frame);
	}
	
	public StartPageGUI(JFrame frame ) {
		start(frame);
	}
	
	public void start(JFrame frame) {
		this.frame = frame;
		
    setLayout(new GridBagLayout());
		
		
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
	
	
	/**Create Customer Button Action Listener
	 * */
	public void setCustomerButtonActionListener(ActionListener al) {
		customerButton.addActionListener(al);
	}
	
	
	/**
	 * Create Button to exist the app
	 * */
	public void addExitActionListener(ActionListener al) {
		exitButton.addActionListener(al);
	}
	/**
	 * Create Staff Button 
	 * */
	public void addStaffActionListener(ActionListener al) {
		staffButton.addActionListener(al);
	}
	public void closeFrame() {
		frame.setVisible(false);
		//frame.dispose();
	}
	
	
}
