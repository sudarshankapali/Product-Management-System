package com.example.productmanagementsystem.config;
import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MInioConfig {
    @Value("$minio.access.name")
    String accessKey;
    @Value("minio.access.secret")
    String secretKey;
    @Value("minio.url")
    String minioURL;

    @Bean
    public MinioClient minioClient() {
        try {
            return MinioClient.builder()
                    .endpoint(minioURL)
                    .credentials(accessKey, secretKey)
                    .build();
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
        }

}
