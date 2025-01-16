package io.github.bragabriel.library_api.repository;

import io.github.bragabriel.library_api.model.Author;
import io.github.bragabriel.library_api.objectmother.AuthorObjectMother;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AuthorRepositoryTest {

    @Autowired
    AuthorRepository repository;

    @Test
    void saveTest(){
        Author author = AuthorObjectMother.createAuthor();

        var savedAuthor = repository.save(author);

        Assertions.assertNotNull(savedAuthor.getId());
        Assertions.assertEquals(author.getName(), savedAuthor.getName());
    }
}
