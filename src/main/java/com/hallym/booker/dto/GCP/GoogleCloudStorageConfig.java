package com.hallym.booker.dto.GCP;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

@Configuration
@ConditionalOnProperty(name = "gcp.enabled", havingValue = "true", matchIfMissing = false)
public class GoogleCloudStorageConfig {

    public GoogleCloudStorageConfig() {
    }

    @Bean
    public Storage storage() throws IOException {

        ClassPathResource resource = new ClassPathResource("project-booker-404207-83e308ade68a.json");
        GoogleCredentials credentials = GoogleCredentials.fromStream(resource.getInputStream());
        String projectId = "project-booker-404207";
        return StorageOptions.newBuilder()
                .setProjectId(projectId)
                .setCredentials(credentials)
                .build()
                .getService();
    }
}
