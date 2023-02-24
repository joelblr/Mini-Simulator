package errors;

public class BadNumberException extends RuntimeException {

//	@java.io.Serial
	private static final long serialVersionUID = 3L;

	/**
	* Constructs a {@code InvalidFunctionException} with no detail message.
	*/
	public BadNumberException() {
		super();
	}

	/**
	* Constructs a {@code InvalidFunctionException} with the specified
	* detail message.
	*
	* @param   s   the detail message.
	*/
	public BadNumberException(String s) {
		super(s);
	}
}
