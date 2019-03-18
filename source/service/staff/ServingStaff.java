package service.staff;

/**
 * @Author Sethu Lekshmy<sl1984@hw.ac.uk>
 * 
 * This class contains the logic for running the Serving Staff in individual threads.
 * Once started, the serving staff thread consumes the Order from the Queue for processing.
 *
 * 
 * */

import CoffeeShopModel.CustomerOrder;
import CoffeeShopModel.OrderManager;
import CoffeeShopUtilities.Logger;
import queueExceptions.QueueEmptyException;

public class ServingStaff implements Runnable {
	
	OrderManager orderMgr;
	CustomerOrder order;
	String staffName;
	public ServingStaff(String sn, OrderManager om) {
		orderMgr = om;
		staffName = sn;
	}

	public void run() {
		while (true) {
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
				Logger.getInstance().log(e.getMessage());
			} catch (QueueEmptyException eq) {
				Logger.getInstance().log("No More Orders to process - "+staffName+ " signing off");
				Thread.currentThread().stop();
			}
		}
	}
}
