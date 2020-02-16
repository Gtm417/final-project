package ua.training.cruise.exception;

public class EntityNotFound extends RuntimeException {
    public EntityNotFound(String message, long id) {
        super(message + id);
    }
}
