package com.project.downloadmanager.util;

import com.project.downloadmanager.model.User;
import com.project.downloadmanager.repo.UserRepository;

import java.sql.SQLException;

public class UserService {

    public UserRepository userRepo;

    public User registration(User user) throws SQLException {
        return userRepo.save(user);
    }

    public User login(String email, String password) throws SQLException {
        User user = userRepo.findByEmail(email).orElseThrow;
        if (user.getPassword().equals(password)) {
            return user;
        } else {
            return null;
        }
    }
}
