package service;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

import model.CustomerOrder;
import model.FoodItem;
import queueExceptions.QueueEmptyException;
import utilities.Logger;
import views.StaffGUI;
/**
 * Each Service staff member is a new thread
 * */


public class ServingStaff implements Runnable {
	
	/**This class is connected to the GUI so that it can be updated easily*/
	StaffGUI display;
	OrderManager orderMgr = OrderManager.getInstance();
	CustomerOrder order;
	String staffName;
	AtomicBoolean running;
	//Make Serving time = 1 min per order
	
	public ServingStaff(String sn, AtomicBoolean flag) {
		staffName = sn;
		running=flag;
	}

	public void addStaffDislplay(StaffGUI s) {
		this.display = s;
	}
	public void run() {
		while (running.get()) {
			try {
				
				// order is picked up from Order queue
				//The working orders are taken from Hashmap qith staffmember and CutomerOrder
				order = display.acceptNextOrder(this);
				
				
				// Processing order is updated
				orderMgr.updateOrdersUnderProcessingByStaff(this, order);			
				Logger.getInstance().log("Order for customer "+ order.getCustomerId()+ " is being processed by "+staffName);
				int prcessTime = findProcessTime(order);
				Logger.getInstance().log("Order for customer "+ order.getCustomerId()+ " is being processed by "
				+staffName + " process time => "+prcessTime);

				//Remove order from display
				display.acceptNextOrderFinished(this, order);
				//REMOVE ORDER FROM STAFF PROCESSING LIST
				orderMgr.updateOrdersUnderProcessingByStaff(this, order);
        
				Thread.sleep(prcessTime);

				// processed order is added to delivery queue
				orderMgr.addProcessedOrderToDeliveryQueue(order);
				
			} catch (InterruptedException e) {
				Logger.getInstance().log("Thread interupted: "+e.getMessage());
			} catch (QueueEmptyException eq) {
				Logger.getInstance().log("No More Orders to process - "+staffName+ " signing off");
				System.out.println("No More Orders to process - "+staffName+ " signing off");
				
				stopThread();
			}
		}
	}
	
	private int findProcessTime(CustomerOrder order) {
		int totalTime = 0;
		if(order.getOrderItems().size() > 0) {
			for(FoodItem fi : order.getOrderItems()) {
				totalTime += orderMgr.getAllProcessTime().get(fi.getCategory());
			}
		}
		return totalTime;
	}
	
	public void stopThread() {
		running.set(false);
		Thread.currentThread().interrupt();
	}
	public String getName(){
		return staffName;
	}
	
}
