package com.miniproject.library.service;

import com.miniproject.library.dto.register.RegisterRequest;
import com.miniproject.library.entity.User;
import com.miniproject.library.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class RegisterService {
    private final UserRepository userRepository;

    private final ModelMapper mapper = new ModelMapper();


    //regis
    public User register(RegisterRequest registerRequest){
        mapper.getConfiguration().setAmbiguityIgnored(true);
        User regis = mapper.map(registerRequest, User.class);
        userRepository.save(regis);

        throw new ResponseStatusException(HttpStatus.CREATED, "Register Berhasil");
    }
}
