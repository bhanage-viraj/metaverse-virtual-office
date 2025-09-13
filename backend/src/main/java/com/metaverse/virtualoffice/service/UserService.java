package com.metaverse.virtualoffice.service;

import com.metaverse.virtualoffice.model.User;
import com.metaverse.virtualoffice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User createUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Optional<User> findById(String id) {
        return userRepository.findById(id);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<User> findAllActiveUsers() {
        return userRepository.findByIsActiveTrue();
    }

    public List<User> findUsersByStatus(String status) {
        return userRepository.findByStatus(status);
    }

    public List<User> findUsersInRoom(String roomId) {
        return userRepository.findByCurrentRoom(roomId);
    }

    public User updateUser(String id, User userDetails) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            
            if (userDetails.getFirstName() != null) {
                user.setFirstName(userDetails.getFirstName());
            }
            if (userDetails.getLastName() != null) {
                user.setLastName(userDetails.getLastName());
            }
            if (userDetails.getAvatarUrl() != null) {
                user.setAvatarUrl(userDetails.getAvatarUrl());
            }
            if (userDetails.getSettings() != null) {
                user.setSettings(userDetails.getSettings());
            }
            
            return userRepository.save(user);
        }
        throw new RuntimeException("User not found with id: " + id);
    }

    public User updateUserStatus(String id, String status) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setStatus(status);
            return userRepository.save(user);
        }
        throw new RuntimeException("User not found with id: " + id);
    }

    public User updateUserPosition(String id, User.Position position) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setPosition(position);
            return userRepository.save(user);
        }
        throw new RuntimeException("User not found with id: " + id);
    }

    public User joinRoom(String userId, String roomId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setCurrentRoom(roomId);
            user.setStatus("online");
            return userRepository.save(user);
        }
        throw new RuntimeException("User not found with id: " + userId);
    }

    public User leaveRoom(String userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setCurrentRoom(null);
            user.setPosition(null);
            return userRepository.save(user);
        }
        throw new RuntimeException("User not found with id: " + userId);
    }

    public List<User> searchUsers(String searchTerm) {
        return userRepository.searchUsers(searchTerm);
    }

    public void deleteUser(String id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new RuntimeException("User not found with id: " + id);
        }
    }

    public boolean validatePassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
