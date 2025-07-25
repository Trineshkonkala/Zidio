package com.example.zidio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@CrossOrigin("*")
public class AdminController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private JobPostRepository jobPostRepository;

    // ✅ 1. Get all students
    @GetMapping("/students")
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // ✅ 2. Search students by email
    @GetMapping("/students/search")
    public List<Student> searchStudents(@RequestParam String email) {
        return studentRepository.findByEmailContainingIgnoreCase(email);
    }

    // ✅ 3. Get all job posts
    @GetMapping("/jobs")
    public List<JobPost> getAllJobs() {
        return jobPostRepository.findAll();
    }

    // ✅ 4. Get all applications
    @GetMapping("/applications")
    public List<Application> getAllApplications() {
        return applicationRepository.findAll();
    }

    // ✅ 5. Get applicants for a specific job
    @GetMapping("/applications/job/{jobPostId}")
    public List<Application> getApplicantsForJob(@PathVariable int jobPostId) {
        return applicationRepository.findByJobPostId(jobPostId);
    }

    // ✅ 6. Approve or reject an application
    @PutMapping("/applications/{applicationId}")
    public String updateApplicationStatus(
            @PathVariable int applicationId,
            @RequestParam String status // values: "Approved" or "Rejected"
    ) {
        Application application = applicationRepository.findById(applicationId).orElse(null);
        if (application != null) {
            application.setStatus(status);
            applicationRepository.save(application);
            return "Status updated to: " + status;
        }
        return "Application not found!";
    }

    // ✅ 7. Dashboard counts summary
    @GetMapping("/dashboard/summary")
    public DashboardSummary getSummary() {
        long totalStudents = studentRepository.count();
        long totalJobs = jobPostRepository.count();
        long totalApplications = applicationRepository.count();
        return new DashboardSummary(totalStudents, totalJobs, totalApplications);
    }

    // ✅ 8. Optional: Get student details for a job (applicant names)
    @GetMapping("/applicants/{jobPostId}")
    public List<Student> getApplicants(@PathVariable int jobPostId) {
        return applicationRepository.findByJobPostId(jobPostId)
                .stream()
                .map(app -> studentRepository.findById(app.getStudentId()).orElse(null))
                .filter(student -> student != null)
                .toList();
    }

    // ✅ DashboardSummary Class
    static class DashboardSummary {
        public long students;
        public long jobs;
        public long applications;

        public DashboardSummary(long students, long jobs, long applications) {
            this.students = students;
            this.jobs = jobs;
            this.applications = applications;
        }
    }
}
