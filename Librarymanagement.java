import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Book {
    private String title;
    private String author;
    private int isbn;
    private boolean available;

    // Constructors for Book class
    public Book(String title, String author, int isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.available = true;
    }

    public Book() {
        // Scanner for user input in the default constructor
        Scanner bookDetails = new Scanner(System.in);

        System.out.print("Enter book title: ");
        this.title = bookDetails.nextLine();

        System.out.print("Enter author name: ");
        this.author = bookDetails.nextLine();

        System.out.print("Enter ISBN: ");
        this.isbn = bookDetails.nextInt();

        this.available = true;
    }

    // Getter methods for Book class
    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getIsbn() {
        return isbn;
    }

    public boolean isAvailable() {
        return available;
    }

    // Setter method for availability
    public void setAvailable(boolean available) {
        this.available = available;
    }

    // toString method for string representation of Book
    public String toString() {
        return "Title: " + title + ", Author: " + author + ", ISBN: " + isbn + ", Available: " + available;
    }
}
class FictionBook extends Book {
    public FictionBook(String title, String author, int isbn) {
        super(title, author, isbn);
    }

    public FictionBook() {
        super();
    }
}

// Subclass for NonFictionBook
class NonFictionBook extends Book {
    public NonFictionBook(String title, String author, int isbn) {
        super(title, author, isbn);
    }

    public NonFictionBook() {
        super();
    }
}

class Library {
    private List<Book> fictionBooks;
    private List<Book> nonFictionBooks;

    // Constructor for Library class
    public Library() {
        this.fictionBooks = new ArrayList<>();
        this.nonFictionBooks = new ArrayList<>();
    }

    // Method to add a book to the library
    public void addBook(Book book, boolean isFiction) {
        if (isFiction) {
            fictionBooks.add(book);
        } else {
            nonFictionBooks.add(book);
        }
    }

    // Method to display available books in the library
    public void displayAvailableBooks() {
        System.out.println("Available Books:");

        System.out.println("\nFiction Books:");
        booksCategory(fictionBooks);

        System.out.println("\nNonfiction Books:");
        booksCategory(nonFictionBooks);
    }

    // Helper method to display books in a specific category
    private void booksCategory(List<Book> bookList) {
        for (Book book : bookList) {
            if (book.isAvailable()) {
                System.out.println(book);
            }
        }
    }

    // Method to borrow a book from the library
    public Book borrowBook(int isbn, boolean isFiction) {
        List<Book> bookList = isFiction ? fictionBooks : nonFictionBooks;

        for (Book book : bookList) {
            if (book.getIsbn() == isbn && book.isAvailable()) {
                book.setAvailable(false);
                return book;
            }
        }
        return null;
    }

    // Method to return a book to the library
    public void returnBook(Book book) {
        book.setAvailable(true);
    }
}

class User {
    private static int userCount = 0;
    private int userId;
    private List<Book> borrowedBooks;

    // Constructor for User class
    public User() {
        this.userId = ++userCount;
        this.borrowedBooks = new ArrayList<>();
    }

    // Getter methods for User class
    public int getUserId() {
        return userId;
    }

    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    // Method to borrow a book
    public void borrowBook(Book book) {
        borrowedBooks.add(book);
    }

    // Method to return a book
    public void returnBook(Book book) {
        borrowedBooks.remove(book);
    }
}

public class Librarymanagement {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Library library = new Library();

        System.out.println("__WELCOME TO SIPSAYURA LIBRARY__ \n     Find Your Best Choice!\n    Improve Your Knowledge..." );

        // Initial books in the library
        Book fictionBook1 = new Book("Anil's Ghost", "Michael Ondaatje", 974374);
        Book fictionBook2 = new Book("Reef", "Romesh Gunesekera", 979828);
        
        // Nonfiction books
        Book nonFictionBook1 = new Book("Running in the Family", "Michael Ondaatje", 976694);
        Book nonFictionBook2 = new Book("Sri Lanka: The Island from Above", "Sebastian Posingis", 970807);

        // Adding books to the library
        library.addBook(fictionBook1, true);
        library.addBook(fictionBook2, true);
        library.addBook(nonFictionBook1, false);
        library.addBook(nonFictionBook2, false);

        // Creating a user
        User user = new User();

        // Menu-driven user interface
        while (true) {
            System.out.println("\n1. Display Available Books");
            System.out.println("2. Borrow a Book");
            System.out.println("3. Return a Book");
            System.out.println("4. Add a New Book");
            System.out.println("5. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    library.displayAvailableBooks();
                    break;
                case 2:
                    System.out.print("Enter ISBN of the book to borrow: ");
                    int isbnToBorrow = scanner.nextInt();
                    System.out.print("Is the book fiction? (true/false): ");
                    boolean isFictionToBorrow = scanner.nextBoolean();
                    Book borrowedBook = library.borrowBook(isbnToBorrow, isFictionToBorrow);

                    if (borrowedBook != null) {
                        user.borrowBook(borrowedBook);
                        System.out.println("Book borrowed successfully.");
                    } else {
                        System.out.println("Book not available or not found.");
                    }
                    break;
                case 3:
                    System.out.print("Enter ISBN of the book to return: ");
                    int isbnToReturn = scanner.nextInt();
                    Book bookToReturn = null;

                    for (Book borrowed : user.getBorrowedBooks()) {
                        if (borrowed.getIsbn() == isbnToReturn) {
                            bookToReturn = borrowed;
                            break;
                        }
                    }

                    if (bookToReturn != null) {
                        library.returnBook(bookToReturn);
                        user.returnBook(bookToReturn);
                        System.out.println("Book returned successfully.");
                    } else {
                        System.out.println("Book not found in your borrowed list.");
                    }
                    break;
                case 4:
                    System.out.print("Is the book fiction? (true/false): ");
                    boolean isFictionToAdd = scanner.nextBoolean();
                    Book newBook = new Book();
                    library.addBook(newBook, isFictionToAdd);
                    System.out.println("New book added to the library.");
                    break;
                case 5:
                    System.out.println("Exiting the Our Library. Thank you!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }
}
