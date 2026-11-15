package librarymanagementsystem;

/**
 * Custom exception thrown when a member tries to borrow an unavailable book.
 */
public class BookNotAvailableException extends Exception {

    public BookNotAvailableException(String message) {
        super(message);
    }
}
