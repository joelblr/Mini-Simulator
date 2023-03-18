package errors;


public class BadTransactionException extends RuntimeException {

//	@java.io.Serial
	private static final long serialVersionUID = 14L;

	/**
	* Constructs a {@code BadTransactionException} with no detail message.
	*/
	public BadTransactionException() {
		super();
	}

	/**
	* Constructs a {@code BadTransactionException} with the specified
	* detail message.
	*
	* @param   s   the detail message.
	*/
	public BadTransactionException(String s) {
		super(s);
	}
}
