package com.jars.HospitalManagement;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
public class HospitalManagementApplication {

	public static void main(String[] args) {

//		Dotenv dotenv = Dotenv.configure()
//				.ignoreIfMissing()   // 🔐 prevents crash
//				.load();
//
//		// Optional debug check
//		System.out.println("Google Client ID: " + dotenv.get("GOOGLE_CLIENT_ID"));

		SpringApplication.run(HospitalManagementApplication.class, args);
	}
}

