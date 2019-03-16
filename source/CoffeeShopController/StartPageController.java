package CoffeeShopController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import CoffeeShopGUI.MenuGUI;
import CoffeeShopGUI.StaffGUI;
import CoffeeShopGUI.StartPageGUI;
import CoffeeShopModel.CustomerOrder;
import CoffeeShopModel.Menu;
import CoffeeShopModel.OrderManager;

public class StartPageController {

	private OrderManager om = new OrderManager();
	private int orderNumber = 0;
	private int customerID = 0;
	private StartPageGUI startPage;
	
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
			//Create Menu Controller With new Customer Order
			MenuController mc = new MenuController(new Menu(true), order,om);
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
