package CoffeeShopController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import CoffeeShopGUI.MenuGUI;
import CoffeeShopGUI.StartPageGUI;
import CoffeeShopUtilities.CustomerOrder;
import CoffeeShopUtilities.OrderManager;

public class OrderManagerController {

	private OrderManager om = new OrderManager();
	private int orderNumber = 0;
	private int customerID = 0;
	private StartPageGUI startPage;
	
	public OrderManagerController(StartPageGUI p) {
		
		this.startPage = p;
		startPage.setCustomerButtonActionListener(new NewOrder());
		startPage.addExitActionListener(new WriteReport());
		

	}
	
	/**
	 * Provisionally create a new order number and a number for the customer.
	 * Name of the customer could be asked before ordering
	 * */
	public class NewOrder implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
			//Create a New order
			String orderNo = Integer.toString(orderNumber++);
			String cId = Integer.toString(customerID++);
			
			CustomerOrder order = new CustomerOrder(orderNo, cId);
			om.submitNewOrder(Integer.toString(orderNumber),order);
			// Open Menu Page 
						MenuGUI menu_gui = new MenuGUI(om,order);
						menu_gui.showMenuPage();
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
	
	
}
