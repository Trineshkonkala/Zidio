package com.example.zidio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recruiters")
@CrossOrigin("*")
public class RecruiterController {

    @Autowired
    private RecruiterRepository recruiterRepository;

    // Register Recruiter
    @PostMapping("/register")
    public ResponseEntity<String> registerRecruiter(@RequestBody Recruiter recruiter) {
        recruiterRepository.save(recruiter);
        return ResponseEntity.ok("Recruiter registered successfully");
    }

    // Login Recruiter
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Recruiter recruiter) {
        Recruiter found = recruiterRepository.findByEmailAndPassword(recruiter.getEmail(), recruiter.getPassword());
        if (found != null) {
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }

    // View Recruiter Profile
    @GetMapping("/profile/{id}")
    public ResponseEntity<Recruiter> getProfile(@PathVariable int id) {
        Recruiter recruiter = recruiterRepository.findById(id).orElse(null);
        if (recruiter != null) {
            return ResponseEntity.ok(recruiter);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
