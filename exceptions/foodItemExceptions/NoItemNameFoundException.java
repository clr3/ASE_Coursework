package foodItemExceptions;

@SuppressWarnings("serial")
public class NoItemNameFoundException extends Exception {

	public NoItemNameFoundException() {
		
		super("Missing item Name");
	}
}
