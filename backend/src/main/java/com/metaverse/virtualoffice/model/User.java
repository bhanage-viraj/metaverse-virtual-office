package com.metaverse.virtualoffice.model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "users")
public class User extends BaseEntity {

    private String username;
    private String avatarUrl;
    private Instant lastSeen;

    public User() {}

    public User(String username, String avatarUrl) {
        this.username = username;
        this.avatarUrl = avatarUrl;
        this.lastSeen = Instant.now();
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getAvatarUrl() { return avatarUrl; }
    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }
    public Instant getLastSeen() { return lastSeen; }
    public void setLastSeen(Instant lastSeen) { this.lastSeen = lastSeen; }
}