package modern.exception;

import modern.constants.HttpStatusConstants;

public class CustomException extends RuntimeException {
    private final String message;
    private final String statusCode;

    public CustomException(String message, String statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }

    public CustomException(String message, String statusCode, Throwable throwable) {
        super(throwable);
        this.message = message;
        this.statusCode = statusCode;
    }

    public CustomException(String message) {
        this(message, HttpStatusConstants.FAIL);
    }

    public CustomException(String message, Throwable throwable) {
        this(message, HttpStatusConstants.FAIL, throwable);
    }
}
