package ua.edu.khpi.project2023.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.edu.khpi.project2023.model.User;
import ua.edu.khpi.project2023.model.request.UpdateUserRequest;
import ua.edu.khpi.project2023.service.UserService;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/students/group")
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    ResponseEntity<List<User>> getStudentsByGroup(@RequestParam(value = "groupName", required = false) String groupName) {
        return ResponseEntity.ok(userService.getAllStudentsInGroup(groupName));
    }

    @PutMapping
    @PreAuthorize("hasRole('STUDENT') or hasRole('TEACHER') or hasRole('ADMIN')")
    ResponseEntity<User> updateUser(@Valid @RequestBody UpdateUserRequest updateUserRequest) {
        return ResponseEntity.ok(userService.updateUser(updateUserRequest));
    }
}
