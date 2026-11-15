package librarymanagementsystem;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Main class for the console-based Library Management System.
 */
public class LibraryManagementSystem {
    private final Scanner scanner;
    private final ArrayList<Book> books;
    private final ArrayList<Member> members;
    private final Librarian librarian;
    private int nextBookId;
    private int nextMemberId;

    public LibraryManagementSystem() {
        scanner = new Scanner(System.in);
        books = new ArrayList<>();
        members = new ArrayList<>();
        librarian = new Librarian("LIB001", "Library Administrator");
        nextBookId = 1;
        nextMemberId = 1;

        // Sample books make it possible to test searching and borrowing immediately.
        addBook("Things Fall Apart", "Chinua Achebe");
        addBook("The Hobbit", "J.R.R. Tolkien");
        addBook("To Kill a Mockingbird", "Harper Lee");
    }

    public static void main(String[] args) {
        new LibraryManagementSystem().run();
    }

    private void run() {
        System.out.println("========================================");
        System.out.println("       LIBRARY MANAGEMENT SYSTEM");
        System.out.println("========================================");

        boolean running = true;
        while (running) {
            System.out.println("\nMAIN LOGIN MENU");
            System.out.println("1. Librarian login");
            System.out.println("2. Member login");
            System.out.println("0. Exit");
            int choice = readInt("Choose an option: ");

            switch (choice) {
                case 1:
                    librarianLogin();
                    break;
                case 2:
                    memberLogin();
                    break;
                case 0:
                    running = false;
                    System.out.println("Thank you for using the Library Management System.");
                    break;
                default:
                    System.out.println("Invalid option. Please choose 0, 1 or 2.");
            }
        }
        scanner.close();
    }

    private void librarianLogin() {
        System.out.println("\nLIBRARIAN LOGIN");
        System.out.println("Demo librarian ID: LIB001");
        String id = readNonEmpty("Enter librarian ID: ");

        if (librarian.getId().equalsIgnoreCase(id)) {
            System.out.println("Welcome, " + librarian.getName() + ".");
            System.out.println("Role: " + librarian.getRole());
            librarianMenu();
        } else {
            System.out.println("Librarian ID not recognised.");
        }
    }

    private void memberLogin() {
        System.out.println("\nMEMBER LOGIN");
        if (members.isEmpty()) {
            System.out.println("There are no registered members yet. Ask the librarian to register you.");
            return;
        }

        String id = readNonEmpty("Enter your member ID: ");
        Member member = findMemberById(id);

        if (member == null) {
            System.out.println("Member ID not found.");
            return;
        }

        System.out.println("Welcome, " + member.getName() + ".");
        System.out.println("Role: " + member.getRole());
        memberMenu(member);
    }

    private void librarianMenu() {
        boolean loggedIn = true;
        while (loggedIn) {
            System.out.println("\n========== LIBRARIAN MENU ==========");
            System.out.println("1. Add a new book");
            System.out.println("2. Add a new member");
            System.out.println("3. Search books");
            System.out.println("4. Display all books and status");
            System.out.println("5. Display members and borrowed books");
            System.out.println("0. Logout");
            int choice = readInt("Choose an option: ");

            switch (choice) {
                case 1:
                    addBookFromInput();
                    break;
                case 2:
                    addMemberFromInput();
                    break;
                case 3:
                    searchBooks();
                    break;
                case 4:
                    displayAllBooks();
                    break;
                case 5:
                    displayMembersAndBorrowedBooks();
                    break;
                case 0:
                    loggedIn = false;
                    System.out.println("Librarian logged out.");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void memberMenu(Member member) {
        boolean loggedIn = true;
        while (loggedIn) {
            System.out.println("\n========== MEMBER MENU ==========");
            System.out.println("1. Search books");
            System.out.println("2. Display all books");
            System.out.println("3. Borrow a book");
            System.out.println("4. Return a book");
            System.out.println("5. Display my borrowed books");
            System.out.println("0. Logout");
            int choice = readInt("Choose an option: ");

            switch (choice) {
                case 1:
                    searchBooks();
                    break;
                case 2:
                    displayAllBooks();
                    break;
                case 3:
                    borrowBook(member);
                    break;
                case 4:
                    returnBook(member);
                    break;
                case 5:
                    displayBorrowedBooks(member);
                    break;
                case 0:
                    loggedIn = false;
                    System.out.println("You have been logged out.");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void addBookFromInput() {
        System.out.println("\nADD NEW BOOK");
        String title = readNonEmpty("Enter book title: ");
        String author = readNonEmpty("Enter author: ");
        Book book = addBook(title, author);
        System.out.println("Book added successfully. Assigned ID: " + book.getId());
    }

    private Book addBook(String title, String author) {
        Book book = new Book(nextBookId++, title, author);
        books.add(book);
        return book;
    }

    private void addMemberFromInput() {
        System.out.println("\nREGISTER NEW MEMBER");
        String name = readNonEmpty("Enter member name: ");
        String id = String.format("MEM%03d", nextMemberId++);
        Member member = new Member(id, name);
        members.add(member);
        System.out.println("Member registered successfully.");
        System.out.println("Member ID (keep this for login): " + member.getId());
        System.out.println("Join date: " + member.getJoinDate());
    }

    private void searchBooks() {
        System.out.println("\nSEARCH BOOKS");
        String query = readNonEmpty("Enter a title or author to search: ").toLowerCase();
        boolean found = false;

        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(query)
                    || book.getAuthor().toLowerCase().contains(query)) {
                System.out.println(book);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No books matched your search.");
        }
    }

    private void displayAllBooks() {
        System.out.println("\nALL LIBRARY BOOKS");
        if (books.isEmpty()) {
            System.out.println("No books have been added.");
            return;
        }

        for (Book book : books) {
            System.out.println(book);
        }
    }

    private void borrowBook(Member member) {
        System.out.println("\nBORROW A BOOK");
        displayAllBooks();
        int id = readInt("Enter the ID of the book you want to borrow: ");
        Book book = findBookById(id);

        if (book == null) {
            System.out.println("Book ID not found.");
            return;
        }

        try {
            member.borrowBook(book);
            System.out.println("You have successfully borrowed: " + book.getTitle());
        } catch (BookNotAvailableException e) {
            System.out.println("Unable to borrow book: " + e.getMessage());
        }
    }

    private void returnBook(Member member) {
        System.out.println("\nRETURN A BOOK");
        displayBorrowedBooks(member);
        int id = readInt("Enter the ID of the book you want to return: ");

        if (member.returnBook(id)) {
            System.out.println("Book returned successfully.");
        } else {
            System.out.println("That book is not currently borrowed under your member account.");
        }
    }

    private void displayBorrowedBooks(Member member) {
        System.out.println("\nBOOKS BORROWED BY " + member.getName().toUpperCase());
        if (member.getBorrowedBooks().isEmpty()) {
            System.out.println("You have no borrowed books.");
            return;
        }

        for (Book book : member.getBorrowedBooks()) {
            System.out.println(book);
        }
    }

    private void displayMembersAndBorrowedBooks() {
        System.out.println("\nREGISTERED MEMBERS");
        if (members.isEmpty()) {
            System.out.println("No members have been registered.");
            return;
        }

        for (Member member : members) {
            System.out.println("\nMember ID: " + member.getId()
                    + " | Name: " + member.getName()
                    + " | Join date: " + member.getJoinDate());
            if (member.getBorrowedBooks().isEmpty()) {
                System.out.println("  Borrowed books: None");
            } else {
                System.out.println("  Borrowed books:");
                for (Book book : member.getBorrowedBooks()) {
                    System.out.println("  - " + book.getTitle() + " (ID: " + book.getId() + ")");
                }
            }
        }
    }

    private Book findBookById(int id) {
        for (Book book : books) {
            if (book.getId() == id) {
                return book;
            }
        }
        return null;
    }

    private Member findMemberById(String id) {
        for (Member member : members) {
            if (member.getId().equalsIgnoreCase(id)) {
                return member;
            }
        }
        return null;
    }

    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a whole number.");
            }
        }
    }

    private String readNonEmpty(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("This field cannot be empty. Please try again.");
        }
    }
}
