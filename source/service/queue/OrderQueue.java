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
import utilities.Logger;

/**
 * @Author Sethu Lekshmy<sl1984@hw.ac.uk>
 * 
 * This class contains the logic for enqueuing and dequeuing Customer Orders to
 * a Priority Queue in a thread safe manner.
 *
 * 
 * */

public class OrderQueue {

	Queue<CustomerOrder> orderQueue;

	// Priority Queue is initialised.
	public OrderQueue() {
		orderQueue = new ArrayBlockingQueue<CustomerOrder>(100);
	}

	/**
	 * Adds a Customer Order to the queue
	 */
	public void enqueue(CustomerOrder order) {
		Logger.getInstance().log("Customer order added to Queue");
		orderQueue.add(order);
	}

	/**
	 * Adds a list of Customer Order to the queue
	 */
	public void enqueueAll(List<CustomerOrder> orders) {
		Logger.getInstance().log("All customer orders added to Queue");
		orderQueue.addAll(orders);
	}

	/**
	 * polls a Customer order from the queue. If no orders are present in
	 * the queue the a Queue Empty Exceptions is thrown
	 */
	public CustomerOrder dequeue() throws QueueEmptyException {
		Logger.getInstance().log("Customer order Dequeued");
		CustomerOrder order = null;
		order = orderQueue.poll();
		if (null != order) {
			return order;
		} else {
			throw new QueueEmptyException();
		}

	}

	/**
	 * Returns all the Customer Orders in the queue
	 */
	public ArrayList<CustomerOrder> viewAllOrders() {
		ArrayList<CustomerOrder> coaList = new ArrayList<CustomerOrder>();
		Iterator<CustomerOrder> itr = orderQueue.iterator();
		while (itr.hasNext()) {
			coaList.add(itr.next());
		}
		return coaList;
	}

	/**
	 * Returns the count of Customer Orders in the queue
	 */
	public int countOrdersInQueue() {
		return orderQueue.size();
	}

	/**
	 * Delete all Customer Orders from the queue
	 */
	public void deleteOrdersFromQueue() {
		Logger.getInstance().log("Delete all customer orders from queue");
		orderQueue.clear();
	}

}
