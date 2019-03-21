package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.FoodCategory;
import service.OrderManager;
import service.StaffManager;
import service.TimerGUI;
import views.StaffGUI;

/**
 * @After StaffGUI is opened
 * 
 * - Start staff threads
 * - show updates as the orders are added and removed from the queue
 * 
 * */
public class StaffGuiController {

	private OrderManager om = OrderManager.getInstance();
	//Create new staffManager
	private StaffManager staffThreads = new StaffManager();
	//Create new StaffGUI
	private StaffGUI staffGUI = new StaffGUI(staffThreads);
	
	private TimerGUI timerPage = new TimerGUI(staffThreads, this);
	

	public StaffGuiController() {
		//On clicking this button, the first customer orders should processed
	 	staffGUI.addStartServeActionListener(serveActionListener());
	 	//Button for timer mage gui
	 	staffGUI.addProcessTimeActionListener(timerActionListener());
	 	staffThreads.addDislplay(staffGUI);
	 	
	}
	public StaffGUI getGUI() {
		return staffGUI;
	}
	
	public ActionListener serveActionListener() {
		return new ActionListener() {
		        @Override
		         public void actionPerformed(ActionEvent e) {
		        	staffThreads.manageServingStaff();
		         }
		};
	}
	public ActionListener timerActionListener() {
		return new ActionListener() {
		        @Override
		         public void actionPerformed(ActionEvent e) {
		        	timerPage.showTimerPage();
		         }
		};
	}
	
	public ActionListener processTimeActionListener(FoodCategory category) {
		return new ActionListener() {
	        @Override
	         public void actionPerformed(ActionEvent e) {
	        	om.setProcessTime(timerPage, category);
	         }
	    };
	}
}

