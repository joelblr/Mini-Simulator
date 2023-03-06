package errors;

public class BadPhoneNumberException extends RuntimeException {

//	@java.io.Serial
	private static final long serialVersionUID = 11L;

	/**
	* Constructs a {@code BadPhoneNumber} with no detail message.
	*/
	public BadPhoneNumberException() {
		super();
	}

	/**
	* Constructs a {@code BadPhoneNumber} with the specified
	* detail message.
	*
	* @param   s   the detail message.
	*/
	public BadPhoneNumberException(String s) {
		super(s);
	}
}
