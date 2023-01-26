package pl.karnecki.rentme.exception;

public class NoSuchAccommodationException extends RuntimeException {
    public NoSuchAccommodationException(final String message) {
        super(message);
    }
}
