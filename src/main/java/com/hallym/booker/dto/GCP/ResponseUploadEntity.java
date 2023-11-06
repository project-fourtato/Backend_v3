package com.hallym.booker.dto.GCP;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseUploadEntity {
    private String data;
    private String filePath;

    public ResponseUploadEntity(String responseM) {
        responseM = responseM;
    }
}
