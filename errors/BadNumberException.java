package errors;

public class BadNumberException extends RuntimeException {

//	@java.io.Serial
	private static final long serialVersionUID = 3L;

	/**
	* Constructs a {@code BadNumberException} with no detail message.
	*/
	public BadNumberException() {
		super();
	}

	/**
	* Constructs a {@code BadNumberException} with the specified
	* detail message.
	*
	* @param   s   the detail message.
	*/
	public BadNumberException(String s) {
		super(s);
	}
}
