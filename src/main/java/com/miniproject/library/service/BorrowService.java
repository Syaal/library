package com.miniproject.library.service;

import com.miniproject.library.dto.borrow.BorrowRequest;
import com.miniproject.library.dto.borrow.BorrowResponse;
import com.miniproject.library.entity.BorrowBook;
import com.miniproject.library.repository.BorrowRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BorrowService {

    private final BorrowRepository borrowRepository;
    private final ModelMapper mapper = new ModelMapper();


    //create borrow
    public BorrowResponse createBorrow(BorrowRequest borrowRequest){
        BorrowBook borrowBook =  mapper.map(borrowRequest,BorrowBook.class);

        return mapper.map(borrowBook,BorrowResponse.class);
    }

    //get all borrow
    public List<BorrowResponse> getAllBorrow(){
        List<BorrowBook> borrowBooks = borrowRepository.findAll();
        return borrowBooks.stream().map(borrowBook -> mapper.map(borrowBooks,BorrowResponse.class)).toList();
    }

    public BorrowResponse getBorrowById(Integer id){
        Optional<BorrowBook> borrowBookOptional = borrowRepository.findById(id);
        if (borrowBookOptional.isPresent()){
            BorrowBook borrowBook = borrowBookOptional.get();
            return mapper.map(borrowBook,BorrowResponse.class);
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Borrow not found");
        }
    }


}
