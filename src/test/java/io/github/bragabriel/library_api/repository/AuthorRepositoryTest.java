package io.github.bragabriel.library_api.repository;

import io.github.bragabriel.library_api.model.Author;
import io.github.bragabriel.library_api.objectmother.AuthorObjectMother;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AuthorRepositoryTest {

    @Autowired
    AuthorRepository repository;

    @BeforeEach
    public void setUp(){
        repository.deleteAll();
    }

    @Test
    void saveTest(){
        Author author = AuthorObjectMother.createAuthor();

        var savedAuthor = repository.save(author);

        Assertions.assertNotNull(savedAuthor.getId());
        Assertions.assertEquals(author.getName(), savedAuthor.getName());
    }

    @Test
    void findByIdTest() {
        Author author = AuthorObjectMother.createAuthor();
        var savedAuthor = repository.save(author);

        var foundAuthor = repository.findById(savedAuthor.getId());

        Assertions.assertTrue(foundAuthor.isPresent());
        Assertions.assertEquals(savedAuthor.getId(), foundAuthor.get().getId());
    }

    @Test
    void findAllTest() {
        Author author1 = AuthorObjectMother.createAuthor();
        Author author2 = AuthorObjectMother.createAuthor();
        repository.save(author1);
        repository.save(author2);

        var authors = repository.findAll();

        Assertions.assertFalse(authors.isEmpty());
        Assertions.assertEquals(2, authors.size());
    }

    @Test
    void deleteTest() {
        Author author = AuthorObjectMother.createAuthor();
        var savedAuthor = repository.save(author);

        repository.delete(savedAuthor);
        var deletedAuthor = repository.findById(savedAuthor.getId());

        Assertions.assertFalse(deletedAuthor.isPresent());
    }

    @Test
    void updateTest() {
        Author author = AuthorObjectMother.createAuthor();
        var savedAuthor = repository.save(author);

        savedAuthor.setName("Updated Name");
        var updatedAuthor = repository.save(savedAuthor);

        Assertions.assertEquals("Updated Name", updatedAuthor.getName());
    }
}
