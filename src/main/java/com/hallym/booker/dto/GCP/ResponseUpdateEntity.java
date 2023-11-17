package com.hallym.booker.dto.GCP;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseUpdateEntity {
    private String data;
    private String filePath;

    public ResponseUpdateEntity() {
    }

    public ResponseUpdateEntity(String responseM) {
        responseM = responseM;
    }
}
