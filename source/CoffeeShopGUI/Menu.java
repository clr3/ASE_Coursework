package CoffeeShopGUI;
import java.util.HashMap;

public class Menu {
	
	HashMap<Integer, String> menu = new HashMap<Integer, String>();
	
	private String discounts;


	public String getDiscounts() {
		return discounts;
	}

	public void setDiscounts(String discounts) {
		this.discounts = discounts;
	}

	public HashMap<Integer, String> getMenu() {
		return menu;
	}

	public void setMenu(HashMap<Integer, String> menu) {
		this.menu = menu;
	}
}
