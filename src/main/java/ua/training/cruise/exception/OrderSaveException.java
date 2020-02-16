package ua.training.cruise.exception;

public class OrderSaveException extends RuntimeException {
    public OrderSaveException(String message, Throwable cause) {
        super(message, cause);
    }
}
