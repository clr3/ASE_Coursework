package CoffeeShopGUI;

import CoffeeShopUtilities.Menu;

public class MainCoffeeShopGUI {
 
	public static void main(String args[]) {

		
		Menu menu_obj = new Menu();
		
		System.out.println(menu_obj);
		
		StartPageGUI start = new StartPageGUI();
		start.show_start_page();		
	}
}
