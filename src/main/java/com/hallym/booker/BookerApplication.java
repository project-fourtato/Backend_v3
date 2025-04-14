package com.hallym.booker;

import com.hallym.booker.domain.Profile;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//** if you use GCP, remove exclude argument! **
@SpringBootApplication(
		exclude = {
				org.springframework.cloud.gcp.autoconfigure.storage.GcpStorageAutoConfiguration.class
		}
)
public class BookerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookerApplication.class, args);

	}

}
