package io.github.bragabriel.library_api.mapper;

import org.mapstruct.Mapper;

import io.github.bragabriel.library_api.dto.UserDto;
import io.github.bragabriel.library_api.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserDto dto);
}
