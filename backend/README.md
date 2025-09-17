### FILE: pom.xml




---

### FILE: src/main/resources/application.yml



---

### FILE: src/main/java/com/metaverse/virtualoffice/VirtualOfficeBackendApplication.java


---

### FILE: src/main/java/com/metaverse/virtualoffice/config/CorsConfig.java


---

### FILE: src/main/java/com/metaverse/virtualoffice/config/MongoConfig.java



---

### FILE: src/main/java/com/metaverse/virtualoffice/model/BaseEntity.java


---

### FILE: src/main/java/com/metaverse/virtualoffice/model/User.java



---

### FILE: src/main/java/com/metaverse/virtualoffice/model/Room.java


---

### FILE: src/main/java/com/metaverse/virtualoffice/repository/UserRepository.java



---

### FILE: src/main/java/com/metaverse/virtualoffice/repository/RoomRepository.java



---

### FILE: src/main/java/com/metaverse/virtualoffice/service/UserService.java



---

### FILE: src/main/java/com/metaverse/virtualoffice/util/CodeGenerator.java



---

### FILE: src/main/java/com/metaverse/virtualoffice/controller/UserController.java


---

### FILE: src/main/java/com/metaverse/virtualoffice/controller/RoomController.java

---

### FILE: src/test/java/com/metaverse/virtualoffice/VirtualOfficeBackendApplicationTests.java


---

### NOTES

- The provided code is a complete minimal Spring Boot + MongoDB backend that covers user guest creation and basic room creation and lookup. It purposefully avoids production JWT complexity and WebSocket signalling to keep the initial code straightforward and runnable locally against a MongoDB instance at `mongodb://localhost:27017/virtualoffice`.

- Next steps I can produce (pick any):
  - Add WebSocket (TextWebSocketHandler) for join/move/signal routing (no deployment required).
  - Add JWT-based token issuance & validation.
  - Add integration tests and a Docker Compose for local Mongo replica set (for Change Streams).

If you want any of those, tell me which one and I will add it to this same backend repository.
