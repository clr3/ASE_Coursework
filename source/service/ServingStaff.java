package service;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

import model.CustomerOrder;
import queueExceptions.QueueEmptyException;
import utilities.Logger;
/**
 * Each Service staff member is a new thread
 * */


public class ServingStaff implements Runnable {
	
	OrderManager orderMgr = OrderManager.getInstance();
	CustomerOrder order;
	String staffName;
	AtomicBoolean running;
	private int servingTime;
	
	public ServingStaff(String sn, AtomicBoolean flag) {
		staffName = sn;
		running=flag;
	}

	public void run() {
		while (running.get()) {
			try {
				
				// order is picked up from Order queue
				order = orderMgr.acceptNextOrder();
				
				// Processing order is updated
				orderMgr.updateOrdersUnderProcessingByStaff(staffName, order);			
				Logger.getInstance().log("Order for customer "+ order.getCustomerId()+ " is being processed by "+staffName);
				Thread.sleep(servingTime);
				
				// processed order is added to delivery queue
				//orderMgr.addProcessedOrderToDeliveryQueue(order);
				
			} catch (InterruptedException e) {
				Logger.getInstance().log("Thread interupted: "+e.getMessage());
			} catch (QueueEmptyException eq) {
				Logger.getInstance().log("No More Orders to process - "+staffName+ " signing off");
				stopThread();
			}
		}
	}
	
	private int getRandomServerTimer() {
		Random r = new Random();
		int low = 1000;
		int high = 6000;
		int result = r.nextInt(high-low) + low;
		servingTime = result;
		return servingTime;
	}
	
	public void stopThread() {
		running.set(false);
		Thread.currentThread().interrupt();
	}
	/**This time will be used for the display*/
	public int getServingTime() {
		return servingTime;
	}
}
