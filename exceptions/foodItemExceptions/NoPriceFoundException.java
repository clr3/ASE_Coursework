package foodItemExceptions;

@SuppressWarnings("serial")
public class NoPriceFoundException extends Exception{

	public NoPriceFoundException() {
		
		super("Missing item Price");
	}
}
