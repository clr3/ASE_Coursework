package foodItemExceptions;

public class NoItemNameFoundException extends Exception {

	public NoItemNameFoundException() {
		
		super("Missing item Name");
	}
}
