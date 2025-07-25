package com.example.zidio.security;

import com.example.zidio.Student;
import com.example.zidio.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private StudentRepository studentRepository;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest authRequest) {
        String email = authRequest.getUsername();
        String password = authRequest.getPassword();

        Student student = studentRepository.findByEmail(email);

        if (student != null && student.getPassword().equals(password)) {
            String token = jwtService.generateToken(email);
            return new AuthResponse("Login successful", token);
        } else {
            return new AuthResponse("Invalid credentials", null);
        }
    }

    static class AuthResponse {
        private String message;
        private String token;

        public AuthResponse(String message, String token) {
            this.message = message;
            this.token = token;
        }

        public String getMessage() {
            return message;
        }

        public String getToken() {
            return token;
        }
    }
}
