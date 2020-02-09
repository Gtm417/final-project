package ua.training.cruise.exception;

public class NoPlaceOnShip extends Exception {
    public NoPlaceOnShip(String message, Long id) {
        super(message + id);
    }
}
