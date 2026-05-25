import java.util.ArrayList;
import java.util.List;

class Book {
    private String title;
    private String author;
    private String genre;
    private boolean borrowed;

    public Book(String title, String author, String genre) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.borrowed = false;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public boolean isBorrowed() {
        return borrowed;
    }

    public void setBorrowed(boolean borrowed) {
        this.borrowed = borrowed;
    }

    @Override
    public String toString() {
        return "Title: " + title + ", Author: " + author + ", Genre: " + genre + ", Borrowed: " + borrowed;
    }
}

class Library {
    private List<Book> books;

    public Library() {
        books = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public List<Book> searchBookByTitle(String title) {
        if (title.isEmpty()) {
            throw new IllegalArgumentException("Invalid title");
        }
        List<Book> results = new ArrayList<>();
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title) && !book.isBorrowed()) {
                results.add(book);
            }
        }
        if (results.isEmpty()) {
            return null;
        }
        return results;
    }

    public void borrowBook(String title, String author) {
        if (title.isEmpty() || author.isEmpty()) {
            throw new IllegalArgumentException("Invalid search query");
        }
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title) && book.getAuthor().equalsIgnoreCase(author)) {
                if (!book.isBorrowed()) {
                    book.setBorrowed(true);
                    System.out.println("Borrowed successfully");
                } else {
                    throw new RuntimeException("Book is already borrowed.");
                }
                return;
            }
        }
        throw new RuntimeException("Book not found");
    }

    public void returnBook(String title) {
        if (title.isEmpty()) {
            throw new IllegalArgumentException("Invalid title");
        }
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                if (book.isBorrowed()) {
                    book.setBorrowed(false);
                    System.out.println("Book returned.");
                } else {
                    throw new RuntimeException("Book was not borrowed.");
                }
                return;
            }
        }
        throw new RuntimeException("Book not found.");
    }

    public void printBooksByGenre(String genre) {
        for (Book book : books) {
            if (book.getGenre().equalsIgnoreCase(genre)) {
                System.out.println(book);
            }
        }
    }

    public int countBooksByGenre(String genre) {
        int count = 0;
        for (Book book : books) {
            if (book.getGenre().equalsIgnoreCase(genre)) {
                count++;
            }
        }
        return count;
    }

    public int countAvailableBooks() {
        int count = 0;
        for (Book book : books) {
            if (!book.isBorrowed()) {
                count++;
            }
        }
        return count;
    }

    public void printBorrowedBooks() {
        for (Book book : books) {
            if (book.isBorrowed()) {
                System.out.println(book);
            }
        }
    }
}

public class SI2026Lab2Main {
    public static void main(String[] args) {
        Library library = new Library();

        library.addBook(new Book("Clean Code", "Robert C. Martin", "Programming"));
        library.addBook(new Book("Effective Java", "Joshua Bloch", "Programming"));
        library.addBook(new Book("The Hobbit", "J.R.R. Tolkien", "Fantasy"));
        library.addBook(new Book("1984", "George Orwell", "Dystopian"));

        System.out.println("Library initialized.");

        System.out.println("Search Clean Code: " + library.searchBookByTitle("Clean Code"));
        System.out.println("Search Harry Potter: " + library.searchBookByTitle("Harry Potter"));

        library.printBooksByGenre("Programming");
        library.borrowBook("The Hobbit", "J.R.R. Tolkien");

        System.out.println("Programming books: " + library.countBooksByGenre("Programming"));
    }
}
