package com.miniproject.library.security;

import com.miniproject.library.entity.Role;
import com.miniproject.library.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) {

        com.miniproject.library.entity.User authenticatedUser = userRepository.findByUsername(username);

        final String authenticatedUsername = String.valueOf(authenticatedUser.getUsername());
        final String authenticatedPassword = authenticatedUser.getPassword();
        final Role userRole = authenticatedUser.getRole();
        final SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_"+userRole.name());

        return new User(authenticatedUsername, authenticatedPassword, Collections.singletonList(grantedAuthority));
    }
}
