package service;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

import utilities.Logger;
import views.TimerGUI;

/**
 * 
 * THREADS FOR STAFF
 * @Author Sethu Lekshmy<sl1984@hw.ac.uk>
 * 
 * This class contains the logic for creating multiple serving staffs in individual threads.
 * On start up of this class 2 serving staff threads are started.
 * This class also provides methods for adding temporary staff when the workload is more.
 * Remove staff will take this temporary stAff off from work
 *
 *
 *@Edits Cristina Rivera
 *
 *
 * 
 * */


public class StaffManager {
	

	
	
	OrderManager orderManager = OrderManager.getInstance();
	
	
	private final AtomicBoolean t1running = new AtomicBoolean(true);
	private final AtomicBoolean t2running = new AtomicBoolean(true);
	private final AtomicBoolean temprunning = new AtomicBoolean(true);
	/**Create staff members before Starting
	 * BAD PRACTICE
	 * make public so they can be accesed
	 * */
	private ServingStaff s1 = new ServingStaff("Joe Green",t1running);
	private ServingStaff s2 = new ServingStaff("Anna Donald", t2running);
	private ServingStaff s3 = new ServingStaff("Liz Atkinson",temprunning);
	
	private ArrayList<ServingStaff> s = new ArrayList<ServingStaff>();
	
	/**CreateEmpty Threads Before Starting*/
	Thread tStaff1;
	Thread tStaff2;
	Thread tTempStaff;

	
	
	public StaffManager () {	
		s.add(s1);
		s.add(s2);
		s.add(s3);
	}
	
	/**
	 * Manage the serving staff. 
	 * Each staff is created on an individual thread.
	 * */
	public void manageServingStaff() {
	
		// creates and starts a thread for staff Joe
		tStaff1 = new Thread(s1);
		tStaff1.start();
		
		// creates and starts a thread for staff Anna
		tStaff2 = new Thread(s2);
		tStaff2.start();
		
		while (t1running.get() && t2running.get()) {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		orderManager.writeReports();
		Logger.getInstance().log("Order Reports generated! Check file order_summary.csv");
	
	}
	

	public ActionListener serveActionListener() {
		return new ActionListener() {
		        @Override
				public void actionPerformed(ActionEvent e) {
					new Thread() {
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
	public void addAdditionalServingStaff(ServingStaff s) {
		// creates and starts a thread for staff Liz
		tTempStaff = new Thread(s);
		tTempStaff.start();
	}
	
	/**
	 * Add a new staff on an individual thread.
	 * */
	public void removeTempServingStaff() {
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
	

	public ArrayList<ServingStaff> getStaffList(){
		return s;
	}
	

}
