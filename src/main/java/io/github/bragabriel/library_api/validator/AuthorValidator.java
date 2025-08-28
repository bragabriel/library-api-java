package io.github.bragabriel.library_api.validator;

import io.github.bragabriel.library_api.exceptions.DuplicatedRegisterException;
import io.github.bragabriel.library_api.model.Author;
import io.github.bragabriel.library_api.repository.AuthorRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthorValidator {

	private AuthorRepository authorRepository;

	public AuthorValidator(AuthorRepository authorRepository) {
		this.authorRepository = authorRepository;
	}

	public void validate(Author author){
		if(isAuthorAlreadyRegistered(author)){
			throw new DuplicatedRegisterException("Author already registered");
		}
	}

	public boolean isAuthorAlreadyRegistered(Author author){
		Optional<Author> authorOptional = authorRepository.findByNameAndBirthdateAndNationality(
				author.getName(), author.getBirthdate(), author.getNationality());

		//new register
		if(author.getId() == null){
			return authorOptional.isPresent();
		}

		//update
		return !author.getId().equals(authorOptional.get().getId()) && authorOptional.isPresent();
	}
}
