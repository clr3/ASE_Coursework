package discountExceptions;

public class NoDiscountFoodItemsException  extends Exception{

	private static final long serialVersionUID = 1L;

	public NoDiscountFoodItemsException() {
		
		super("Discount food items not exist or wrong value");
	}
}