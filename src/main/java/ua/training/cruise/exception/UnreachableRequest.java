package ua.training.cruise.exception;

public class UnreachableRequest extends RuntimeException {
    public UnreachableRequest(String message) {
        super(message);
    }
}
