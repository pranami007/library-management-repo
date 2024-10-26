package com.library.management.mapper;

import com.library.management.dto.BookAllDto;
import com.library.management.dto.BookDto;
import com.library.management.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BookMapper {

    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    Book bookFromBookDto(BookDto bookDto);

    BookDto bookDtoFromBook(Book book);

    Book bookFromBookAllDto(BookAllDto bookAllDto);
}
