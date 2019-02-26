package CoffeeShopController;

import CoffeeShopGUI.*;
import CoffeeShopUtilities.*;

public class OrderController {

	MenuGUI menuGui;
	CustomerOrder order;
	
public OrderController(MenuGUI m, CustomerOrder o) {
		this.order = o;
		this.menuGui = m;
		
		
	}
}
