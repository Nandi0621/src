package librarymanagementsystem;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a library member and the books they have borrowed.
 */
public class Member extends User {
    private final LocalDate joinDate;
    private final ArrayList<Book> borrowedBooks;

    public Member(String id, String name) {
        super(id, name);
        this.joinDate = LocalDate.now();
        this.borrowedBooks = new ArrayList<>();
    }

    @Override
    public String getRole() {
        return "Member - can search, borrow and return books";
    }

    public LocalDate getJoinDate() {
        return joinDate;
    }

    public List<Book> getBorrowedBooks() {
        return Collections.unmodifiableList(borrowedBooks);
    }

    public void borrowBook(Book book) throws BookNotAvailableException {
        if (!book.isAvailable()) {
            throw new BookNotAvailableException(
                "Sorry, \"" + book.getTitle() + "\" is currently unavailable."
            );
        }

        book.setAvailable(false);
        borrowedBooks.add(book);
    }

    public boolean returnBook(int bookId) {
        for (int i = 0; i < borrowedBooks.size(); i++) {
            Book book = borrowedBooks.get(i);
            if (book.getId() == bookId) {
                book.setAvailable(true);
                borrowedBooks.remove(i);
                return true;
            }
        }
        return false;
    }
}
