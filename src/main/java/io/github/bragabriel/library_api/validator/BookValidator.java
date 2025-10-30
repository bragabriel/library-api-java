package io.github.bragabriel.library_api.validator;

import java.util.Optional;

import org.springframework.stereotype.Component;

import io.github.bragabriel.library_api.exceptions.DuplicatedRegisterException;
import io.github.bragabriel.library_api.model.Book;
import io.github.bragabriel.library_api.repository.BookRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BookValidator {

    private final BookRepository bookRepository;

    public void validate(Book book){
        if(existBookWithIsbn(book)){
            throw new DuplicatedRegisterException("ISBN already registered");
        }
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
