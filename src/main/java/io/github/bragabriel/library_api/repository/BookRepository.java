package io.github.bragabriel.library_api.repository;

import io.github.bragabriel.library_api.model.Author;
import io.github.bragabriel.library_api.model.Book;
import io.github.bragabriel.library_api.model.BookGenreEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * @see BookRepositoryTest
 */
@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {

    //JPA Query Methods
    List<Book> findByAuthor(Author author);

    List<Book> findByTitleAndPrice(String title, BigDecimal price);

    List<Book> findByTitleOrGenre(String title, BookGenreEnum genre);

    //JPQL
    @Query("select b from Book as b order by b.title, b.price")
    List<Book> listAllOrderedByTitleAndPrice();
}
