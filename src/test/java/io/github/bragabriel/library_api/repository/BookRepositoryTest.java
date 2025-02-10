package io.github.bragabriel.library_api.repository;

import io.github.bragabriel.library_api.model.Author;
import io.github.bragabriel.library_api.model.Book;
import io.github.bragabriel.library_api.objectmother.AuthorObjectMother;
import io.github.bragabriel.library_api.objectmother.BookObjectMother;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class BookRepositoryTest {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorRepository authorRepository;

    @BeforeEach
    public void setUp(){
        bookRepository.deleteAll();
        authorRepository.deleteAll();
    }

    @Test
    void saveTest() {
        Book savedBook = createAndSaveBookWithExistingAuthor();

        assertNotNull(savedBook.getId());
        assertEquals(savedBook.getIsbn(), savedBook.getIsbn());
        assertEquals(savedBook.getTitle(), savedBook.getTitle());
    }

    @Test
    void findByIdTest() {
        Book savedBook = createAndSaveBookWithExistingAuthor();

        Optional<Book> foundBook = bookRepository.findById(savedBook.getId());

        assertTrue(foundBook.isPresent());
        assertEquals(savedBook.getId(), foundBook.get().getId());
    }

    @Test
    void findAllTest() {
        Book book1 = createAndSaveBookWithExistingAuthor();
        Book book2 = createAndSaveBookWithExistingAuthor();

        List<Book> books = bookRepository.findAll();

        assertEquals(2, books.size());
        assertEquals(book1.getId(), books.get(0).getId());
        assertEquals(book2.getId(), books.get(1).getId());
    }

    @Test
    void updateTest() {
        Book savedBook = createAndSaveBookWithExistingAuthor();

        savedBook.setTitle("Harry Potter - The Philosopher's Stone");
        Book updatedBook = bookRepository.save(savedBook);

        assertEquals("Harry Potter - The Philosopher's Stone", updatedBook.getTitle());
    }

    @Test
    void deleteTest() {
        Book book = createAndSaveBookWithExistingAuthor();

        bookRepository.deleteById(book.getId());

        Optional<Book> deletedBook = bookRepository.findById(book.getId());

        assertFalse(deletedBook.isPresent());
    }

    @Test
    @Transactional
    void findBooksByAuthorTest(){
        Author savedAuthor = createAndSaveAuthorWithBooks();
        var foundBooksByAuthor = bookRepository.findByAuthor(savedAuthor);

        IntStream.range(0, savedAuthor.getBookList().size()).forEach(i -> {
            Book originalBook = savedAuthor.getBookList().get(i);

            assertNotNull(foundBooksByAuthor.get(i).getId());
            assertEquals(savedAuthor, foundBooksByAuthor.get(i).getAuthor());
            assertEquals(originalBook.getTitle(), foundBooksByAuthor.get(i).getTitle());
            assertEquals(originalBook.getIsbn(), foundBooksByAuthor.get(i).getIsbn());
            assertEquals(originalBook.getPublicationDate(), foundBooksByAuthor.get(i).getPublicationDate());
            assertEquals(originalBook.getGenre(), foundBooksByAuthor.get(i).getGenre());
            assertEquals(originalBook.getPrice(), foundBooksByAuthor.get(i).getPrice());
        });
    }

    @Test
    @Transactional
    void findBooksByAuthorAndPriceTest(){
        Author savedAuthor = createAndSaveAuthorWithBooks();
        Book book = savedAuthor.getBookList().getFirst();

        var bookTitle = book.getTitle();
        var bookPrice = book.getPrice();

        var foundBooksByTitleAndPrice = bookRepository.findByTitleAndPrice(bookTitle, bookPrice);

        Book firstFoundedBook = foundBooksByTitleAndPrice.getFirst();

        assertNotNull(firstFoundedBook.getId());
        assertEquals(savedAuthor, firstFoundedBook.getAuthor());
        assertEquals(book.getTitle(), firstFoundedBook.getTitle());
        assertEquals(book.getIsbn(), firstFoundedBook.getIsbn());
        assertEquals(book.getPublicationDate(), firstFoundedBook.getPublicationDate());
        assertEquals(book.getGenre(), firstFoundedBook.getGenre());
        assertEquals(book.getPrice(), firstFoundedBook.getPrice());
    }

    @Test
    @Transactional
    void findBooksByTitleOrGenreWhenTitleIsNullTest(){
        Author savedAuthor = createAndSaveAuthorWithBooks();
        Book book = savedAuthor.getBookList().getFirst();

        var bookGenre = book.getGenre();

        var foundBooksByTitleAndPrice = bookRepository.findByTitleOrGenre(null, bookGenre);

        Book firstFoundedBook = foundBooksByTitleAndPrice.getFirst();

        assertNotNull(firstFoundedBook.getId());
        assertEquals(savedAuthor, firstFoundedBook.getAuthor());
        assertEquals(book.getTitle(), firstFoundedBook.getTitle());
        assertEquals(book.getIsbn(), firstFoundedBook.getIsbn());
        assertEquals(book.getPublicationDate(), firstFoundedBook.getPublicationDate());
        assertEquals(book.getGenre(), firstFoundedBook.getGenre());
        assertEquals(book.getPrice(), firstFoundedBook.getPrice());
    }

    @Test
    @Transactional
    void listAllOrderedByTitleAndPriceTest(){
        var author1 = AuthorObjectMother.createAuthorNamed("J. K. Rowling");
        var author2 = AuthorObjectMother.createAuthorNamed("George R. R. Martin");
        authorRepository.save(author1);
        authorRepository.save(author2);

        List<Book> bookList = List.of(
                BookObjectMother.createBookWithTitleAndPrice("Harry Potter", BigDecimal.valueOf(45), author1),
                BookObjectMother.createBookWithTitleAndPrice("A Song of Ice and Fire", BigDecimal.valueOf(50), author2),
                BookObjectMother.createBookWithTitleAndPrice("A Clash of Kings", BigDecimal.valueOf(80), author2)
        );
        bookRepository.saveAll(bookList);

        var orderedBooks = bookRepository.listAllOrderedByTitleAndPrice();

        assertEquals("A Clash of Kings", orderedBooks.get(0).getTitle());
        assertEquals(BigDecimal.valueOf(80), orderedBooks.get(0).getPrice());

        assertEquals("A Song of Ice and Fire", orderedBooks.get(1).getTitle());
        assertEquals(BigDecimal.valueOf(50), orderedBooks.get(1).getPrice());

        assertEquals("Harry Potter", orderedBooks.get(2).getTitle());
        assertEquals(BigDecimal.valueOf(45), orderedBooks.get(2).getPrice());
    }

    private Author createAndSaveAuthorWithBooks() {
        Author author = AuthorObjectMother.createAuthor();

        List<Book> bookList = List.of(
                BookObjectMother.createBook("A Song of Ice and Fire", author),
                BookObjectMother.createBook("A Clash of Kings", author)
        );
        author.setBookList(bookList);
        bookRepository.saveAll(author.getBookList());

        return authorRepository.save(author);
    }

    private Book createAndSaveBookWithExistingAuthor() {
        Author author = AuthorObjectMother.createAuthor();
        authorRepository.save(author);

        Book book = BookObjectMother.createBookWithAuthor(author);
        bookRepository.save(book);

        return book;
    }
}
