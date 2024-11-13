package com.bidkoi.auctionkoi.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

@Configuration
public class FirebaseConfig {
     @Value("${fcm.credentials.file.path}")
     private String fcmCredentials;

     @Bean
     public FirebaseApp firebaseApp() throws IOException {

         FirebaseOptions options = FirebaseOptions.builder()
                 .setCredentials(GoogleCredentials.fromStream(new ClassPathResource(fcmCredentials).getInputStream())) // load data từ file json vào stream và set vào credentials
                 .build();

         return FirebaseApp.initializeApp(options);
     }
}
