package io.github.bragabriel.library_api.objectmother;

import io.github.bragabriel.library_api.model.Author;
import io.github.bragabriel.library_api.model.Book;
import io.github.bragabriel.library_api.model.BookGenreEnum;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

    public static Book createBookWithTitleAndDateAndAuthor(String title, LocalDate publicationDate, Author author){
        return Book.builder()
                .isbn("isbn")
                .title(title)
                .publicationDate(publicationDate)
                .genre(BookGenreEnum.FANTASY)
                .price(BigDecimal.valueOf(50))
                .author(author)
                .build();
    }

    public static Book createBookWithTitleAndPrice(String title, BigDecimal price, Author author){
        return Book.builder()
                .isbn("isbn")
                .title(title)
                .publicationDate(LocalDate.now())
                .genre(BookGenreEnum.FANTASY)
                .price(price)
                .author(author)
                .build();
    }

    public static Book createBookWithAuthor(Author author){
        return Book.builder()
                .isbn("123")
                .title("The adventures of something")
                .publicationDate(LocalDate.now())
                .genre(BookGenreEnum.FANTASY)
                .price(BigDecimal.valueOf(10).setScale(2, RoundingMode.UNNECESSARY))
                .author(author)
                .build();
    }

    public static Book createBookWithTitleAndPriceAndGenreAndDate(String title, BigDecimal price, Author author){
        return Book.builder()
                .isbn("isbn")
                .title(title)
                .publicationDate(LocalDate.now())
                .genre(BookGenreEnum.FANTASY)
                .price(price)
                .author(author)
                .build();
    }

    public static Book createBookWithTitleAndPriceAndGenreAndDate(
            String title, BigDecimal price, BookGenreEnum genre, LocalDate publicationDate, Author author){
        return Book.builder()
                .isbn("isbn")
                .title(title)
                .publicationDate(publicationDate)
                .genre(genre)
                .price(price)
                .author(author)
                .build();
    }

}
