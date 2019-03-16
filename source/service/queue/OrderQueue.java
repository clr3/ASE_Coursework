package service.queue;

import java.util.List;
import java.util.Queue;

/**
 * @Author Sethu Lekshmy<sl1984@hw.ac.uk>
 * 
 * This class contains the logic for enqueuing and dequeuing Customer Orders to
 * a Priority Queue in a thread safe manner.
 *
 * 
 * */

import java.util.concurrent.PriorityBlockingQueue;

import CoffeeShopModel.CustomerOrder;
import queueExceptions.QueueEmptyException;

public class OrderQueue {
	
	Queue<CustomerOrder> orderPriorityQueue;
	
	// Priority Queue is initialised.
	public OrderQueue() {
		orderPriorityQueue = new PriorityBlockingQueue<CustomerOrder>();
	}
	
	
	/**
	 * Adds a Customer Order to the priority queue
	 * */
	public void enqueue(CustomerOrder order) {
		orderPriorityQueue.add(order);
	}
	
	/**
	 * Adds a list of Customer Order to the priority queue
	 * */
	public void enqueueAll(List<CustomerOrder> orders) {
		orderPriorityQueue.addAll(orders);
	}
	
	/**
	 * polls a Customer order from the priority queue. If no orders are present in the queue
	 * the a Queue Empty Exceptions is thrown
	 * */
	public CustomerOrder dequeue() throws QueueEmptyException{
		
		CustomerOrder order = null;
		order = orderPriorityQueue.poll();
		if (null != order) {
			return order;
		} else {
			throw new QueueEmptyException();
		}
		
	}
	
	/**
	 * Returns the count of messages in the priority queue
	 * */
	public int countOrdersInQueue() {
		return orderPriorityQueue.size();
	}
	
	/**
	 * Delete all messages from the priority queue
	 * */
	public void deleteOrdersFromQueue() {
		orderPriorityQueue.clear();
	}

}
