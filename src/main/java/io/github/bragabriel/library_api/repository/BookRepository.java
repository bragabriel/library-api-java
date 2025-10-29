package io.github.bragabriel.library_api.repository;

import io.github.bragabriel.library_api.model.Author;
import io.github.bragabriel.library_api.model.Book;
import io.github.bragabriel.library_api.model.BookGenreEnum;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * @see BookRepositoryTest
 */
@Repository
public interface BookRepository extends JpaRepository<Book, UUID>, JpaSpecificationExecutor<Book> {

    //JPA Query Methods
    List<Book> findByAuthor(Author author);

    List<Book> findByTitleAndPrice(String title, BigDecimal price);

    List<Book> findByTitleOrGenre(String title, BookGenreEnum genre);

    List<Book> findByGenre(BookGenreEnum genre, Sort sort);

	boolean existsByAuthor(Author author);

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

    //JPQL - With modification to the database
    @Modifying
    @Transactional
    @Query("DELETE FROM Book b WHERE genre = ?1")
    void deleteByGenre(BookGenreEnum genre);

    @Modifying
    @Transactional
    @Query("UPDATE Book b set b.publicationDate = ?1 WHERE b.title = ?2")
    void updatePublicationDateByTitle(LocalDate newDate, String title);
}
