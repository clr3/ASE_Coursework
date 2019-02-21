package discountExceptions;

public class NoDiscountIdException extends Exception{

	private static final long serialVersionUID = 1L;

	public NoDiscountIdException() {
		
		super("Discount id not exist or wrong value");
	}
}