package ua.training.cruise.exception;

public class OrderSaveException extends RuntimeException {
    public OrderSaveException(String message) {
        super(message);
    }
}
