package com.library.management.controller;

import com.library.management.dto.BookAllDto;
import com.library.management.dto.BookDto;
import com.library.management.dto.ResponseVO;
import com.library.management.model.Book;
import com.library.management.service.BookService;
import jakarta.validation.Valid;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/book")
@Data
public class BookController {

    private final BookService bookService;

    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> saveBook(@Valid @RequestBody BookDto bookDto){
        Book book = bookService.save(bookDto);
        return ResponseEntity.ok().body(ResponseVO.create(HttpStatus.CREATED.value(),"Book created successfully",book));
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<?> getAllBooks(){
        List<Book> bookList = bookService.getAll();
        return ResponseEntity.ok().body(ResponseVO.create(HttpStatus.OK.value(), "All books fetched successfully", bookList));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<?> findById(@PathVariable Long id){
        Book book = bookService.findById(id);
        return ResponseEntity.ok().body(ResponseVO.create(HttpStatus.OK.value(), "Book fetched successfully", book));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteBook(@PathVariable Long id){
        bookService.deleteBook(id);
        return ResponseEntity.ok().body(ResponseVO.create(HttpStatus.OK.value(), "Book deleted successfully"));
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateBook(@Valid @RequestBody BookAllDto bookAllDto){
        Book book = bookService.updateBook(bookAllDto);
        return ResponseEntity.ok().body(ResponseVO.create(HttpStatus.OK.value(), "Book updated successfully", book));
    }
}
