package prj.searching.exception;

import prj.searching.constant.StatusCodeMessageConstant;

public class ApplicationException extends RuntimeException {
    private static final long serialVersionUID = 1992L;
    private final String message;
    private final String statusCodeMessage;

    public ApplicationException(String message) {
        this(message, StatusCodeMessageConstant.EXCEPTION_FAILED);
    }

    public ApplicationException(String message, String statusCodeMessage) {
        this.message = message;
        this.statusCodeMessage = statusCodeMessage;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    public String getStatusCodeMessage() {
        return statusCodeMessage;
    }
}
