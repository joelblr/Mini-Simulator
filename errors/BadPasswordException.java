package errors;

public class BadPasswordException extends RuntimeException {

//	@java.io.Serial
	private static final long serialVersionUID = 12L;

	/**
	* Constructs a {@code BadPasswordException} with no detail message.
	*/
	public BadPasswordException() {
		super();
	}

	/**
	* Constructs a {@code BadPasswordException} with the specified
	* detail message.
	*
	* @param   s   the detail message.
	*/
	public BadPasswordException(String s) {
		super(s);
	}
}
