package com.hallym.booker.dto.journals;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FindBooksInfoDto {
    private String title;
    private String author;
    private String pubDate;
    private String description;
    private String cover;
    private String categoryName;
    private String publisher;

    public FindBooksInfoDto() {}
}
