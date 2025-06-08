package com.example.productmanagementsystem.service;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.messages.Bucket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

@Service
public class MinioAdapter {
    @Autowired
    MinioClient minioClient;

    @Value("${minio.buckek.name}")
    String defaultBucketName;

    @Value("${minio.default.folder}")
    String defaultBaseFolder;

    public List<Bucket> getBucketList() {
        try {
            return minioClient.listBuckets();
        }catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void uploadFile(String name, byte[] data) {
        File file = new File("/tmp/" + name);
        file.canWrite();
        file.canRead();
        try{
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(data);
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(defaultBucketName)
                            .object(defaultBaseFolder + name)
                            .stream(new FileInputStream(file), file.length(), -1)
                            .contentType("application/octet-stream")
                            .build()
            );
        }catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
