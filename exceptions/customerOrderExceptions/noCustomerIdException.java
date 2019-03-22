package customerOrderExceptions;

@SuppressWarnings("serial")
public class noCustomerIdException extends Exception{
public noCustomerIdException() {
		
		super("No cutomer ID");
	}
}
