package com.hallym.booker.dto.books;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
public class BooksFindDto {
    private String title;
    private String author;
    private String isbn;
    private String publisher;
}
