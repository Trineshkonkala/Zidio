package com.example.zidio.security;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtFilter implements Filter {

    @Autowired
    private JwtService jwtService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String uri = httpRequest.getRequestURI();
        String authHeader = httpRequest.getHeader("Authorization");

        // Allow login endpoint to bypass token
        if (uri.contains("/auth/login")) {
            chain.doFilter(request, response);
            return;
        }

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            String email = jwtService.extractUsername(token);

            if (jwtService.validateToken(token, email)) {
                chain.doFilter(request, response);
                return;
            }
        }

        httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized - Invalid or missing token");
    }
}
