package com.hallym.booker.dto.books;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SaleLibraryDto {
    private String libName;
    private String address;
    private String tel;
    private String latitude; //위도
    private String longitude; //경도
    private String homepage;
    private String closed;
    private String operatingTime;
}
