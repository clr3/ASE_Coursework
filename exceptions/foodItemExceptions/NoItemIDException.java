package foodItemExceptions;

@SuppressWarnings("serial")
public class NoItemIDException extends Exception {
	
	public NoItemIDException() {
		
		super("Missing item ID");
	}
}
