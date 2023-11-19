package com.hallym.booker.service;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.hallym.booker.dto.GCP.ResponseUploadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GCPService {

    @Value("${spring.cloud.gcp.storage.credentials.location.bucket}")
    private String bucketName;

    @Value("${spring.cloud.gcp.storage.credentials.location.project-id}")
    private String projectId;

    private String imgUrl = "https://storage.googleapis.com/booker-v3/";

    public ResponseUploadDto uploadImage(MultipartFile image) throws IOException {
        BlobInfo blobInfo;
        try (InputStream inputStream = image.getInputStream()) {
            Credentials credentials = GoogleCredentials.fromStream(new FileInputStream("src\\main\\resources\\project-booker-404207-83e308ade68a.json"));
            Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();

            String uuid = UUID.randomUUID().toString();
            String ext = image.getContentType();

            blobInfo = storage.createFrom(
                    BlobInfo.newBuilder(bucketName, uuid)
                            .setContentType(ext)
                            .build(),
                    image.getInputStream()
            );
        }
        return new ResponseUploadDto(blobInfo.getName(), (imgUrl + blobInfo.getName()));
    }

    public String deleteImage(String fileName) throws IOException {
        Credentials credentials = GoogleCredentials.fromStream(new FileInputStream("src\\main\\resources\\project-booker-404207-83e308ade68a.json"));
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();

        Blob blob = storage.get(bucketName, fileName);

        if (blob == null) {
            return "blob is null";
        }

        Storage.BlobSourceOption precondition =
                Storage.BlobSourceOption.generationMatch(blob.getGeneration());

        storage.delete(bucketName, fileName, precondition);

        return "delete success";
    }

    public ResponseUploadDto updateImage(MultipartFile image, String fileName) throws IOException {
        Credentials credentials = GoogleCredentials.fromStream(new FileInputStream("src\\main\\resources\\project-booker-404207-83e308ade68a.json"));
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();

        deleteImage(fileName);
        ResponseUploadDto responseUploadDto = uploadImage(image);

        return new ResponseUploadDto(responseUploadDto.getImageName(), responseUploadDto.getImageUrl());
    }
}
