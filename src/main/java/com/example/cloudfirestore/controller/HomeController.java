package com.example.cloudfirestore.controller;

import com.example.cloudfirestore.model.User;
import com.example.cloudfirestore.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
public class HomeController {

    private final UserService userService;

    public HomeController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/addUser")
    public ResponseEntity<String> addUser(@RequestBody User user) throws ExecutionException, InterruptedException {
         String responseDetails = userService.createUser(user);
         return ResponseEntity.accepted().body(responseDetails);
    }

    @GetMapping("/getUser/{userId}")
    public ResponseEntity<List<User>> getUser(@PathVariable("userId") String userId) throws ExecutionException, InterruptedException {
        List<User> users = userService.getUser(userId);
        return ResponseEntity.ok().body(users);
    }

    @PutMapping("/updateUser")
    public ResponseEntity<String> updateUser(@RequestBody User user, @RequestParam("userId") String userId) throws ExecutionException, InterruptedException {
        String updateDetail = userService.updateUser(userId, user);
        return ResponseEntity.ok().body(updateDetail);
    }

    @DeleteMapping("/deleteUser")
    public ResponseEntity<String> deleteUser(@RequestParam("userId") String userId) throws ExecutionException, InterruptedException {
        String deleteDetail = userService.deleteUser(userId);
        return ResponseEntity.ok().body(deleteDetail);
    }
}
