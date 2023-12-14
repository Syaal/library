package com.miniproject.library.service;

import com.miniproject.library.dto.librarian.LibrarianRequest;
import com.miniproject.library.dto.librarian.LibrarianResponse;
import com.miniproject.library.entity.Librarian;
import com.miniproject.library.repository.LibrarianRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

class LibrarianServiceTest {
    private LibrarianRepository librarianRepository;
    private LibrarianService librarianService;


    @BeforeEach
    void setUp() {
        librarianRepository = mock(LibrarianRepository.class);
        librarianService = new LibrarianService(librarianRepository);
    }


    @Test
    void TestUpdateLibrarian(){
        Integer librarianId = 1;
        LibrarianRequest request = new LibrarianRequest();
        request.setNip(1234542L);
        request.setName("Arsyal");
        request.setEmail("arsyal@gmail.com");
        request.setPhone("1256756555");
        request.setAddress("Bekasi Selatan");
        request.setGender("Male");

        Librarian existingLibrarian = new Librarian();
        existingLibrarian.setId(librarianId);

        when(librarianRepository.findById(anyInt())).thenReturn(Optional.of(existingLibrarian));
        when(librarianRepository.save(any(Librarian.class))).thenReturn(new Librarian());

        LibrarianResponse response = librarianService.updateLibrarian(request, librarianId);

        assertNotNull(response);
        assertEquals(1234542L, response.getNip());
        assertEquals("Arsyal", response.getName());
        assertEquals("arsyal@gmail.com", response.getEmail());
        assertEquals("1256756555", response.getPhone());
        assertEquals("Bekasi Selatan", response.getAddress());
        assertEquals("Male", response.getGender());
    }

    @Test
    void testUpdateLibrarianNotFound() {
        // Arrange
        Integer librarianId = 1;
        LibrarianRequest request = new LibrarianRequest();
        request.setNip(1234542L);
        request.setName("Fanny");
        request.setEmail("fanny@gmail.com");
        request.setPhone("125675651235");
        request.setAddress("Tambun");
        request.setGender("Male");

        when(librarianRepository.findById(librarianId)).thenReturn(Optional.empty());

        // Act and Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> librarianService.updateLibrarian(request, librarianId));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Id Librarian Not Found", exception.getReason());
    }

    @Test
    void testGetAllLibrarian() {
        // Asumsi jika memiliki beberapa data di repository
        Librarian sampleLibrarian = new Librarian();
        sampleLibrarian.setId(1);
        sampleLibrarian.setNip(12345L);
        sampleLibrarian.setName("Arsyal");
        sampleLibrarian.setEmail("arsyal@gmail.com");
        sampleLibrarian.setPhone("1256756555");
        sampleLibrarian.setAddress("Bekasi Selatan");
        sampleLibrarian.setGender("Male");
        List<Librarian> librarians = new ArrayList<Librarian>();
        librarians.add(sampleLibrarian);

        when(librarianRepository.findAll()).thenReturn(librarians);

        List<Librarian> librarianList = librarianService.getAllLibrarian();


        Librarian firstLibrarian = librarianList.get(0);
        assertNotNull(firstLibrarian.getId());
        assertEquals(12345L, firstLibrarian.getNip());
        assertEquals("Arsyal", firstLibrarian.getName());
        assertEquals("arsyal@gmail.com", firstLibrarian.getEmail());
        assertEquals("1256756555", firstLibrarian.getPhone());
        assertEquals("Bekasi Selatan", firstLibrarian.getAddress());
        assertEquals("Male", firstLibrarian.getGender());
        verify(librarianRepository, times(1)).findAll();
    }

    @Test
    void testGetLibrarianById() {
        Integer id = 1;

        Librarian sampleLibrarian = new Librarian();
        sampleLibrarian.setId(1);
        sampleLibrarian.setNip(12345L);
        sampleLibrarian.setName("Arsyal");
        sampleLibrarian.setEmail("arsyal@gmail.com");
        sampleLibrarian.setPhone("1256756555");
        sampleLibrarian.setAddress("Bekasi Selatan");
        sampleLibrarian.setGender("Male");

        when(librarianRepository.findById(id)).thenReturn(Optional.of(sampleLibrarian));

        Librarian librarian = librarianService.getLibrarianById(id);

        assertNotNull(librarian);

        assertEquals(id, librarian.getId());
        assertEquals(12345L, librarian.getNip());
        assertEquals("Arsyal", librarian.getName());
        assertEquals("arsyal@gmail.com", librarian.getEmail());
        assertEquals("1256756555", librarian.getPhone());
        assertEquals("Bekasi Selatan", librarian.getAddress());
        assertEquals("Male", librarian.getGender());

        verify(librarianRepository, times(1)).findById(id);
    }

    @Test
    void testGetLibrarianByIdNotFound() {
        // Arrange
        Integer librarianId = 1;

        when(librarianRepository.findById(librarianId)).thenReturn(Optional.empty());

        // Act and Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> librarianService.getLibrarianById(librarianId));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Id Librarian It's Not Exist!!!", exception.getReason());
    }

    @Test
    void testDeleteLibrarianById() {
        Integer librarianId = 1;

        assertDoesNotThrow(() -> librarianService.deleteLibrarianById(librarianId),
                "Unexpected exception during delete operation");
    }
}