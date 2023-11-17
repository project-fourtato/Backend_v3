package com.hallym.booker.dto.GCP;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseUploadDto {
    String imageName;
    String imageUrl;
}
