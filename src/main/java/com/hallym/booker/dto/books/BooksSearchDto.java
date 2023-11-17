package com.hallym.booker.dto.books;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BooksSearchDto {
    private String uid;
    private String isbn;
    private int bookstate;

    public BooksSearchDto() {
    }
}
