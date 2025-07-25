package com.example.zidio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobs")
@CrossOrigin("*")
public class JobPostController {

    @Autowired
    private JobPostRepository jobPostRepository;

    // ✅ Post job (with recruiter ID)
    @PostMapping("/post")
    public ResponseEntity<String> postJob(@RequestBody JobPost jobPost) {
        jobPostRepository.save(jobPost);
        return ResponseEntity.ok("Job posted successfully!");
    }

    // ✅ View all jobs
    @GetMapping("/all")
    public ResponseEntity<List<JobPost>> getAllJobs() {
        List<JobPost> jobs = jobPostRepository.findAll();
        return ResponseEntity.ok(jobs);
    }

    // ✅ View jobs by recruiter ID
    @GetMapping("/recruiter/{recruiterId}")
    public ResponseEntity<List<JobPost>> getJobsByRecruiter(@PathVariable int recruiterId) {
        List<JobPost> jobs = jobPostRepository.findByRecruiterId(recruiterId);
        return ResponseEntity.ok(jobs);
    }
}
