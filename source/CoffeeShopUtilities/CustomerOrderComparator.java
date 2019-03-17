package CoffeeShopUtilities;

import java.util.Comparator;

import CoffeeShopModel.CustomerOrder;

public class CustomerOrderComparator implements Comparator<CustomerOrder> {

	@Override
	public int compare(CustomerOrder o1, CustomerOrder o2) {
		return o1.getPriority().compareTo(o2.getPriority()); 
	}
	

}
