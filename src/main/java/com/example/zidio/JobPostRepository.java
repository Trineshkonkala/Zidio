package com.example.zidio;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface JobPostRepository extends JpaRepository<JobPost, Integer> {
    List<JobPost> findByRecruiterId(int recruiterId); // ✅ new method
}
