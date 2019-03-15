package CoffeeShopController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import CoffeeShopGUI.MenuGUI;
import CoffeeShopGUI.StaffGUI;
import CoffeeShopGUI.StartPageGUI;
import CoffeeShopUtilities.CustomerOrder;
import CoffeeShopUtilities.Menu;
import CoffeeShopUtilities.OrderManager;

public class StartPageController {

	private OrderManager om = new OrderManager();
	private int orderNumber = 0;
	private int customerID = 0;
	private StartPageGUI startPage;
	private MenuController mc = new MenuController(new Menu(true), om);
	
	private StaffGUI staff = new StaffGUI(om);

	public StartPageController(StartPageGUI p) {
		
		this.startPage = p;
		startPage.setCustomerButtonActionListener(new NewOrder());
		startPage.addExitActionListener(new WriteReport());
		startPage.addStaffActionListener(new StaffView());

	}
	
	/**
	 * Provisionally create a new order number and a number for the customer.
	 * Name of the customer could be asked before ordering
	 * */
	public class NewOrder implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			System.out.println("MENU Selected..");

			//Create a New order
			String orderNo = Integer.toString(orderNumber++);
			String cId = Integer.toString(customerID++);
			
			CustomerOrder order = new CustomerOrder(orderNo, cId);
			//om.submitNewOrder(Integer.toString(orderNumber),order);
			// Open and Display Menu Page 
			mc.showMenuPage();

		}
		
	}
	
	public class WriteReport implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			// print summary report to summary_report.csv
			om.writeReports();
			System.out.println("Order summary report is saved to summary_report.csv");
			startPage.closeFrame();
		}
		
	}
	
	public class StaffView implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			//Will show staff view
			System.out.println("Staff");
			staff.showStaffView();
		}
		
	}
}
