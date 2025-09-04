package io.github.bragabriel.library_api.controller;

import io.github.bragabriel.library_api.dto.AuthorDto;
import io.github.bragabriel.library_api.dto.ErrorResponse;
import io.github.bragabriel.library_api.exceptions.DuplicatedRegisterException;
import io.github.bragabriel.library_api.exceptions.NotAllowedException;
import io.github.bragabriel.library_api.mapper.AuthorMapper;
import io.github.bragabriel.library_api.model.Author;
import io.github.bragabriel.library_api.service.AuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
public class AuthorController implements GenericController {

	private final AuthorService authorService;
	private final AuthorMapper authorMapper;

	@PostMapping
	public ResponseEntity<Void> save(@RequestBody @Valid AuthorDto dto) {
		Author authorEntity = authorMapper.toEntity(dto);
		authorService.save(authorEntity);
		URI location = generateHeaderLocation(authorEntity.getId());
		return ResponseEntity.created(location).build();
	}

	@GetMapping("{id}")
	public ResponseEntity<AuthorDto> getDetails(@PathVariable("id") String id) {
		var idAuthor = UUID.fromString(id);
		Author author = authorService.getById(idAuthor)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

		AuthorDto dto = authorMapper.toDto(author);

		return ResponseEntity.ok(dto);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") String id) {
		var idAuthor = UUID.fromString(id);

		Author author = authorService.getById(idAuthor)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

		authorService.delete(author);

		return ResponseEntity.noContent().build();
	}

	@GetMapping
	public ResponseEntity<List<AuthorDto>> find(
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "nationality", required = false) String nationality) {
		List<Author> authors = authorService.find(name, nationality);

		List<AuthorDto> authorDtos = authors.stream()
				.map(authorMapper::toDto).collect(Collectors.toList());

		return ResponseEntity.ok(authorDtos);
	}

	@PutMapping("{id}")
	public ResponseEntity<Void> update(
			@PathVariable("id") String id,
			@RequestBody AuthorDto authorDto
	) {
		var idAuthor = UUID.fromString(id);
		Author author = authorService.getById(idAuthor)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

		author.setName(authorDto.name());
		author.setNationality(authorDto.nationality());
		author.setBirthdate(authorDto.birthdate());

		authorService.update(author);

		return ResponseEntity.noContent().build();
	}
}
