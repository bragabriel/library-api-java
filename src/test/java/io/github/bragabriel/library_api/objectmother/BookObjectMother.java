package io.github.bragabriel.library_api.objectmother;

import io.github.bragabriel.library_api.model.Author;
import io.github.bragabriel.library_api.model.Book;
import io.github.bragabriel.library_api.model.BookGenreEnum;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.time.LocalDate;

@UtilityClass
public class BookObjectMother {

    public static Book createBook(String title, Author author){
        return Book.builder()
                .isbn("isbn")
                .title(title)
                .publicationDate(LocalDate.now())
                .genre(BookGenreEnum.FANTASY)
                .price(BigDecimal.valueOf(50))
                .author(author)
                .build();
    }

    public static Book createBookWithAuthor(Author author){
        return Book.builder()
                .isbn("123")
                .title("The adventures of something")
                .publicationDate(LocalDate.now())
                .genre(BookGenreEnum.FANTASY)
                .price(BigDecimal.TEN)
                .author(author)
                .build();
    }
}
