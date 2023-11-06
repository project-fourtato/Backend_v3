package com.hallym.booker.dto.books;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateBooksStateRequest {
    private int bookstate;
    private int salestate;

    public UpdateBooksStateRequest() {}
}
