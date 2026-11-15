package librarymanagementsystem;

/**
 * Represents a book in the library.
 */
public class Book {
    private final int id;
    private String title;
    private String author;
    private boolean available;

    public Book(int id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.available = true;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        StringBuilder details = new StringBuilder();
        details.append("Book ID: ").append(id)
               .append(" | Title: ").append(title)
               .append(" | Author: ").append(author)
               .append(" | Status: ")
               .append(available ? "Available" : "Borrowed");
        return details.toString();
    }
}
