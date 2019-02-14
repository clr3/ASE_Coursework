package CoffeeShopGUI;

import javax.swing.*;

public class StartPageGUI {
	
	private int mainFrameHight = 400;
	private int mainFrameWidth = 300;
	
	
	public StartPageGUI() {}
	
	/**
	 * Frame which provides access to: 
	 * 				menu (for customers)
	 * 				staff view 
	 * 
	 * |---------------|
	 * |			   |
	 * |    Staff	   |
	 * |   Customer    |
	 * |---------------|
	 * */
	public void show_start_page() {
		JFrame frame = new JFrame("Coffee Shop Start");		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	
		frame.setSize(mainFrameWidth , mainFrameHight);
			
		
		JButton staffButton = new JButton("Staff");
		JButton customerButton = new JButton("Customer");
		
		frame.getContentPane().add(customerButton);
		frame.getContentPane().add(staffButton);
		frame.setVisible(true);

	}
}
