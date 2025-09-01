package io.github.bragabriel.library_api.mapper;

import io.github.bragabriel.library_api.dto.AuthorDto;
import io.github.bragabriel.library_api.model.Author;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

	Author toEntity(AuthorDto dto);

	AuthorDto toDto(Author author);
}
