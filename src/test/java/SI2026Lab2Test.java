import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class SI2026Lab2Test {

  @Test
  public void searchBookEveryStatementTest() {
    Library library = new Library();
    Book available = new Book("Clean Code", "Robert C. Martin", "Programming");
    Book borrowed = new Book("Effective Java", "Joshua Bloch", "Programming");
    borrowed.setBorrowed(true);
    library.addBook(available);
    library.addBook(borrowed);

    // Test 1: line 1 (true), line 2
    try {
      library.searchBookByTitle("");
      assertTrue("Expected IllegalArgumentException for empty title", false);
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid title", ex.getMessage());
    }

    // Test 2: lines 1, 3, 4, 5 (true), 6, 7 (false), 9
    List<Book> found = library.searchBookByTitle("Clean Code");
    assertNotNull(found);
    assertEquals(1, found.size());
    assertEquals("Clean Code", found.get(0).getTitle());
    assertFalse(found.get(0).isBorrowed());

    // Test 3: lines 1, 3, 4, 5 (false for borrowed / no match), 7 (true), 8
    List<Book> missing = library.searchBookByTitle("Harry Potter");
    assertNull(missing);

    List<Book> borrowedOnly = library.searchBookByTitle("Effective Java");
    assertNull(borrowedOnly);
  }

  @Test
  public void borrowBookEveryBranchTest() {
    Library library = new Library();
    Book hobbit = new Book("The Hobbit", "J.R.R. Tolkien", "Fantasy");
    Book borrowed = new Book("1984", "George Orwell", "Dystopian");
    borrowed.setBorrowed(true);
    library.addBook(hobbit);
    library.addBook(borrowed);

    // Branch 1 true: invalid query
    try {
      library.borrowBook("", "Author");
      assertTrue("Expected IllegalArgumentException for empty title", false);
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid search query", ex.getMessage());
    }

    try {
      library.borrowBook("Title", "");
      assertTrue("Expected IllegalArgumentException for empty author", false);
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid search query", ex.getMessage());
    }

    // Branch 1 false, branch 3 true, branch 4 true: successful borrow
    library.borrowBook("The Hobbit", "J.R.R. Tolkien");
    assertTrue(hobbit.isBorrowed());

    // Branch 3 true, branch 4 false: already borrowed
    try {
      library.borrowBook("1984", "George Orwell");
      assertTrue("Expected RuntimeException for already borrowed book", false);
    } catch (RuntimeException ex) {
      assertEquals("Book is already borrowed.", ex.getMessage());
    }

    // Branch 3 false for all books: not found
    try {
      library.borrowBook("Dune", "Frank Herbert");
      assertTrue("Expected RuntimeException for missing book", false);
    } catch (RuntimeException ex) {
      assertEquals("Book not found", ex.getMessage());
    }
  }

  @Test
  public void searchBookMultipleConditionTest() {
    Library library = new Library();
    Book available = new Book("Clean Code", "Robert C. Martin", "Programming");
    Book borrowed = new Book("Effective Java", "Joshua Bloch", "Programming");
    borrowed.setBorrowed(true);
    library.addBook(available);
    library.addBook(borrowed);

    // TT: title match && not borrowed
    List<Book> tt = library.searchBookByTitle("Clean Code");
    assertNotNull(tt);
    assertEquals(1, tt.size());

    // TF: title match && borrowed
    List<Book> tf = library.searchBookByTitle("Effective Java");
    assertNull(tf);

    // FT: title does not match (second sub-condition not evaluated)
    List<Book> ft = library.searchBookByTitle("Harry Potter");
    assertNull(ft);

    // FF: title does not match on another existing book
    List<Book> ff = library.searchBookByTitle("Dune");
    assertNull(ff);
  }

  @Test
  public void borrowBookMultipleConditionTest() {
    Library library = new Library();
    library.addBook(new Book("The Hobbit", "J.R.R. Tolkien", "Fantasy"));

    // TT: title empty (author not evaluated because of short-circuit)
    try {
      library.borrowBook("", "J.R.R. Tolkien");
      assertTrue("Expected IllegalArgumentException", false);
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid search query", ex.getMessage());
    }

    // FT: title not empty, author empty
    try {
      library.borrowBook("The Hobbit", "");
      assertTrue("Expected IllegalArgumentException", false);
    } catch (IllegalArgumentException ex) {
      assertEquals("Invalid search query", ex.getMessage());
    }

    // FF: both sub-conditions false
    library.borrowBook("The Hobbit", "J.R.R. Tolkien");
    assertTrue(library.countAvailableBooks() == 0);
  }
}
