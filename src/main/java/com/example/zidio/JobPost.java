package com.example.zidio;

import jakarta.persistence.*;

@Entity
public class JobPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;
    private String description;
    private String location;
    private String company;
    private String salary;

    private int recruiterId;  // ✅ new field

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getCompany() { return company; }
    public void setCompany(String company) { this.company = company; }

    public String getSalary() { return salary; }
    public void setSalary(String salary) { this.salary = salary; }

    public int getRecruiterId() { return recruiterId; }
    public void setRecruiterId(int recruiterId) { this.recruiterId = recruiterId; }
}
