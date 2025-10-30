package io.github.bragabriel.library_api.repository;

import io.github.bragabriel.library_api.model.Author;
import io.github.bragabriel.library_api.model.Book;
import io.github.bragabriel.library_api.model.BookGenreEnum;
import io.github.bragabriel.library_api.objectmother.AuthorObjectMother;
import io.github.bragabriel.library_api.objectmother.BookObjectMother;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
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
    void listAllOrderedByTitleAndPriceWithPriceRangeTest(){
        var author1 = AuthorObjectMother.createAuthorNamed("J. K. Rowling");
        var author2 = AuthorObjectMother.createAuthorNamed("George R. R. Martin");
        authorRepository.save(author1);
        authorRepository.save(author2);

        List<Book> bookList = List.of(
                BookObjectMother.createBookWithTitleAndPrice("Harry Potter", BigDecimal.valueOf(45), author1),
                BookObjectMother.createBookWithTitleAndPrice("A Song of Ice and Fire", BigDecimal.valueOf(50), author2),
                BookObjectMother.createBookWithTitleAndPrice("A Clash of Kings", BigDecimal.valueOf(80), author2),
                BookObjectMother.createBookWithTitleAndPrice("A Study in Scarlet", BigDecimal.valueOf(100), author2)
        );
        bookRepository.saveAll(bookList);

        var orderedBooks = bookRepository.listAllOrderedByTitleAndPrice("80");

        assertEquals("A Clash of Kings", orderedBooks.get(0).getTitle());
        assertEquals(new BigDecimal("80.00"), orderedBooks.get(0).getPrice());

        assertEquals("A Song of Ice and Fire", orderedBooks.get(1).getTitle());
        assertEquals(new BigDecimal("50.00"), orderedBooks.get(1).getPrice());

        assertEquals("Harry Potter", orderedBooks.get(2).getTitle());
        assertEquals(new BigDecimal("45.00"), orderedBooks.get(2).getPrice());
    }

    @Test
    void listAllByByGenreSortedTest(){
        createAndSaveBooksWithExistingAuthor();

        List<Book> romanceBooks = bookRepository.findByGenre(
                BookGenreEnum.ROMANCE,
                Sort.by(Sort.Direction.ASC, "price")
        );

        // Asserts for ROMANCE books, sorted by price
        assertNotNull(romanceBooks);
        assertEquals(2, romanceBooks.size());
        assertEquals(BigDecimal.valueOf(30).setScale(2, RoundingMode.CEILING), romanceBooks.get(0).getPrice());
        assertEquals(BigDecimal.valueOf(55).setScale(2, RoundingMode.CEILING), romanceBooks.get(1).getPrice());
    }

    @Test
    void listAllByGenrePositionalParamsTest(){
        createAndSaveBooksWithExistingAuthor();

        List<Book> romanceBooks = bookRepository.findByGenreAndTitlePositionalParams(
                BookGenreEnum.ROMANCE,
                "Romeo and Juliet"
        );

        // Asserts for ROMANCE books, by genre and title
        assertNotNull(romanceBooks);
        assertEquals(1, romanceBooks.size());
        assertEquals("Romeo and Juliet", romanceBooks.getFirst().getTitle());
        assertEquals(BookGenreEnum.ROMANCE, romanceBooks.getFirst().getGenre());
    }

    @Test
    void deleteByGenreTest(){
        createAndSaveBooksWithExistingAuthor();

        bookRepository.deleteByGenre(BookGenreEnum.ROMANCE);
        List<Book> fetchedBooks = bookRepository.findAll();

        assertEquals(1, fetchedBooks.size());
        assertNotEquals(BookGenreEnum.ROMANCE, fetchedBooks.getFirst().getGenre());
    }

    @Test
    void updatePublicationDate(){
        Book book = createAndSaveBookWithExistingAuthor();
        LocalDate newDate = LocalDate.of(2000, 12, 1);

        bookRepository.updatePublicationDateByTitle(newDate, book.getTitle());
        List<Book> fetchedBooks = bookRepository.findAll();

        assertEquals(newDate, fetchedBooks.getFirst().getPublicationDate());
    }

    @Test
    void searchByIsbnTest(){
        Book book = createAndSaveBookWithExistingAuthor();

        Book fetchedBook = bookRepository.findByIsbn("123").orElseThrow();

        assertEquals(book.getId(), fetchedBook.getId());
        assertEquals(book.getIsbn(), fetchedBook.getIsbn());
        assertEquals(book.getPrice(), fetchedBook.getPrice());
        assertEquals(book.getAuthor().getId(), fetchedBook.getAuthor().getId());
        assertEquals(book.getAuthor().getName(), fetchedBook.getAuthor().getName());
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

    private Author createAndSaveNamedAuthorWithBooks(String name) {
        Author author = AuthorObjectMother.createAuthorNamed(name);

        List<Book> bookList = List.of(
                BookObjectMother.createBookWithTitleAndDateAndAuthor("The Hound of the Baskervilles", LocalDate.of(1902, 4, 12), author),
                BookObjectMother.createBookWithTitleAndDateAndAuthor("A Study in Scarlet", LocalDate.of(1887, 11, 1), author)
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

    private void createAndSaveBooksWithExistingAuthor() {
        Author author = AuthorObjectMother.createAuthor();

        List<Book> bookList = List.of(
                BookObjectMother.createBookWithTitleAndPriceAndGenreAndDate("The Fault in Our Stars", BigDecimal.valueOf(55), BookGenreEnum.ROMANCE, LocalDate.of(1597, 10, 10),author),
                BookObjectMother.createBookWithTitleAndPriceAndGenreAndDate("A Clash of Kings", BigDecimal.valueOf(50), BookGenreEnum.FICTION, LocalDate.of(1998, 11, 16), author),
                BookObjectMother.createBookWithTitleAndPriceAndGenreAndDate("Romeo and Juliet", BigDecimal.valueOf(30), BookGenreEnum.ROMANCE, LocalDate.of(2012, 1, 1), author)
        );
        author.setBookList(bookList);
        authorRepository.save(author);

        bookRepository.saveAll(bookList);
    }
}
