package customerOrderExceptions;

@SuppressWarnings("serial")
public class noOrderItemException extends Exception{
public noOrderItemException() {
		
		super("No Item given for the order");
		
	}
}
