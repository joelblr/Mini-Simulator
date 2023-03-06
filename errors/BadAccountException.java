package errors;

public class BadAccountException extends RuntimeException {

//	@java.io.Serial
	private static final long serialVersionUID = 13L;

	/**
	* Constructs a {@code BadAccountException} with no detail message.
	*/
	public BadAccountException() {
		super();
	}

	/**
	* Constructs a {@code BadAccountException} with the specified
	* detail message.
	*
	* @param   s   the detail message.
	*/
	public BadAccountException(String s) {
		super(s);
	}
}
