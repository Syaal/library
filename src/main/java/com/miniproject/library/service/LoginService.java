package com.miniproject.library.service;

import com.miniproject.library.dto.user.UserRequest;
import com.miniproject.library.entity.User;
import com.miniproject.library.repository.UserRepository;
import com.miniproject.library.util.JwtToken;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final UserRepository userRepository;

    public User login(UserRequest userRequest){
        Optional<User> optionalUser =
                userRepository.findByUsernameAndPassword(
                        userRequest.getUsername(),
                        userRequest.getPassword());

        if (optionalUser.isPresent()){
            User user = optionalUser.get();
            user.setToken(JwtToken.getToken(userRequest));
            userRepository.save(user);
        }

        throw new ResponseStatusException(HttpStatus.FOUND, "Login Berhasil");
    }
}
