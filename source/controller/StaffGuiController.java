package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
	private StaffGUI staffGUI = new StaffGUI();

	public StaffGuiController() {
		
		//On clicking this button, the first customer orders should processed
	 	staffGUI.addStartServeActionListener(serveActionListener());
	 	
	}
	
	
	public ActionListener serveActionListener() {
		return new ActionListener() {
		        @Override
		         public void actionPerformed(ActionEvent e) {
		        	staffThreads.manageServingStaff();
		         }
		};
	}
}

