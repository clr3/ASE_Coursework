package service.queue;
/**
 * @Author Sethu Lekshmy<sl1984@hw.ac.uk>
 * 
 * 
 * */
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

import model.CustomerOrder;
import queueExceptions.QueueEmptyException;

/**
 * @Author Sethu Lekshmy<sl1984@hw.ac.uk>
 * 
 * This class contains the logic for enqueuing and dequeuing Customer Orders which are processed and ready for delivery.
 *
 * 
 * */

public class DeliveryQueue {

	Queue<CustomerOrder> deliveryQueue;

	// Queue is initialised.
	public DeliveryQueue() {
		deliveryQueue = new ArrayBlockingQueue<CustomerOrder>(100);
	}

	/**
	 * Adds a Customer Order to the queue
	 */
	public void enqueue(CustomerOrder order) {
		deliveryQueue.add(order);
	}

	/**
	 * Adds a list of Customer Order to the queue
	 */
	public void enqueueAll(List<CustomerOrder> orders) {
		deliveryQueue.addAll(orders);
	}

	/**
	 * polls a Customer order from the priority queue. If no orders are present in
	 * the queue the a Queue Empty Exceptions is thrown
	 */
	public CustomerOrder dequeue() throws QueueEmptyException {

		CustomerOrder order = null;
		order = deliveryQueue.poll();
		if (null != order) {
			return order;
		} else {
			throw new QueueEmptyException();
		}

	}

	/**
	 * Returns the count of messages in the queue
	 */
	public ArrayList<CustomerOrder> viewAllOrders() {
		ArrayList<CustomerOrder> coaList = new ArrayList<CustomerOrder>();
		Iterator<CustomerOrder> itr = deliveryQueue.iterator();
		while (itr.hasNext()) {
			coaList.add(itr.next());
		}
		return coaList;
	}

	/**
	 * Returns the count of messages in the queue
	 */
	public int countOrdersInQueue() {
		return deliveryQueue.size();
	}

	/**
	 * Delete all messages from the queue
	 */
	public void deleteOrdersFromQueue() {
		deliveryQueue.clear();
	}

}
