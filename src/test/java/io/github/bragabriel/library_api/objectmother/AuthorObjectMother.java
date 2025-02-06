package io.github.bragabriel.library_api.objectmother;

import io.github.bragabriel.library_api.model.Author;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;

@UtilityClass
public class AuthorObjectMother {

    public static Author createAuthor(){
        return Author.builder()
                .name("Gabriel")
                .birthdate(LocalDate.of(2000, 1, 31))
                .nationality("Brazilian")
                .build();
    }
}
