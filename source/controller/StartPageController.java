package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.CustomerOrder;
import model.Menu;
import service.OrderManager;
import service.StaffManager;
import views.StaffGUI;
import views.StartPageGUI;
import views.TimerGUI;

public class StartPageController {

	/**
	 * @Comments Cristina Rivera
	 * OrderManager should use singleton Pattern To save RunTime
	 * Passing The OrderManager from method to method is too inefficient
	 * */
	private OrderManager om = new OrderManager();
	private int orderNumber = 0;
	private int customerID = 0;
	private StartPageGUI startPage;
	
	private StaffManager sm = new StaffManager(om);
	private TimerGUI timerGui = new TimerGUI(om, sm);
	private StaffGUI staff = new StaffGUI(om, sm, timerGui);


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
			MenuController mc = new MenuController(Menu.getInstance(), order,om);
			mc.showMenuPage();

		}
		
	}
	
	public class WriteReport implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			// print summary report to summary_report.csv
			om.writeReports();
			String exit = "Order summary report is saved to summary_report.csv";
			System.out.println(exit);
			startPage.closeFrame(exit);
			System.exit(0);
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
