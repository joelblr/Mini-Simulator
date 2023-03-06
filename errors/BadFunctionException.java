package errors;

public class BadFunctionException extends RuntimeException {

//	@java.io.Serial
	private static final long serialVersionUID = 1L;

	/**
	* Constructs a {@code InvalidFunctionException} with no detail message.
	*/
	public BadFunctionException() {
		super();
	}

	/**
	* Constructs a {@code InvalidFunctionException} with the specified
	* detail message.
	*
	* @param   s   the detail message.
	*/
	public BadFunctionException(String s) {
		super(s);
	}
}
