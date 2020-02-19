package ua.training.cruise.exception;

public class UnsupportedCruise extends RuntimeException {
    public UnsupportedCruise() {
    }

    public UnsupportedCruise(String message, long id) {
        super(message + id);
    }
}
