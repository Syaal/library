package com.miniproject.library.service;

import com.miniproject.library.dto.borrow.BorrowResponse;
import com.miniproject.library.dto.returnbook.ReturnRequest;
import com.miniproject.library.dto.returnbook.ReturnResponse;
import com.miniproject.library.entity.BorrowBook;
import com.miniproject.library.entity.ReturnBook;
import com.miniproject.library.repository.ReturnRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReturnService {
    private final ReturnRepository returnRepository;

    private final ModelMapper mapper = new ModelMapper();

    //create returnBook
    public ReturnResponse createReturnBook(ReturnRequest returnRequest){
        ReturnBook returnBook =  mapper.map(returnRequest,ReturnBook.class);

        return mapper.map(returnBook,ReturnResponse.class);
    }

    //get all returnBook
    public List<ReturnResponse> getAllReturnBook(){
        List<ReturnBook> returnBooks = returnRepository.findAll();
        return returnBooks.stream().map(returnBook -> mapper.map(returnBook,ReturnResponse.class)).toList();
    }

    //get returnBook By id
    public ReturnResponse getReturnBookById(Integer id){
        Optional<ReturnBook> returnBookOptional = returnRepository.findById(id);
        if (returnBookOptional.isPresent()){
            ReturnBook returnBook = returnBookOptional.get();
            return mapper.map(returnBook,ReturnResponse.class);
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Return not found");
        }
    }
}
