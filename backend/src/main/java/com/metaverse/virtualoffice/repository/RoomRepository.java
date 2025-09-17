package com.metaverse.virtualoffice.repository;

import com.metaverse.virtualoffice.model.Room;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomRepository extends MongoRepository<Room, String> {
    Optional<Room> findByCode(String code);
    boolean existsByCode(String code);
}