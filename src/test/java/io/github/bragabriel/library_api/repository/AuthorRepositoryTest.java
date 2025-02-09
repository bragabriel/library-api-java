package io.github.bragabriel.library_api.repository;

import io.github.bragabriel.library_api.model.Author;
import io.github.bragabriel.library_api.model.Book;
import io.github.bragabriel.library_api.objectmother.AuthorObjectMother;
import io.github.bragabriel.library_api.objectmother.BookObjectMother;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class AuthorRepositoryTest {

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    BookRepository bookRepository;

    @BeforeEach
    public void setUp(){
        authorRepository.deleteAll();
        bookRepository.deleteAll();
    }

    @Test
    void saveTest(){
        Author author = AuthorObjectMother.createAuthor();

        var savedAuthor = authorRepository.save(author);

        assertNotNull(savedAuthor.getId());
        assertEquals(author.getName(), savedAuthor.getName());
    }

    @Test
    void findByIdTest() {
        Author author = AuthorObjectMother.createAuthor();
        var savedAuthor = authorRepository.save(author);

        var foundAuthor = authorRepository.findById(savedAuthor.getId());

        Assertions.assertTrue(foundAuthor.isPresent());
        Author authorGet = foundAuthor.get();
        assertEquals(savedAuthor.getId(), authorGet.getId());
        assertEquals(savedAuthor.getName(), authorGet.getName());
        assertEquals(savedAuthor.getBirthdate(), authorGet.getBirthdate());
    }

    @Test
    void findAllTest() {
        Author author1 = AuthorObjectMother.createAuthor();
        Author author2 = AuthorObjectMother.createAuthor();
        authorRepository.save(author1);
        authorRepository.save(author2);

        var authors = authorRepository.findAll();

        assertFalse(authors.isEmpty());
        assertEquals(2, authors.size());
    }

    @Test
    void deleteTest() {
        Author author = AuthorObjectMother.createAuthor();
        var savedAuthor = authorRepository.save(author);

        authorRepository.delete(savedAuthor);
        var deletedAuthor = authorRepository.findById(savedAuthor.getId());

        assertFalse(deletedAuthor.isPresent());
    }

    @Test
    void updateTest() {
        Author author = AuthorObjectMother.createAuthor();
        var savedAuthor = authorRepository.save(author);

        savedAuthor.setName("Updated Name");
        var updatedAuthor = authorRepository.save(savedAuthor);

        assertEquals("Updated Name", updatedAuthor.getName());
    }

    @Test
    @Transactional
    void saveAuthorWithBooksTest(){
        var savedAuthor = createAndSaveAuthorWithBooks();
        var fetchedAuthor = authorRepository.findById(savedAuthor.getId()).orElse(null);

        assert fetchedAuthor != null;
        //Author asserts
        assertNotNull(fetchedAuthor.getId());
        assertEquals(savedAuthor.getName(), fetchedAuthor.getName());
        assertEquals(savedAuthor.getBirthdate(), fetchedAuthor.getBirthdate());
        assertEquals(savedAuthor.getNationality(), fetchedAuthor.getNationality());

        //Book asserts
        assertNotNull(fetchedAuthor.getBookList());

        IntStream.range(0, fetchedAuthor.getBookList().size()).forEach(i -> {
            Book fetchedBook = fetchedAuthor.getBookList().get(i);
            Book originalBook = savedAuthor.getBookList().get(i);

            assertNotNull(fetchedBook.getId());
            assertEquals(savedAuthor, fetchedBook.getAuthor());
            assertEquals(originalBook.getTitle(), fetchedBook.getTitle());
            assertEquals(originalBook.getIsbn(), fetchedBook.getIsbn());
            assertEquals(originalBook.getPublicationDate(), fetchedBook.getPublicationDate());
            assertEquals(originalBook.getGenre(), fetchedBook.getGenre());
            assertEquals(originalBook.getPrice(), fetchedBook.getPrice());
        });
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
}
