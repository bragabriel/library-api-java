package io.github.bragabriel.library_api.validator;

import java.util.Optional;

import org.springframework.stereotype.Component;

import io.github.bragabriel.library_api.exceptions.DuplicatedRegisterException;
import io.github.bragabriel.library_api.exceptions.InvalidFieldException;
import io.github.bragabriel.library_api.model.Book;
import io.github.bragabriel.library_api.repository.BookRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BookValidator {

    private static final int BOOK_PUBLICATION_DATE_MIN_YEAR = 2020;

    private final BookRepository bookRepository;

    public void validate(Book book){
        if(existBookWithIsbn(book)){
            throw new DuplicatedRegisterException("ISBN already registered");
        }

        if(isMandatoryPriceNull(book)){
            throw new InvalidFieldException("price", "For books published in 2020 or later, the price is required.");
        }
    }

    private boolean isMandatoryPriceNull(Book book) {
        return book.getPrice() == null &&
            book.getPublicationDate().getYear() >= BOOK_PUBLICATION_DATE_MIN_YEAR;
    }

    public boolean existBookWithIsbn(Book book){
        Optional<Book> foundBook = bookRepository.findByIsbn(book.getIsbn());

        if(book.getId() == null){
            return foundBook.isPresent();
        }

        return foundBook
                .map(Book::getId)
                .stream()
                .anyMatch(id -> id.equals(book.getId()));
    }
}
