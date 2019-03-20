package controller;

import service.StaffManager;
import views.StaffGUI;

/**
 * @After StaffGUI is opened
 * 
 * - Start staff threads
 * - show updates as the orders are added and removed from the queue
 * 
 * */
public class StaffGuiController {

	private StaffManager staffThreads = new StaffManager();
	private StaffGUI staff = new StaffGUI();

	public StaffGuiController() {
	 	
	}
}
