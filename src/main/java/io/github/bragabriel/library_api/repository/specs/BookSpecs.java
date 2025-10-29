package io.github.bragabriel.library_api.repository.specs;

import org.springframework.data.jpa.domain.Specification;

import io.github.bragabriel.library_api.model.Author;
import io.github.bragabriel.library_api.model.Book;
import io.github.bragabriel.library_api.model.BookGenreEnum;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;

public class BookSpecs {

    public static Specification<Book> isbnEqual(String isbn){
        return (root, query, cb) -> cb.equal(root.get("isbn"), isbn);
    }

    public static Specification<Book> titleLike(String title){
        return (root, query, cb) ->
                cb.equal(
                        cb.upper(root.get("title")),
                        "%" + title.toUpperCase() + "%"
                );
    }

    public static Specification<Book> genreEqual(BookGenreEnum genre){
        return (root, query, cb) -> cb.equal(root.get("genre"), genre);
    }

    public static Specification<Book> publicationDateEqual(Integer publicationDate){
        return (root, query, cb) ->
                cb.equal(cb.function("to_char", String.class,
                                root.get("publicationDate"), cb.literal("YYYY")), publicationDate);
    }

    public static Specification<Book> nameAuthorLike(String nameAuthor){
        return (root, query, cb) ->
                cb.equal(cb.upper(root.get("author").get("name")), "%" + nameAuthor.toUpperCase() + "%");
    }

    public static Specification<Book> nameAuthorLikeUsingJoin(String nameAuthor){
        return (root, query, cb) -> {
            Join<Book, Author> authorJoin = root.join("author", JoinType.INNER);
            return cb.equal(cb.upper(authorJoin.get("name")), "%" + nameAuthor.toUpperCase() + "%");
        };
    }
}
