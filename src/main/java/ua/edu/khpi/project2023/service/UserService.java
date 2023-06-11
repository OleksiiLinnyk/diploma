package ua.edu.khpi.project2023.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.edu.khpi.project2023.model.ERole;
import ua.edu.khpi.project2023.model.Group;
import ua.edu.khpi.project2023.model.Role;
import ua.edu.khpi.project2023.model.User;
import ua.edu.khpi.project2023.payload.request.UserRegisterRequest;
import ua.edu.khpi.project2023.repository.RoleRepository;
import ua.edu.khpi.project2023.repository.UserRepository;
import ua.edu.khpi.project2023.security.model.AuthUser;
import ua.edu.khpi.project2023.security.util.SecurityUtil;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    GroupService groupService;

    public List<User> getAllUsers() {
        log.debug("Get all users");
        AuthUser authUser = SecurityUtil.getAuthUser();
        return userRepository.getAllUsers(authUser.getEmail());
    }

    public List<User> getAllStudentsInGroup(String groupName) {
        log.debug("Get all students in group {}", groupName);
        return userRepository.findAllStudentsInGroup(groupName);
    }

    public User addUser(UserRegisterRequest userRegisterRequest) {
        log.info("Register user for user - {}", userRegisterRequest);
        if (!userRepository.existsByEmail(userRegisterRequest.getEmail())) {
            String password = UUID.randomUUID().toString();
            Optional<Role> role = roleRepository.findByName(userRegisterRequest.getRole());
            Optional<Group> group = userRegisterRequest.getGroupName() != null
                    ? groupService.getGroupByName(userRegisterRequest.getGroupName())
                    : Optional.empty();
            if (userRegisterRequest.getRole().equals(ERole.ROLE_STUDENT) && !group.isPresent()) {
                throw new RuntimeException("User cannot be without group");
            }
            User user = User.builder()
                    .role(role.orElseThrow(() -> new RuntimeException("Role does not exist")))
                    .name(userRegisterRequest.getName())
                    .email(userRegisterRequest.getEmail())
                    .password(passwordEncoder.encode(password))
                    .subject(userRegisterRequest.getSubject())
                    .group(group.orElse(null))
                    .build();

            user = userRepository.save(user);
            user.setPassword(password);
            return user;
        }
        // TODO add custom exception
        throw new RuntimeException("User already registered");
    }
}
