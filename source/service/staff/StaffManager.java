package service.staff;

import CoffeeShopModel.OrderManager;
import CoffeeShopUtilities.Logger;

/**
 * @Author Sethu Lekshmy<sl1984@hw.ac.uk>
 * 
 * This class contains the logic for creating multiple serving staffs in individual threads.
 * On start up of this class 2 serving staff threads are started.
 * This class also provides methods for adding temporary staff when the workload is more.
 * Remove staff will take this temporary stAff off from work
 *
 * 
 * */
public class StaffManager {
	OrderManager orderManager;
	Thread tTempStaff;
	public StaffManager (OrderManager oMgr) {
		orderManager = oMgr;
		
	}
	
	/**
	 * Manage the serving staff. 
	 * Each staff is created on an individual thread.
	 * */
	public void manageServingStaff() {
	
		// creates and starts a thread for staff Joe
		Thread tStaff1 = new Thread(new ServingStaff("Staff - Joe Green", orderManager));
		tStaff1.start();
		
		// creates and starts a thread for staff Anna
		Thread tStaff2 = new Thread(new ServingStaff("Staff - Anna Donald", orderManager));
		tStaff2.start();
		
		while (tStaff1.isAlive() && tStaff2.isAlive()) {
			try {
				Thread.sleep(60000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		orderManager.writeReports();
		Logger.getInstance().log("Order Reports generated! Check file order_summary.csv");
	
	}
	
	/**
	 * Add a new staff on an individual thread.
	 * */
	public void addAdditionalServingStaff() {
		// creates and starts a thread for staff Liz
		tTempStaff = new Thread(new ServingStaff("Staff - Liz Atkinson", orderManager));
		tTempStaff.start();
	}
	
	/**
	 * Add a new staff on an individual thread.
	 * */
	public void removeAdditionalServingStaff() {
		// stops thread for staff Liz
		if (tTempStaff.isAlive()) {
			tTempStaff.stop();
		}
	}
	
}
