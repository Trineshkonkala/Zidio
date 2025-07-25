package com.example.zidio;

import jakarta.persistence.*;

@Entity
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int studentId;
    private int jobPostId;
    private String status = "Pending";  // Default status

    // Constructors
    public Application() {}

    public Application(int studentId, int jobPostId) {
        this.studentId = studentId;
        this.jobPostId = jobPostId;
        this.status = "Pending";
    }

    // Getters and Setters
    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public int getStudentId() { return studentId; }

    public void setStudentId(int studentId) { this.studentId = studentId; }

    public int getJobPostId() { return jobPostId; }

    public void setJobPostId(int jobPostId) { this.jobPostId = jobPostId; }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }
}
