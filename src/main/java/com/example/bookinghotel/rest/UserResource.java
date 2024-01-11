package com.example.bookinghotel.rest;

import com.example.bookinghotel.model.request.UpdateUserRequest;
import com.example.bookinghotel.model.request.UpsertUserRequest;
import com.example.bookinghotel.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/users")
public class UserResource {
    private final UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }
    @GetMapping
    public ResponseEntity<?> getAllUser(@RequestParam(required = false, defaultValue = "1") Integer page,
                                        @RequestParam(required = false, defaultValue = "10") Integer limit) {
        return ResponseEntity.ok(userService.getAllUser(page, limit));
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }
    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody UpsertUserRequest request) {
        return new ResponseEntity<>(userService.createUser(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@RequestBody UpdateUserRequest request, @PathVariable Integer id) {
        return ResponseEntity.ok(userService.updateUser(request,id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
