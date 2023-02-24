package errors;

public class ManyInputsException extends RuntimeException {

//	@java.io.Serial
	private static final long serialVersionUID = 5L;

	/**
	* Constructs a {@code InvalidFunctionException} with no detail message.
	*/
	public ManyInputsException() {
		super();
	}

	/**
	* Constructs a {@code InvalidFunctionException} with the specified
	* detail message.
	*
	* @param   s   the detail message.
	*/
	public ManyInputsException(String s) {
		super(s);
	}
}