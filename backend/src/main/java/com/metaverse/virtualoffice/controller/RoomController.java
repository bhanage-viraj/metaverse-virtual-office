package com.metaverse.virtualoffice.controller;

import com.metaverse.virtualoffice.model.Room;
import com.metaverse.virtualoffice.repository.RoomRepository;
import com.metaverse.util.CodeGenerator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.net.URI;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/rooms")
@Validated
public class RoomController {

    private final RoomRepository roomRepo;

    @Value("${app.room.code-length:6}")
    private int codeLength;

    @Value("${app.room.default-capacity:16}")
    private int defaultCapacity;

    public static class CreateRoomRequest {
        public String name;
        public String createdBy;
        @Min(2)
        @Max(200)
        public Integer capacity;
        public Map<String, Object> metadata;
        public Long expiresInSeconds;
    }

    public RoomController(RoomRepository roomRepo) {
        this.roomRepo = roomRepo;
    }

    @PostMapping
    public ResponseEntity<?> createRoom(@RequestBody CreateRoomRequest req) {
        String name = (req.name == null || req.name.isBlank()) ? "Room" : req.name.trim();
        Integer capacity = req.capacity == null ? defaultCapacity : req.capacity;
        String createdBy = req.createdBy == null ? "guest" : req.createdBy;

        // generate unique code (simple retry loop)
        String code;
        int attempts = 0;
        do {
            code = CodeGenerator.generate(codeLength);
            attempts++;
            if (attempts > 5) break;
        } while (roomRepo.existsByCode(code));

        Room r = new Room(code, name, createdBy, capacity);
        if (req.metadata != null) r.setMetadata(req.metadata);
        if (req.expiresInSeconds != null && req.expiresInSeconds > 0) {
            r.setExpiresAt(Instant.now().plusSeconds(req.expiresInSeconds));
        }
        // naive room token - in a real app use signed JWT
        r.setRoomToken("room-" + code + "-" + System.currentTimeMillis()%100000);

        Room saved = roomRepo.save(r);

        Map<String, Object> data = new HashMap<>();
        data.put("roomId", saved.getId());
        data.put("code", saved.getCode());
        data.put("name", saved.getName());
        data.put("createdAt", saved.getCreatedAt());
        data.put("expiresAt", saved.getExpiresAt());
        data.put("capacity", saved.getCapacity());
        data.put("roomToken", saved.getRoomToken());

        return ResponseEntity.created(URI.create("/api/rooms/" + saved.getCode()))
                .body(Map.of("status", "ok", "data", data));
    }

    @GetMapping("/{code}")
    public ResponseEntity<?> getRoom(@PathVariable String code) {
        Optional<Room> opt = roomRepo.findByCode(code.toUpperCase());
        if (opt.isEmpty()) return ResponseEntity.status(404).body(Map.of("status", "error", "message", "Room not found"));
        Room r = opt.get();
        Map<String, Object> data = new HashMap<>();
        data.put("roomId", r.getId());
        data.put("code", r.getCode());
        data.put("name", r.getName());
        data.put("createdBy", r.getCreatedBy());
        data.put("createdAt", r.getCreatedAt());
        data.put("capacity", r.getCapacity());
        data.put("metadata", r.getMetadata());
        data.put("expiresAt", r.getExpiresAt());
        return ResponseEntity.ok(Map.of("status", "ok", "data", data));
    }
}
