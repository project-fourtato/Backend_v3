package com.hallym.booker.service;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GCPService {

    @Value("${spring.cloud.gcp.storage.credentials.location.bucket}")
    private String bucketName;

    @Value("${spring.cloud.gcp.storage.credentials.location.project-id}")
    private String projectId;

    private String imgUrl = "https://storage.googleapis.com/booker-v3/";

    public String uploadImage(MultipartFile image) throws IOException {
        Credentials credentials = GoogleCredentials.fromStream(new FileInputStream("C:\\Users\\82108\\OneDrive\\바탕 화면\\Booker_ver3\\Backend_v3\\src\\main\\resources\\project-booker-404207-83e308ade68a.json"));
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();

        String uuid = UUID.randomUUID().toString();
        String ext = image.getContentType();

        BlobInfo blobInfo = storage.createFrom(
                BlobInfo.newBuilder(bucketName, uuid)
                        .setContentType(ext)
                        .build(),
                image.getInputStream()
        );

        return blobInfo.getName();
    }

    public String deleteImage(String fileName) throws IOException {
        Credentials credentials = GoogleCredentials.fromStream(new FileInputStream("C:\\Users\\82108\\OneDrive\\바탕 화면\\Booker_ver3\\Backend_v3\\src\\main\\resources\\project-booker-404207-83e308ade68a.json"));
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

    public String updateImage(MultipartFile image, String fileName) throws IOException {
        Credentials credentials = GoogleCredentials.fromStream(new FileInputStream("C:\\Users\\82108\\OneDrive\\바탕 화면\\Booker_ver3\\Backend_v3\\src\\main\\resources\\project-booker-404207-83e308ade68a.json"));
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();

        deleteImage(fileName);
        String link = uploadImage(image);

        return link;
    }
}
