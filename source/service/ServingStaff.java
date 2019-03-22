package service;

import java.util.concurrent.atomic.AtomicBoolean;

import model.CustomerOrder;
import model.FoodItem;
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

	@Override
	public void run() {
		while (running.get()) {
			try {
				CustomerOrder tempOrder =new CustomerOrder();
				// order is picked up from Order queue
				order = orderMgr.fetchOrderFromQueue();
				
				// Processing order is updated
				orderMgr.updateOrdersUnderProcessingByStaff(staffName, order);			
				Logger.getInstance().log("Order for customer "+ order.getCustomerId()+ " is being processed by "+staffName);
				//Thread.sleep(getRandomServerTimer());
				int prcessTime = findProcessTime(order);
				Logger.getInstance().log("Order for customer "+ order.getCustomerId()+ " is being processed by "
									+staffName + " process time => "+prcessTime);
				Thread.sleep(prcessTime);
				// processed order is added to delivery queue
				orderMgr.addProcessedOrderToDeliveryQueue(order);
				orderMgr.updateOrdersUnderProcessingByStaff(staffName, tempOrder);
			} catch (InterruptedException e) {
				Logger.getInstance().log("Thread interupted: "+e.getMessage());
			} catch (QueueEmptyException eq) {
				Logger.getInstance().log("No More Orders to process - "+staffName+ " signing off");
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
}
