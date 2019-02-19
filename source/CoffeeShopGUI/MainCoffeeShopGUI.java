package CoffeeShopGUI;

import CoffeeShopUtilities.Menu;
import javax.swing.JFrame;

public class MainCoffeeShopGUI {

	public MainCoffeeShopGUI() {
		int mainFrameWidth = 300;
		int mainFrameHight = 400;
		
		JFrame frame = new JFrame("Coffee Shop Start");
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		frame.setSize(mainFrameWidth , mainFrameHight);
		
		frame.setContentPane(new StartPageGUI());
		frame.setVisible(true);
	}
	

	public static void main(String args[]) {
		
		MainCoffeeShopGUI main = new MainCoffeeShopGUI();
		
	}
	
	
}
