package com.miniproject.library.service;

import com.miniproject.library.dto.librarian.LibrarianRequest;
import com.miniproject.library.dto.librarian.LibrarianResponse;
import com.miniproject.library.entity.Librarian;
import com.miniproject.library.repository.LibrarianRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LibrarianService {
    private final LibrarianRepository librarianRepository;

    private final ModelMapper mapper = new ModelMapper();

    //add visitor
    public LibrarianResponse createLibrarian(LibrarianRequest librarianRequest){
        Librarian librarian = mapper.map(librarianRequest, Librarian.class);
        librarianRepository.save(librarian);

        return mapper.map(librarian, LibrarianResponse.class);
    }

    //get by id
    public Librarian getByIdLibrarian(Integer id){
        Optional<Librarian> optionalLibrarian = librarianRepository.findById(id);
        if (optionalLibrarian.isPresent()){
            Librarian librarian = optionalLibrarian.get();
            return mapper.map(librarian, Librarian.class);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id Tidak Ditemukan");
        }
    }

    //get all librarian
    public List<Librarian> getAllLibrarian(){
        List<Librarian> librarianList = librarianRepository.findAll();
        return librarianList.stream().map(librarian -> mapper.map(librarian, Librarian.class))
                .toList();
    }
}
