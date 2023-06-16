package ua.edu.khpi.project2023.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.edu.khpi.project2023.exception.NotFoundException;
import ua.edu.khpi.project2023.model.Group;
import ua.edu.khpi.project2023.service.GroupService;

import java.util.List;

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

    @GetMapping("/name")
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    ResponseEntity<Group> getGroupByName(@RequestParam String groupName) {
        Group group = groupService.getGroupByName(groupName)
                .orElseThrow(() -> new NotFoundException(String.format("Group not found %s", groupName)));
        return ResponseEntity.ok(group);
    }
}
