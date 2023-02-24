package errors;

public class InvalidFunctionException extends RuntimeException {

//	@java.io.Serial
	private static final long serialVersionUID = 1L;

	/**
	* Constructs a {@code InvalidFunctionException} with no detail message.
	*/
	public InvalidFunctionException() {
		super();
	}

	/**
	* Constructs a {@code InvalidFunctionException} with the specified
	* detail message.
	*
	* @param   s   the detail message.
	*/
	public InvalidFunctionException(String s) {
		super(s);
	}
}
