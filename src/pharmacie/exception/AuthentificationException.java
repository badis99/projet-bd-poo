package pharmacie.exception;

/**
 * Thrown when authentication fails (wrong credentials, user not found).
 */
public class AuthentificationException extends Exception {
    public AuthentificationException(String message) {
        super(message);
    }
}
