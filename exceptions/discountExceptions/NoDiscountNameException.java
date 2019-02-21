package discountExceptions;

public class NoDiscountNameException  extends Exception{

	private static final long serialVersionUID = 1L;

	public NoDiscountNameException() {
		
		super("Discount name not exist or wrong value");
	}
}