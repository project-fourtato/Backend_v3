package com.hallym.booker.dto.books;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BooksDetailDto {
    String title;
    String author;
    String pubDate;
    String description;
    String isbn13;
    String cover;
    String categoryName;
    String publisher;

    public BooksDetailDto() {
    }
}
