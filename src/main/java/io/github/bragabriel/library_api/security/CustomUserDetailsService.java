package io.github.bragabriel.library_api.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.springframework.security.core.userdetails.User.*;

import io.github.bragabriel.library_api.model.User;
import io.github.bragabriel.library_api.service.UserService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService service;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = service.getByLogin(login);

        if(user == null){
            throw new UsernameNotFoundException("User not found");
        }

        return builder()
                .username(user.getLogin())
                .password(user.getPassword())
                .roles(user.getRoles().toArray(new String[user.getRoles().size()]))
                .build();
    }
}
