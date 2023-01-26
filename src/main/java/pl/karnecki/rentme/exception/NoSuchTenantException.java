package pl.karnecki.rentme.exception;

public class NoSuchTenantException extends RuntimeException {
    public NoSuchTenantException(final String message) {
        super(message);
    }
}
