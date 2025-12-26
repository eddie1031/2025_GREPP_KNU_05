package io.eddie.batchexp.C04.exception;

public class InvalidOrderDataException extends RuntimeException {

    public InvalidOrderDataException() {
        super();
    }

    public InvalidOrderDataException(String message) {
        super(message);
    }

    public InvalidOrderDataException(String message, Throwable cause) {
        super(message, cause);
    }

}
