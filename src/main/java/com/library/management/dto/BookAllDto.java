package com.library.management.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BookAllDto extends BookDto{

    @NotNull(message = "Id must not be blank")
    private Long id;

}
