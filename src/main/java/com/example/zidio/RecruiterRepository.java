package com.example.zidio;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RecruiterRepository extends JpaRepository<Recruiter, Integer> {
    Recruiter findByEmailAndPassword(String email, String password);
}
