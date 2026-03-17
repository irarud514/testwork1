package com.example.demo.controller;

import com.example.demo.repository.model.UserEntity;
import com.example.demo.repository.model.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserRepository userRepository;
    private Iterable<? extends UserEntity> entities;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserInfo>> getUsers() {
        List<UserEntity> users = userRepository.findAll();
        List<UserInfo> resultUsers = new ArrayList<>();
        for (UserEntity user : users) {
            resultUsers.add(new UserInfo(user.getFirstName(), user.getLastName()));
        }

        return ResponseEntity.ok(resultUsers);
    }

    @PostMapping("/users")
    public ResponseEntity<UserInfo> createUser(@RequestBody UserInfo userInfo) {
        userRepository.save(new UserEntity(userInfo.getFirstName(), userInfo.getLastName()));
        return ResponseEntity.ok(new UserInfo(userInfo.getFirstName(), userInfo.getLastName()));

    }

    @DeleteMapping("/users")
    public ResponseEntity<Void> deleteUser() {
        userRepository.deleteAll();
        return ResponseEntity.ok().build();
    }
    @PostMapping("/users")
    public ResponseEntity<Void> updateFirstNameUsers(){
        for (UserEntity user : entities){
            user.setFirstName("Irina");
        }
        userRepository.saveAll(entities);
        return ResponseEntity.ok().build();

    }


}
