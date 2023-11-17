package com.hallym.booker.dto.books;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateBooksResponse {
    private String data;

    public CreateBooksResponse() {
    }
}
