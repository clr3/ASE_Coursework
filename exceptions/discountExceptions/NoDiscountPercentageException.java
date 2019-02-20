package discountExceptions;

public class NoDiscountPercentageException extends Exception{

	private static final long serialVersionUID = 1L;

	public NoDiscountPercentageException() {
		
		super("Discount percentage not exist or wrong value");
	}
}
