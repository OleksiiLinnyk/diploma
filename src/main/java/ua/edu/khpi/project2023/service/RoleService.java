package ua.edu.khpi.project2023.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.khpi.project2023.exception.NotFoundException;
import ua.edu.khpi.project2023.model.ERole;
import ua.edu.khpi.project2023.model.Role;
import ua.edu.khpi.project2023.repository.RoleRepository;

import java.util.List;

@Slf4j
@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Role getRoleByName(ERole eRole) {
        log.debug("Start looking for role with name {}", eRole);
        return roleRepository.findByName(eRole)
                .orElseThrow(() -> new NotFoundException(String.format("Role does not exist %s", eRole)));
    }

    //Roles for admin except ADMIN role
    public List<Role> getAllRoles() {
        log.debug("Start getting all roles");
        return roleRepository.getAllRoles();
    }
}
