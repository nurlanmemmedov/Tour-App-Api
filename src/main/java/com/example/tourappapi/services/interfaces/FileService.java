package com.example.tourappapi.services.interfaces;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface FileService {
    /**
     * Uploads new file
     * @param file
     * @return
     */
    String upload(File file);

    /**
     * deletes file by name
     * @param filename
     */
    void delete(String filename);
}

