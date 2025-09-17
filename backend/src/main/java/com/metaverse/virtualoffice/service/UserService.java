package com.metaverse.virtualoffice.service;

import com.metaverse.virtualoffice.model.User;
import com.metaverse.virtualoffice.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createGuest(String username, String avatarUrl) {
        // do a simple uniqueness check if you like
        Optional<User> existing = userRepository.findByUsername(username);
        if (existing.isPresent()) {
            // append suffix to keep unique
            username = username + "_" + System.currentTimeMillis()%1000;
        }
        User u = new User(username, avatarUrl);
        return userRepository.save(u);
    }

    public Optional<User> findById(String id) {
        return userRepository.findById(id);
    }
}