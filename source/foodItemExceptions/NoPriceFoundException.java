package foodItemExceptions;

public class NoPriceFoundException extends Exception{

	public NoPriceFoundException() {
		
		super("Missing item Price");
	}
}
