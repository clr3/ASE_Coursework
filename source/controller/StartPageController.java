package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.CustomerOrder;
import model.Menu;
import service.OrderManager;
import service.StaffManager;
import service.TimerGUI;
import views.StaffGUI;
import views.StartPageGUI;


/**
 * 
 * */
public class StartPageController {

	
	/**
	
	 *	The following controls are in this class:
	 *
	 *	- Create a new menuGUI, attached to a new Customer Order
	 *	- show StaffGUI
	 *	- close the program and write OrderHistory
	 *
	 * */
	
	 /* Singletons and Threads are used by the controllers. 	 */
	private OrderManager om = OrderManager.getInstance();
	private int orderNumber = 0;
	private int customerID = 0;
	private StartPageGUI startPage;
	
	//private StaffManager sm = new StaffManager();
	
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

			
			//Create Menu Controller With new Customer Order
			MenuController mc = new MenuController(om.createNewOrder());

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
	/**
	 * Show staff GUI
	 * */
	public class StaffView implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			//Will show staff view
			System.out.println("Staff");
			
			StaffGuiController c = new StaffGuiController();
			c.getGUI().showStaffView();
			//StaffGUI staff = new StaffGUI();
			//staff.showStaffView();
		}
		
	}
}
