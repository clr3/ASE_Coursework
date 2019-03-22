package queueExceptions;

@SuppressWarnings("serial")
public class QueueEmptyException extends Exception{

	public QueueEmptyException() {
		super("No Orders present in the Queue");
	}

}
