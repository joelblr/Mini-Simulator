package errors;

public class InvalidOperatorException extends RuntimeException {

//	@java.io.Serial
	private static final long serialVersionUID = 2L;

	/**
	* Constructs a {@code InvalidFunctionException} with no detail message.
	*/
	public InvalidOperatorException() {
		super();
	}

	/**
	* Constructs a {@code InvalidFunctionException} with the specified
	* detail message.
	*
	* @param   s   the detail message.
	*/
	public InvalidOperatorException(String s) {
		super(s);
	}
}
