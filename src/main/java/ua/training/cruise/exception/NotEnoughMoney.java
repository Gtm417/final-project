package ua.training.cruise.exception;

public class NotEnoughMoney extends RuntimeException {
    public NotEnoughMoney(String message) {
        super(message);
    }
}
