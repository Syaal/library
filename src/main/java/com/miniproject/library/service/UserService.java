package com.miniproject.library.service;

import com.miniproject.library.dto.user.UserRequest;
import com.miniproject.library.dto.user.UserResponse;
import com.miniproject.library.entity.User;
import com.miniproject.library.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    private final ModelMapper mapper = new ModelMapper();

    public List<UserResponse>getAll(){
        List<User> userList=userRepository.findAll();
        return userList.stream().map(user -> mapper.map(user,UserResponse.class)).collect(Collectors.toList());
    }

    public UserResponse getById(Integer id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return mapper.map(user, UserResponse.class);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pengguna tidak ditemukan");
        }
    }

    public void deleteById(Integer id) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            userRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pengguna tidak ditemukan");
        }
    }
    public UserResponse updateById(Integer id,@Valid UserRequest userRequest) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            User existingUser = userOptional.get();
            ModelMapper modelMapper = new ModelMapper();
            modelMapper.map(userRequest, existingUser);
            userRepository.save(existingUser);

            return modelMapper.map(existingUser, UserResponse.class);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pengguna tidak ditemukan");
        }
    }
}
