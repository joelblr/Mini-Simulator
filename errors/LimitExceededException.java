package errors;

public class LimitExceededException extends RuntimeException {

//	@java.io.Serial
	private static final long serialVersionUID = 7L;

	/**
	* Constructs a {@code LimitExceededException} with no detail message.
	*/
	public LimitExceededException() {
		super();
	}

	/**
	* Constructs a {@code LimitExceededException} with the specified
	* detail message.
	*
	* @param   s   the detail message.
	*/
	public LimitExceededException(String s) {
		super(s);
	}
}
