package service;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.atomic.AtomicBoolean;

import utilities.Logger;

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
	private final AtomicBoolean t1running = new AtomicBoolean(true);
	private final AtomicBoolean t2running = new AtomicBoolean(true);
	private final AtomicBoolean temprunning = new AtomicBoolean(true);
	public StaffManager (OrderManager oMgr) {
		orderManager = oMgr;
	}
	
	/**
	 * Manage the serving staff. 
	 * Each staff is created on an individual thread.
	 * */
	public void manageServingStaff() {
	
		// creates and starts a thread for staff Joe
		Thread tStaff1 = new Thread(new ServingStaff("Staff - Joe Green", orderManager, t1running));
		tStaff1.start();
		
		// creates and starts a thread for staff Anna
		Thread tStaff2 = new Thread(new ServingStaff("Staff - Anna Donald", orderManager,t2running));
		tStaff2.start();
		
		while (t1running.get() && t2running.get()) {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		orderManager.staffGui.reRenderQueue();
		orderManager.writeReports();
		Logger.getInstance().log("Order Reports generated! Check file order_summary.csv");
	
	}
	
	
	public ActionListener serveActionListener() {
		return new ActionListener() {
		        @Override
				public void actionPerformed(ActionEvent e) {
					new Thread() {
						@Override
						public void run() {
							try {
								manageServingStaff();
							} catch (Exception e) {
								e.printStackTrace();
							}

						}
					}.start();
		        }
		    };
		}
	/**
	 * Add a new staff on an individual thread.
	 * */
	public void addAdditionalServingStaff() {
		// creates and starts a thread for staff Liz
		tTempStaff = new Thread(new ServingStaff("Staff - Liz Atkinson", orderManager,temprunning));
		tTempStaff.start();
	}
	
	/**
	 * Add a new staff on an individual thread.
	 * */
	@SuppressWarnings("deprecation")
	public void removeAdditionalServingStaff() {
		// stops thread for staff Liz
		if (tTempStaff.isAlive()) {
			tTempStaff.stop();
		}
	}
	
	public ActionListener showTimerPageActionListener(OrderManager om) {
		return new ActionListener() {
	        @Override
	         public void actionPerformed(ActionEvent e) {
	        	om.timerPage.showTimerPage();
	         }
	    };
	}
	
	
	
	
}
