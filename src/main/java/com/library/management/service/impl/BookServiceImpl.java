package com.library.management.service.impl;

import com.library.management.dto.BookAllDto;
import com.library.management.dto.BookDto;
import com.library.management.enums.LibraryExceptionType;
import com.library.management.enums.ModuleType;
import com.library.management.exception.LibraryException;
import com.library.management.mapper.BookMapper;
import com.library.management.model.Book;
import com.library.management.repository.BookRepository;
import com.library.management.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public Book save(BookDto bookDto) {
        Book book = BookMapper.INSTANCE.bookFromBookDto(bookDto);
        if(bookRepository.existsByTitleIgnoreCase(book.getTitle())){
            LibraryException.throwException(ModuleType.LIBRARY, LibraryExceptionType.BOOK_PRESENT_BY_TITLE, bookDto.getTitle());
        }
        return bookRepository.save(book);
    }

    @Override
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book findById(Long id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if(optionalBook.isEmpty()){
            LibraryException.throwException(ModuleType.LIBRARY, LibraryExceptionType.BOOK_NOT_FOUND, id.toString());
        }
        return optionalBook.get();
    }

    @Override
    public void deleteBook(Long id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if(optionalBook.isEmpty()){
            LibraryException.throwException(ModuleType.LIBRARY, LibraryExceptionType.BOOK_NOT_FOUND, id.toString());
        }
        bookRepository.deleteById(id);
    }

    @Override
    public Book updateBook(BookAllDto bookAllDto) {
        Optional<Book> optionalBook = bookRepository.findById(bookAllDto.getId());
        if(optionalBook.isEmpty()){
            LibraryException.throwException(ModuleType.LIBRARY, LibraryExceptionType.BOOK_NOT_FOUND, bookAllDto.getId().toString());
        }
        if(bookRepository.existsByTitleIgnoreCaseAndIdNot(bookAllDto.getTitle(), bookAllDto.getId())){
            LibraryException.throwException(ModuleType.LIBRARY, LibraryExceptionType.BOOK_PRESENT_BY_TITLE, bookAllDto.getTitle());
        }
        Book book = BookMapper.INSTANCE.bookFromBookAllDto(bookAllDto);
        return bookRepository.save(book);
    }
}
