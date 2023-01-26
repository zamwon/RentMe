package pl.karnecki.rentme.exception;

public class PlaceNotAvailableException extends RuntimeException {

    public PlaceNotAvailableException(final String message) {
        super(message);
    }
}
