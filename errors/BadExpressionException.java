package errors;

public class BadExpressionException extends RuntimeException {

//	@java.io.Serial
	private static final long serialVersionUID = 6L;

	/**
	* Constructs a {@code BadExpression} with no detail message.
	*/
	public BadExpressionException() {
		super();
	}

	/**
	* Constructs a {@code BadExpression} with the specified
	* detail message.
	*
	* @param   s   the detail message.
	*/
	public BadExpressionException(String s) {
		super(s);
	}
}
