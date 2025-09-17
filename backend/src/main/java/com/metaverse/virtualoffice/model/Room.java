package com.metaverse.virtualoffice.model;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Document(collection = "rooms")
public class Room extends BaseEntity {

    @Indexed(unique = true)
    private String code;

    private String name;
    private String createdBy;
    private Integer capacity = 16;
    private Map<String, Object> metadata = new HashMap<>();

    // optional ephemeral token you can use for room-scoped auth
    private String roomToken;
    private Instant expiresAt;

    public Room() {}

    public Room(String code, String name, String createdBy, Integer capacity) {
        this.code = code;
        this.name = name;
        this.createdBy = createdBy;
        this.capacity = capacity == null ? 16 : capacity;
    }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }
    public Integer getCapacity() { return capacity; }
    public void setCapacity(Integer capacity) { this.capacity = capacity; }
    public Map<String, Object> getMetadata() { return metadata; }
    public void setMetadata(Map<String, Object> metadata) { this.metadata = metadata; }
    public String getRoomToken() { return roomToken; }
    public void setRoomToken(String roomToken) { this.roomToken = roomToken; }
    public Instant getExpiresAt() { return expiresAt; }
    public void setExpiresAt(Instant expiresAt) { this.expiresAt = expiresAt; }
}
