package mesosphere.marathon.client.utils;

public class MarathonException extends Exception {
	private static final long serialVersionUID = 1L;

	public final int statusCode;

	public MarathonException(int statusCode, String message) {
		super(String.format("%s (HTTP status: %d)", message, statusCode));
		this.statusCode = statusCode;
	}

	@Override
	public String toString() {
		return getMessage();
	}
}
