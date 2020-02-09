package ua.training.cruise.exception;

public class NotFoundExcursion extends Exception {
    public NotFoundExcursion(String message, long id) {
        super(message + id);
    }
}
