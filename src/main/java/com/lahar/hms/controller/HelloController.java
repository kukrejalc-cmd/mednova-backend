package com.lahar.hms.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class HelloController {

    // Test endpoint (main check)
    @GetMapping("/hello")
    public String hello() {
        return "API is working!";
    }

    // Root API endpoint (optional but useful)
    @GetMapping("/")
    public String home() {
        return "MedNova Backend Running Successfully 🚀";
    }

    // Health check endpoint (recommended for deployment)
    @GetMapping("/health")
    public String health() {
        return "Backend is UP ✔";
    }
}