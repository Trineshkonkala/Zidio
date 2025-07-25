package com.example.zidio;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/upload")
@CrossOrigin("*")
public class FileUploadController {

    private final String UPLOAD_DIR = System.getProperty("user.dir") + File.separator + "uploads";

    @PostMapping("/resume")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            // Check if file is empty
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("File is empty.");
            }

            // Create uploads directory if it doesn't exist
            File dir = new File(UPLOAD_DIR);
            if (!dir.exists() && !dir.mkdirs()) {
                return ResponseEntity.status(500).body("Could not create upload directory.");
            }

            // Save file
            String filePath = UPLOAD_DIR + File.separator + file.getOriginalFilename();
            File dest = new File(filePath);
            file.transferTo(dest);

            return ResponseEntity.ok("File uploaded successfully: " + file.getOriginalFilename());
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to upload file: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Unexpected error: " + e.getMessage());
        }
    }
}
