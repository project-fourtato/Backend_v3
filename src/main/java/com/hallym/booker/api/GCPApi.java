package com.hallym.booker.api;

import com.hallym.booker.dto.GCP.ResponseDeleteEntity;
import com.hallym.booker.dto.GCP.ResponseUpdateEntity;
import com.hallym.booker.dto.GCP.ResponseUploadDto;
import com.hallym.booker.dto.GCP.ResponseUploadEntity;
import com.hallym.booker.service.GCPService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequiredArgsConstructor
public class GCPApi {

    @Autowired
    private final GCPService gcpService;

    @Value("${spring.cloud.gcp.storage.credentials.location.bucket}")
    private String bucketName;

    @Value("${spring.cloud.gcp.storage.credentials.location.project-id}")
    private String projectId;

    @PostMapping("/GCP/upload")
    public ResponseUploadEntity uploadImage(@RequestParam("file") MultipartFile file)
            throws IOException {
        if(file.isEmpty()) {
            return new ResponseUploadEntity("is empty");
        }

        File tempFile = File.createTempFile("temp", ".jpg");
        try {
            file.transferTo(tempFile);
            ResponseUploadDto responseUploadDto = gcpService.uploadImage(file);
            return new ResponseUploadEntity("upload success", responseUploadDto.getImageName());
        } finally {
            Path tempPath = Paths.get(tempFile.getAbsolutePath());
            try {
                Files.delete(tempPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @PostMapping("/GCP/delete")
    public ResponseDeleteEntity deleteImage(String fileName)
            throws IOException{
        if(fileName.isEmpty()) {
            return new ResponseDeleteEntity("is empty");
        }
        return new ResponseDeleteEntity(gcpService.deleteImage(fileName));
    }

    @PostMapping("/GCP/update")
    public ResponseUpdateEntity updateImage(@RequestParam("file") MultipartFile file, String nameFile)
            throws IOException {
        if(file.isEmpty()) {
            return new ResponseUpdateEntity("is empty");
        }
        ResponseUploadDto responseUploadDto = gcpService.updateImage(file, nameFile);
        return new ResponseUpdateEntity("update success", responseUploadDto.getImageName());
    }
}
