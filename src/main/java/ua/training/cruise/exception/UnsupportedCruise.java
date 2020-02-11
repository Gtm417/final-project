package ua.training.cruise.exception;

public class UnsupportedCruise extends Exception {
    public UnsupportedCruise() {
    }

    public UnsupportedCruise(String message, long id) {
        super(message + id);
    }
}
