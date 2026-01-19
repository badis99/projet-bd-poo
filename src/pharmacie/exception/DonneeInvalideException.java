package pharmacie.exception;

/**
 * Thrown when input data is invalid (empty fields, negative values).
 */
public class DonneeInvalideException extends Exception {
    public DonneeInvalideException(String message) {
        super(message);
    }
}
