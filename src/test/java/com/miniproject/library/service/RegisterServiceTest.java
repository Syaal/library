package com.miniproject.library.service;

import com.miniproject.library.dto.register.RegisterRequest;
import com.miniproject.library.entity.Anggota;
import com.miniproject.library.entity.Librarian;
import com.miniproject.library.entity.Role;
import com.miniproject.library.entity.User;
import com.miniproject.library.repository.AnggotaRepository;
import com.miniproject.library.repository.LibrarianRepository;
import com.miniproject.library.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class RegisterServiceTest {
    @InjectMocks
    RegisterService registerService;
    @Mock
    UserRepository userRepository;
    @Mock
    LibrarianRepository librarianRepository;
    @Mock
    AnggotaRepository anggotaRepository;

    private final ModelMapper mapper = new ModelMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
     void testRegisterLibrarian() {
        // Arrange
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("aku Visitor");
        registerRequest.setPassword("123");
        String role = "LIBRARIAN";

        // Mocking behavior
        when(userRepository.save(any())).thenReturn(new User());

        // Act
        User result = registerService.register(registerRequest, role);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(Role.LIBRARIAN, result.getRole());
        verify(librarianRepository, times(1)).save(any(Librarian.class));
        verify(anggotaRepository, never()).save(any(Anggota.class));
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
     void testRegisterVisitor() {
        // Arrange
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("aku Visitor");
        registerRequest.setPassword("123");
        String role = "VISITOR";
        User expectedUser = new User();
        expectedUser.setUsername(registerRequest.getUsername());
        expectedUser.setRole(Role.valueOf(role));
        expectedUser.setLibrarian(null);
        expectedUser.setAnggota(new Anggota());
        expectedUser.setPassword(registerService.passwordEncoder().encode(registerRequest.getPassword()));

        // Mocking behavior
        when(userRepository.save(any())).thenReturn(expectedUser);

        // Act
        User result = registerService.register(registerRequest, role);

        // Assert
        assertNotNull(result);
        assertEquals(Role.VISITOR, result.getRole());
        verify(librarianRepository, never()).save(any(Librarian.class));
        verify(anggotaRepository, times(1)).save(any(Anggota.class));
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
     void testRegisterInvalidRole() {
        // Arrange
        RegisterRequest registerRequest = new RegisterRequest();
        String role = "INVALID_ROLE";

        // Act and Assert
        assertThrows(ResponseStatusException.class, () -> registerService.register(registerRequest, role));
        verify(librarianRepository, never()).save(any(Librarian.class));
        verify(anggotaRepository, never()).save(any(Anggota.class));
        verify(userRepository, never()).save(any(User.class));
    }
}