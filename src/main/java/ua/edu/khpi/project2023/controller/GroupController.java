package ua.edu.khpi.project2023.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.edu.khpi.project2023.exception.NotFoundException;
import ua.edu.khpi.project2023.model.Group;
import ua.edu.khpi.project2023.model.request.UpdateGroupRequest;
import ua.edu.khpi.project2023.service.GroupService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/group")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @GetMapping
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    ResponseEntity<List<Group>> getAllGroups() {
        return ResponseEntity.ok(groupService.getAllGroups());
    }

    @GetMapping("/test")
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    ResponseEntity<List<Group>> getGroupsByTestId(@RequestParam Long testId) {
        return ResponseEntity.ok(groupService.getGroupByTestId(testId));
    }

    @GetMapping("/name")
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    ResponseEntity<Group> getGroupByName(@RequestParam String groupName) {
        Group group = groupService.getGroupByName(groupName)
                .orElseThrow(() -> new NotFoundException(String.format("Group not found %s", groupName)));
        return ResponseEntity.ok(group);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<Void> createGroup(@RequestParam String groupName) {
        groupService.createGroup(groupName);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<Group> updateGroup(@Valid @RequestBody UpdateGroupRequest request) {
        return ResponseEntity.ok(groupService.updateGroup(request));
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<Void> deleteGroup(@RequestParam @NotNull Long groupId) {
        groupService.deleteGroup(groupId);
        return ResponseEntity.ok().build();
    }
}
