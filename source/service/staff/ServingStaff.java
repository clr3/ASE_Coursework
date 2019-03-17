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
				
				System.out.println("Total Orders on Order Queue = "+orderMgr.viewAllOrdersOnQueue().size());
				
				order = orderMgr.fetchOrderFromQueue();
				
				System.out.println("Order for customer "+ order.getCustomerId()+ " is being processed by "+staffName);
				Thread.sleep(10000);
				
				orderMgr.addProcessedOrderToDeliveryQueue(order);
				
				System.out.println("Total Processed Orders on Delivery Queue = "+orderMgr.viewAllOrdersOnDeliveryQueue().size());
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
			} catch (QueueEmptyException eq) {
				System.out.println("No More Orders to process");
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
