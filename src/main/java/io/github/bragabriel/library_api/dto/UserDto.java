package io.github.bragabriel.library_api.dto;

import java.util.List;

public record UserDto(String login, String password, List<String> roles) {
}
