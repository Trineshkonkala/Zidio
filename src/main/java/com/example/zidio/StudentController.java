package com.example.zidio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/students")
@CrossOrigin("*")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @PostMapping("/register")
    public ResponseEntity<String> registerStudent(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("file") MultipartFile file
    ) {
        try {
            String uploadDir = "uploads/";
            File dir = new File(uploadDir);
            if (!dir.exists()) dir.mkdirs();

            String filePath = uploadDir + file.getOriginalFilename();
            file.transferTo(new File(filePath));

            Student student = new Student();
            student.setName(name);
            student.setEmail(email);
            student.setPassword(password);
            student.setResumeUrl(file.getOriginalFilename());

            studentRepository.save(student);

            return ResponseEntity.ok("Student registered successfully with resume!");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to register student.");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Student student) {
        Student found = studentRepository.findByEmailAndPassword(student.getEmail(), student.getPassword());
        if (found != null) {
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }

    @GetMapping("/profile/{id}")
    public ResponseEntity<Student> getProfile(@PathVariable int id) {
        Student student = studentRepository.findById(id).orElse(null);
        if (student != null) {
            return ResponseEntity.ok(student);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
