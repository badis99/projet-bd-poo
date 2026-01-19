package pharmacie.exception;

/**
 * Thrown when trying to sell or use more stock than available.
 */
public class StockInsuffisantException extends Exception {
    public StockInsuffisantException(String message) {
        super(message);
    }
}
