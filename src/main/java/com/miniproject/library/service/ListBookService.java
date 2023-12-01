package com.miniproject.library.service;

import com.miniproject.library.dto.ListBook.ListBookResponse;
import com.miniproject.library.entity.ListBook;
import com.miniproject.library.repository.ListBookRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ListBookService {
    private final ListBookRepository listBookRepository;
    private final ModelMapper mapper = new ModelMapper();

    public List<ListBookResponse>getAll(){
        List<ListBook> list = listBookRepository.findAll();
        return list.stream().map(listBook -> mapper.map(listBook, ListBookResponse.class)).toList();
    }

    public ListBookResponse getById(Integer id) {
        Optional<ListBook> listBookOptional = listBookRepository.findById(id);
        if (listBookOptional.isPresent()) {
            ListBook book = listBookOptional.get();
            return mapper.map(book, ListBookResponse.class);
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "List tidak ditemukan");
        }
    }
}
