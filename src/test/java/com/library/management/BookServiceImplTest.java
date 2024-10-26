package com.library.management;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.library.management.dto.BookAllDto;
import com.library.management.dto.BookDto;
import com.library.management.exception.LibraryException;
import com.library.management.model.Book;
import com.library.management.repository.BookRepository;
import com.library.management.service.impl.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    private Book book;
    private BookDto bookDto;
    private BookAllDto bookAllDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Sample data for testing
        book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");
        book.setAuthor("Author Name");
        book.setPublicationYear(2023);

        bookDto = new BookDto();
        bookDto.setTitle("Test Book");
        bookDto.setAuthor("Author Name");
        bookDto.setPublicationYear(2023);

        bookAllDto = new BookAllDto();
        bookAllDto.setId(1L);
        bookAllDto.setTitle("Updated Book");
        bookAllDto.setAuthor("Updated Author");
        bookAllDto.setPublicationYear(2024);
    }

    @Test
    @DisplayName("Test saving a new book - Success scenario")
    void testSave_Success() {
        when(bookRepository.existsByTitleIgnoreCase(bookDto.getTitle())).thenReturn(false);
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        Book savedBook = bookService.save(bookDto);

        assertNotNull(savedBook, "The saved book should not be null");
        assertEquals(book.getTitle(), savedBook.getTitle(), "Book title should match");
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    @DisplayName("Test saving a book - Book already exists scenario")
    void testSave_BookAlreadyExists() {
        when(bookRepository.existsByTitleIgnoreCase(bookDto.getTitle())).thenReturn(true);

        LibraryException exception = assertThrows(LibraryException.class, () -> bookService.save(bookDto));
        assertEquals("Book already present with title : Test Book", exception.getMessage());
    }

    @Test
    @DisplayName("Test retrieving all books - Success scenario")
    void testGetAll_Success() {
        when(bookRepository.findAll()).thenReturn(Collections.singletonList(book));

        List<Book> books = bookService.getAll();

        assertNotNull(books, "The books list should not be null");
        assertEquals(1, books.size(), "The books list should contain one book");
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Test finding a book by ID - Success scenario")
    void testFindById_Success() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        Book foundBook = bookService.findById(1L);

        assertNotNull(foundBook, "The found book should not be null");
        assertEquals(book.getTitle(), foundBook.getTitle(), "Book title should match");
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Test finding a book by ID - Book not found scenario")
    void testFindById_BookNotFound() {
        when(bookRepository.findById(99L)).thenReturn(Optional.empty());

        LibraryException exception = assertThrows(LibraryException.class, () -> bookService.findById(99L));
        assertEquals("Book not found with id : 99", exception.getMessage());
        verify(bookRepository, times(1)).findById(99L);
    }

    @Test
    @DisplayName("Test deleting a book by ID - Success scenario")
    void testDeleteBook_Success() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        doNothing().when(bookRepository).deleteById(1L);

        bookService.deleteBook(1L);

        verify(bookRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Test deleting a book by ID - Book not found scenario")
    void testDeleteBook_BookNotFound() {
        when(bookRepository.findById(99L)).thenReturn(Optional.empty());

        LibraryException exception = assertThrows(LibraryException.class, () -> bookService.deleteBook(99L));
        assertEquals("Book not found with id : 99", exception.getMessage());
    }

    @Test
    @DisplayName("Test updating a book - Success scenario")
    void testUpdateBook_Success() {
        when(bookRepository.findById(bookAllDto.getId())).thenReturn(Optional.of(book));
        when(bookRepository.existsByTitleIgnoreCaseAndIdNot(bookAllDto.getTitle(), bookAllDto.getId())).thenReturn(false);
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        Book updatedBook = bookService.updateBook(bookAllDto);

        assertNotNull(updatedBook, "The updated book should not be null");
        assertEquals(bookAllDto.getTitle(), updatedBook.getTitle(), "Updated title should match");
        verify(bookRepository, times(1)).save(any(Book.class));
    }

}
