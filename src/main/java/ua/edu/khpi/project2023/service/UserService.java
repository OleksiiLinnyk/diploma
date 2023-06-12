package ua.edu.khpi.project2023.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.edu.khpi.project2023.exception.BadRequestException;
import ua.edu.khpi.project2023.exception.NotFoundException;
import ua.edu.khpi.project2023.exception.UserAlreadyExistException;
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
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleService roleService;

    @Autowired
    private GroupService groupService;

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
        log.debug("Register user for user - {}", userRegisterRequest);
        if (!userRepository.existsByEmail(userRegisterRequest.getEmail())) {
            String password = UUID.randomUUID().toString();
            Role role = roleService.getRoleByName(userRegisterRequest.getRole());
            Optional<Group> group = userRegisterRequest.getGroupName() != null
                    ? groupService.getGroupByName(userRegisterRequest.getGroupName())
                    : Optional.empty();
            if (userRegisterRequest.getRole().equals(ERole.ROLE_STUDENT) && !group.isPresent()) {
                throw new BadRequestException("User cannot be without group");
            }
            User user = User.builder()
                    .role(role)
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
        throw new UserAlreadyExistException();
    }
}
