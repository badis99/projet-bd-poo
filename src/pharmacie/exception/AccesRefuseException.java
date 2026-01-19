package pharmacie.exception;

/**
 * Thrown when a user tries to access a feature without proper permissions.
 */
public class AccesRefuseException extends Exception {
    public AccesRefuseException(String message) {
        super(message);
    }
}
