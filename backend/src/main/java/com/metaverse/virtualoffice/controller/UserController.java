
package com.metaverse.virtualoffice.controller;

import com.metaverse.virtualoffice.model.User;
import com.metaverse.virtualoffice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@Validated
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public static class GuestRequest {
        @NotBlank
        @Size(min = 2, max = 32)
        public String username;
        public String avatarId;
    }

    @PostMapping("/guest")
    public ResponseEntity<?> createGuest(@RequestBody GuestRequest req) {
        // basic sanitization
        String username = req.username.trim();
        String avatar = req.avatarId == null ? null : req.avatarId.trim();
        User u = userService.createGuest(username, avatar);

        Map<String, Object> data = new HashMap<>();
        data.put("userId", u.getId());
        data.put("username", u.getUsername());
        data.put("avatarUrl", u.getAvatarUrl());
        // token generation: for now a naive room-less token
        data.put("token", "guest-" + u.getId());
        data.put("expiresAt", null);

        return ResponseEntity.created(URI.create("/api/users/" + u.getId()))
                .body(Map.of("status", "ok", "data", data));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUser(@PathVariable String id) {
        return userService.findById(id)
                .map(u -> ResponseEntity.ok(Map.of("status", "ok", "data", u)))
                .orElse(ResponseEntity.status(404).body(Map.of("status", "error", "message", "User not found")));
    }
}