package CoffeeShopGUI;

import javax.swing.JFrame;

import CoffeeShopUtilities.Logger;

public class MainCoffeeShopGUI {
		
	JFrame frame = new JFrame("Coffee Shop App");
	
	public void startMainGUI() {
		int mainFrameWidth = 300;
		int mainFrameHight = 300;
		
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		frame.setSize(mainFrameWidth , mainFrameHight);
		
		frame.setContentPane(new StartPageGUI(frame));
		
		frame.setVisible(true);
	}
	

	public static void main(String args[]) {
		Logger.getInstance().log("Coffee shop GUI Starts");
		MainCoffeeShopGUI main = new MainCoffeeShopGUI();
		main.startMainGUI();

		
	}
	
	
}
