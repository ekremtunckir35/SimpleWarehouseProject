package DepoApp;

public class InsufficentQuantityException extends RuntimeException {

    public InsufficentQuantityException(String message) {

        super (message);
    }
}