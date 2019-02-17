package customerOrderExceptions;

public class NoOrderIdException extends Exception{

	public NoOrderIdException() {
		
		super("No Order ID given");
	}
}
