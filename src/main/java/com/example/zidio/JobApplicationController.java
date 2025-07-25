package com.example.zidio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/applications")
@CrossOrigin("*")
public class JobApplicationController {

    @Autowired
    private JobApplicationRepository applicationRepository;

    // Apply for a job
    @PostMapping("/apply")
    public ResponseEntity<String> applyToJob(@RequestBody JobApplication application) {
        applicationRepository.save(application);
        return ResponseEntity.ok("Application submitted successfully!");
    }

    // View applications by student
    @GetMapping("/student/{studentId}")
    public List<JobApplication> getApplicationsByStudent(@PathVariable int studentId) {
        return applicationRepository.findByStudentId(studentId);
    }

    // View applications by jobPostId (for recruiter)
    @GetMapping("/job/{jobPostId}")
    public List<JobApplication> getApplicationsByJob(@PathVariable int jobPostId) {
        return applicationRepository.findByJobPostId(jobPostId);
    }
}
