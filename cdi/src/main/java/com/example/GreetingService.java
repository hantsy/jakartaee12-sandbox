package com.example;

import jakarta.enterprise.context.RequestScoped;

@RequestScoped
public class GreetingService {

    public String greet(String name) {
        return "Hello, " + name + "!";
    }
}
