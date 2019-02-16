package CoffeeShopGUI;
import java.awt.*;
import javax.swing.*;


/**
 * @Author Cristina Rivera 	<clr3@hw.ac.uk>
 * 
 * JPanel which provides access to: 
 *  				menu (for customers)
 * 					staff view 
 * */

public class StartPageGUI extends JPanel{
	
	private GridBagConstraints constraints = new GridBagConstraints();
	
	private JButton staffButton;
	private JButton customerButton;

	
	
	/**Initialise the buttons */
	public StartPageGUI() {
		setLayout(new GridBagLayout());
		staffButton = new JButton("Staff");
		customerButton = new JButton("View Menu");
		
		
		
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
	
	
}
