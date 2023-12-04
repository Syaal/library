package com.miniproject.library.service;

import com.miniproject.library.dto.book.BookRequest;
import com.miniproject.library.dto.book.BookResponse;
import com.miniproject.library.entity.Book;
import com.miniproject.library.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final ModelMapper mapper = new ModelMapper();

    //create Book
    public BookResponse createBook(BookRequest bookRequest){
        Book book = mapper.map(bookRequest, Book.class);
        book.setRead(0);
        bookRepository.save(book);
        return mapper.map(book,BookResponse.class);
    }

    //get all book
    public List<BookResponse> getAllBook(){
        List<Book> bookList = bookRepository.findAll();
        return bookList.stream().map(book -> mapper.map(book,BookResponse.class)).toList();
    }

    //get book by id
    public BookResponse getBookById(Integer id){
        Optional<Book> bookOptional = bookRepository.findById(id);
        if (bookOptional.isPresent()){
            Book book = bookOptional.get();
            return mapper.map(book, BookResponse.class);
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found");
        }
    }

    //update book
    public BookResponse updateBook(Integer id, BookRequest bookRequest){
        Optional<Book> bookOptional = bookRepository.findById(id);
        if (bookOptional.isPresent()){
            Book book = bookOptional.get();
            mapper.map(bookRequest,book);
            bookRepository.save(book);
            return mapper.map(book,BookResponse.class);
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found");
        }
    }

    //delete book
    public void deleteById(Integer id){
        Optional<Book> bookOptional = bookRepository.findById(id);
        if (bookOptional.isPresent()){
            bookRepository.deleteById(id);
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found");
        }
    }
}
