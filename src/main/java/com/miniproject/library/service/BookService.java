package com.miniproject.library.service;

import com.miniproject.library.dto.book.BookRequest;
import com.miniproject.library.dto.book.BookResponse;
import com.miniproject.library.entity.Book;
import com.miniproject.library.entity.Category;
import com.miniproject.library.repository.BookRepository;
import com.miniproject.library.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper mapper;

    public BookResponse addBook(BookRequest request){
        Book book = new Book();
        Optional<Category> optionalCategory = categoryRepository.findById(request.getCategoryId());
        if (optionalCategory.isPresent()){
            Category category = optionalCategory.get();
            book.setAuthor(request.getAuthor());
            book.setPublisher(request.getPublisher());
            book.setPublicationDate(request.getPublicationDate());
            book.setStock(request.getStock());
            book.setTitle(request.getTitle());
            book.setSummary(request.getSummary());
            book.setCategory(category);
            bookRepository.save(book);

            return mapper.map(book,BookResponse.class);
        }
        throw new IllegalArgumentException("Category Id It's Not Exist");
    }

    public BookResponse updateBook(BookRequest request, Integer id){
        Optional<Book> optionalBook = bookRepository.findById(id);
        Optional<Category> optionalCategory = categoryRepository.findById(request.getCategoryId());
        if (optionalBook.isPresent() && optionalCategory.isPresent()){
            Book book = optionalBook.get();
            Category category = optionalCategory.get();
            book.setId(id);
            book.setAuthor(request.getAuthor());
            book.setPublisher(request.getPublisher());
            book.setPublicationDate(request.getPublicationDate());
            book.setStock(request.getStock());
            book.setTitle(request.getTitle());
            book.setSummary(request.getSummary());
            book.setCategory(category);
            bookRepository.save(book);

            return mapper.map(book,BookResponse.class);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id Book Not Found");
    }

    public List<Book> getAllBook(){
        return bookRepository.findAll();
    }

    public Book getBookByIdBook(Integer id){
        return bookRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Id Book It's Not Exist!!!"));
    }
}
