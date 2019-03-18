package service;

import java.util.concurrent.atomic.AtomicBoolean;

import model.CustomerOrder;
import queueExceptions.QueueEmptyException;
import utilities.Logger;

public class ServingStaff implements Runnable {
	
	OrderManager orderMgr;
	CustomerOrder order;
	String staffName;
	AtomicBoolean running;
	public ServingStaff(String sn, OrderManager om, AtomicBoolean flag) {
		orderMgr = om;
		staffName = sn;
		running=flag;
	}

	public void run() {
		while (running.get()) {
			try {
				
				// order is picked up from Order queue
				order = orderMgr.fetchOrderFromQueue();
				
				// Processing order is updated
				orderMgr.updateOrdersUnderProcessingByStaff(staffName, order);			
				Logger.getInstance().log("Order for customer "+ order.getCustomerId()+ " is being processed by "+staffName);
				Thread.sleep(60000);
				
				// processed order is added to delivery queue
				orderMgr.addProcessedOrderToDeliveryQueue(order);
				
			} catch (InterruptedException e) {
				Logger.getInstance().log("Thread interupted: "+e.getMessage());
			} catch (QueueEmptyException eq) {
				Logger.getInstance().log("No More Orders to process - "+staffName+ " signing off");
				stopThread();
			}
		}
	}
	
	public void stopThread() {
		running.set(false);
		Thread.currentThread().interrupt();
	}
}