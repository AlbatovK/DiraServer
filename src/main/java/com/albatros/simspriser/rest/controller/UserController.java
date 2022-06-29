package com.albatros.simspriser.rest.controller;

import com.albatros.simspriser.domain.DiraUser;
import com.albatros.simspriser.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RequestMapping("/user")
@RestController
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private final UserService service;

    @PostMapping(value = "/create", consumes = "application/json", produces = "application/json")
    public DiraUser createUser(@RequestBody DiraUser user) throws ExecutionException, InterruptedException {
        service.saveUser(user);
        return user;
    }

    @GetMapping("/find")
    public ResponseEntity<DiraUser> findById(@RequestParam("user_id") String user_id) throws ExecutionException, InterruptedException {
        for (DiraUser user: service.getUsers()) {
            if (user.getTokenId().equalsIgnoreCase(user_id)) {
                return ResponseEntity.ok(user);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/get/all")
    public List<DiraUser> getAll() throws ExecutionException, InterruptedException {
        return service.getUsers();
    }
}
