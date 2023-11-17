package com.hallym.booker.dto.books;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BestsellerResultDto {
    private String BookName;
    private String BookAuthor;
    private String isbn;
    private String publisher;
    private String cover;

}
