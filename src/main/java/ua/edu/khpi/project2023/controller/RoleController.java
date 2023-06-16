package ua.edu.khpi.project2023.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.edu.khpi.project2023.model.ERole;
import ua.edu.khpi.project2023.model.Role;
import ua.edu.khpi.project2023.service.RoleService;

import java.util.List;

@RestController
@RequestMapping("/api/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<List<Role>> getAllRoles() {
        return ResponseEntity.ok(roleService.getAllRoles());
    }

    @GetMapping("/name")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<Role> getRoleByName(@RequestParam String roleName) {
        return ResponseEntity.ok(roleService.getRoleByName(ERole.findRole(roleName)));
    }
}
