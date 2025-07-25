package com.example.zidio;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    Student findByEmail(String email); // ✅ Added for JWT AuthController
    Student findByEmailAndPassword(String email, String password); // Optional
    List<Student> findByEmailContainingIgnoreCase(String email);
}
