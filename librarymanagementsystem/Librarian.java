package librarymanagementsystem;

/**
 * Represents a librarian who manages books and members.
 */
public class Librarian extends User {

    public Librarian(String id, String name) {
        super(id, name);
    }

    @Override
    public String getRole() {
        return "Librarian - manages books and library members";
    }
}
