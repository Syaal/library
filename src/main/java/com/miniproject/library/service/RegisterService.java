package com.miniproject.library.service;

import com.miniproject.library.dto.register.RegisterRequest;
import com.miniproject.library.entity.*;
import com.miniproject.library.repository.AnggotaRepository;
import com.miniproject.library.repository.LibrarianRepository;
import com.miniproject.library.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class RegisterService {
    private final UserRepository userRepository;
    private final LibrarianRepository librarianRepository;
    private final AnggotaRepository anggotaRepository;

    private final ModelMapper mapper = new ModelMapper();


    //regis
    public User register(RegisterRequest registerRequest, String role){
        role = role.toUpperCase();
        if (role.equals("LIBRARIAN")){
            Librarian librarian = new Librarian();
            librarianRepository.save(librarian);
        }else if (role.equals("VISITOR")){
            Anggota anggota = new Anggota();
            anggotaRepository.save(anggota);
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid role");
        }
        User regis = mapper.map(registerRequest, User.class);
        regis.setPassword(passwordEncoder().encode(registerRequest.getPassword()));
        regis.setRole(Role.valueOf(role));
        userRepository.save(regis);
        return regis;
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
