package com.example.tourappapi.services;

import com.example.tourappapi.services.interfaces.FileService;
import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import com.google.firebase.cloud.StorageClient;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    private static final String MY_URL = "https://firebasestorage.googleapis.com/v0/b/turboaz-e8cff.appspot.com/o/%s?alt=media";
    String TEMP_URL = "";

    @Override
    public String upload(File file) {
        try {
            String fileName = file.getName();
            fileName = UUID.randomUUID().toString().concat(this.getExtension(fileName));
            TEMP_URL = this.uploadFile(file, fileName);
            file.delete();
            return TEMP_URL;
        } catch (Exception e) {
            e.printStackTrace();
            return "400";
        }
    }

    @Override
    public void delete(String fileName){
        Bucket bucket = StorageClient.getInstance().bucket();
        Blob blob= bucket.get(fileName);
        blob.delete();
    }

    private String uploadFile(File file, String fileName) throws IOException {
        BlobId blobId = BlobId.of("turboaz-e8cff.appspot.com", fileName);
        String type = Files.probeContentType(file.toPath());
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(type).build();
        Credentials credentials = GoogleCredentials.fromStream(new FileInputStream("src/main/resources/firebase.json"));
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        storage.create(blobInfo, Files.readAllBytes(file.toPath()));
        return String.format(MY_URL, URLEncoder.encode(fileName, StandardCharsets.UTF_8));
    }

    private File convertToFile(MultipartFile multipartFile, String fileName) throws IOException {
        File tempFile = new File(fileName);
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(multipartFile.getBytes());
            fos.close();
        }
        return tempFile;
    }

    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }
}