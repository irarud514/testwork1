package com.example.demo.controller;

import com.example.demo.repository.model.UserEntity;
import com.example.demo.repository.model.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:63342")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserInfo>> getUsers() {
        List<UserEntity> users = userRepository.findAll();
        List<UserInfo> result = new ArrayList<>();
        for (UserEntity user : users) {
            result.add(new UserInfo(
                    user.getId(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getAge()
            ));
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserInfo> getUserById(@PathVariable Long id) {
        UserEntity user = userRepository.findById(id).orElseThrow();
        return ResponseEntity.ok(new UserInfo(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getAge()
        ));
    }

    @PostMapping("/users")
    public ResponseEntity<UserInfo> createUser(@RequestBody UserInfo userInfo) {
        UserEntity user = new UserEntity();
        user.setFirstName(userInfo.getFirstName());
        user.setLastName(userInfo.getLastName());
        user.setAge(userInfo.getAge());
        userRepository.save(user);
        return ResponseEntity.ok(new UserInfo(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getAge()
        ));
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<UserInfo> updateUser(@PathVariable Long id, @RequestBody UserInfo userInfo) {
        UserEntity existing = userRepository.findById(id).orElseThrow();
        existing.setFirstName(userInfo.getFirstName());
        existing.setLastName(userInfo.getLastName());
        existing.setAge(userInfo.getAge());
        userRepository.save(existing);
        return ResponseEntity.ok(new UserInfo(
                existing.getId(),
                existing.getFirstName(),
                existing.getLastName(),
                existing.getAge()
        ));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id) {
        userRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/users")
    public ResponseEntity<Void> deleteAllUsers() {
        userRepository.deleteAll();
        return ResponseEntity.ok().build();
    }

    @PutMapping("/users/lastname")
    public ResponseEntity<Void> updateAllLastnames() {
        List<UserEntity> users = userRepository.findAll();
        for (UserEntity user : users) {
            user.setLastName("RUDNIKOVA");
        }
        userRepository.saveAll(users);
        return ResponseEntity.ok().build();
    }
}
