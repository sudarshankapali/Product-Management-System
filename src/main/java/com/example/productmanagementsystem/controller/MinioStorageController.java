package com.example.productmanagementsystem.controller;
import com.example.productmanagementsystem.service.MinioAdapter;
import io.minio.messages.Bucket;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class MinioStorageController {
    private final MinioAdapter minioAdapter;

    public MinioStorageController(MinioAdapter minioAdapter) {
        this.minioAdapter = minioAdapter;
    }


    @GetMapping("/buckets")
    public List<Bucket> listBuckets(){
        return minioAdapter.getBucketList();
    }

    @PostMapping(path = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Map<String, String> uploadFile(@RequestPart("file") MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File must not be empty");
        }

        minioAdapter.uploadFile(file.getOriginalFilename(), file.getBytes());

        Map<String, String> result = new HashMap<>();
        result.put("key", file.getOriginalFilename());
        return result;
    }

    @GetMapping(path = "/download")
    public ResponseEntity<ByteArrayResource> downloadFile(@RequestParam("file") String file) throws IOException {
        byte[] data = minioAdapter.downloadFile(file);
        ByteArrayResource resource = new ByteArrayResource(data);

        return ResponseEntity
                .ok()
                .contentLength(data.length)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
