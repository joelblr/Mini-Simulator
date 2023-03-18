package errors;

public class InputsFreqException extends RuntimeException {

//	@java.io.Serial
	private static final long serialVersionUID = 5L;

	/**
	* Constructs a {@code ManyInputsException} with no detail message.
	*/
	public InputsFreqException() {
		super();
	}

	/**
	* Constructs a {@code ManyInputsException} with the specified
	* detail message.
	*
	* @param   s   the detail message.
	*/
	public InputsFreqException(String s) {
		super(s);
	}
}