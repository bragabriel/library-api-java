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

import java.util.List;
import java.util.Optional;

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

    private Book createAndSaveBookWithExistingAuthor() {
        Author author = AuthorObjectMother.createAuthor();
        authorRepository.save(author);

        Book book = BookObjectMother.createBookWithAuthor(author);
        bookRepository.save(book);

        return book;
    }
}
