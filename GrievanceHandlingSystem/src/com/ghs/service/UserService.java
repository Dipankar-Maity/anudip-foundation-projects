package com.ghs.service;

import com.ghs.dao.UserDAO;
import com.ghs.model.User;

public class UserService {
    private UserDAO userDAO = new UserDAO();

    public String registerUser(User user) {
        // Business Rule: Ensure username is not empty
        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            return "Registration Failed: Username is required.";
        }
        
        int result = userDAO.registerUser(user);
        return (result > 0) ? "User registered successfully!" : "Registration failed.";
    }

    public User login(String username, String password) {
        // Business Rule: Basic validation before hitting DB
        if (username == null || password == null) {
            return null;
        }
        return userDAO.login(username, password);
    }
}