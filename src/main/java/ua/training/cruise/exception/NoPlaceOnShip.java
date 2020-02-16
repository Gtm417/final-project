package ua.training.cruise.exception;

public class NoPlaceOnShip extends RuntimeException {
    private long id;

    public NoPlaceOnShip(String message, Long id) {
        super(message + id);
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
