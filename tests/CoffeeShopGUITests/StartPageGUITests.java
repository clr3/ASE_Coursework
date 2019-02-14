package CoffeeShopGUITests;

import org.junit.jupiter.api.Test;

import CoffeeShopGUI.StartPageGUI;

class StartPageGUITests {

	@Test
	void show_start_page_test() {
		StartPageGUI page = new StartPageGUI();
		page.show_start_page();
	}

}
