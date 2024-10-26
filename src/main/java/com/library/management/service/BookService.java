package com.library.management.service;

import com.library.management.dto.BookAllDto;
import com.library.management.dto.BookDto;
import com.library.management.model.Book;

import java.util.List;

public interface BookService {

    Book save(BookDto bookDto);

    List<Book> getAll();

    void deleteBook(Long id);

    Book updateBook(BookAllDto bookAllDto);

    Book findById(Long id);
}
