package errors;

public class BadExpression extends RuntimeException {

//	@java.io.Serial
	private static final long serialVersionUID = 6L;

	/**
	* Constructs a {@code BadExpression} with no detail message.
	*/
	public BadExpression() {
		super();
	}

	/**
	* Constructs a {@code BadExpression} with the specified
	* detail message.
	*
	* @param   s   the detail message.
	*/
	public BadExpression(String s) {
		super(s);
	}
}
