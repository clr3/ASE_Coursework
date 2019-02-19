package CoffeeShopGUI;

import CoffeeShopUtilities.Menu;
import javax.swing.JFrame;

public class MainCoffeeShopGUI {

	public MainCoffeeShopGUI(Menu menu_obj) {
		int mainFrameWidth = 300;
		int mainFrameHight = 400;
		
		JFrame frame = new JFrame("Coffee Shop App");
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		frame.setSize(mainFrameWidth , mainFrameHight);
		
		frame.setContentPane(new StartPageGUI(menu_obj, frame));
		frame.setVisible(true);
	}
	

	public static void main(String args[]) {
		Menu menu_obj = new Menu();
		MainCoffeeShopGUI main = new MainCoffeeShopGUI(menu_obj);
		
	}
	
	
}
