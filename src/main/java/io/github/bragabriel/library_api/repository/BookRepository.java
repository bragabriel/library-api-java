package io.github.bragabriel.library_api.repository;

import io.github.bragabriel.library_api.model.Author;
import io.github.bragabriel.library_api.model.Book;
import io.github.bragabriel.library_api.model.BookGenreEnum;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    List<Book> findByGenre(BookGenreEnum genre, Sort sort);

    //JPQL - Named parameter
    @Query("SELECT b FROM Book b WHERE b.price<=:price ORDER BY b.title, b.price")
    List<Book> listAllOrderedByTitleAndPrice(
            @Param("price") String price
    );

    //JPQL - Positional parameter
    @Query("SELECT b FROM Book b WHERE b.genre = ?1 AND b.title = ?2")
    List<Book> findByGenreAndTitlePositionalParams(
            BookGenreEnum genre, String title
    );
}
