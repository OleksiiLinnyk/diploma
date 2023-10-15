package ua.edu.khpi.project2023.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.edu.khpi.project2023.exception.*;
import ua.edu.khpi.project2023.model.ERole;
import ua.edu.khpi.project2023.model.Group;
import ua.edu.khpi.project2023.model.Role;
import ua.edu.khpi.project2023.model.User;
import ua.edu.khpi.project2023.model.request.UpdateUserRequest;
import ua.edu.khpi.project2023.payload.request.UserRegisterRequest;
import ua.edu.khpi.project2023.repository.UserRepository;
import ua.edu.khpi.project2023.security.model.AuthUser;
import ua.edu.khpi.project2023.security.util.SecurityUtil;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

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

    public User getUserById(Long id) {
        return userRepository.getReferenceById(id);
    }

    public List<User> getAllUsers() {
        log.debug("Get all users");
        AuthUser authUser = SecurityUtil.getAuthUser();
        return userRepository.getAllUsers(authUser.getEmail());
    }

    public List<User> getAllStudentsInGroup(String groupName) {
        log.debug("Get all students in group {}", groupName);
        if (groupName == null) {
            return userRepository.findUsersWithoutGroup();
        }
        return userRepository.findAllStudentsInGroup(groupName);
    }

    public List<User> getAllStudentsInGroupByGroupId(Long groupId) {
        log.debug("Get all students in group {}", groupId);
        if (groupId != null) {
            return userRepository.findAllStudentsByGroupId(groupId);
        }
        throw new BadRequestException("Group id cannot be null");
    }

    public User addUser(UserRegisterRequest userRegisterRequest) {
        log.debug("Register user for user - {}", userRegisterRequest);
        if (!userRepository.existsByEmail(userRegisterRequest.getEmail())) {
            Role role = roleService.getRoleByName(userRegisterRequest.getRole());
            Optional<Group> group = userRegisterRequest.getGroupName() != null
                    ? groupService.getGroupByName(userRegisterRequest.getGroupName())
                    : groupService.getGroupByName("staff");
            if (userRegisterRequest.getRole().equals(ERole.ROLE_STUDENT) && !group.isPresent()) {
                throw new BadRequestException("User cannot be without group");
            }
            User user = User.builder()
                    .role(role)
                    .name(userRegisterRequest.getName())
                    .email(userRegisterRequest.getEmail())
                    .password(passwordEncoder.encode(userRegisterRequest.getPassword()))
                    .subject(userRegisterRequest.getSubject())
                    .group(group.orElse(null))
                    .build();
            return userRepository.save(user);
        }
        throw new UserAlreadyExistException();
    }

    @Transactional
    public User updateUser(UpdateUserRequest updateUserRequest) {
        AuthUser authUser = SecurityUtil.getAuthUser();
        User user = userRepository.findById(authUser.getId()).orElseThrow(() -> new NotFoundException("User not found"));
        if (StringUtils.isNotBlank(updateUserRequest.getEmail())) {
            if (userRepository.existsByEmailAndId(updateUserRequest.getEmail(), authUser.getId()) == 0) {
                userRepository.updateUserEmail(updateUserRequest.getEmail(), user.getId());
            } else {
                throw new EmailAlreadyExistException();
            }
        }
        if (StringUtils.isNotBlank(updateUserRequest.getPassword())) {
            if (updateUserRequest.getPassword().equals(updateUserRequest.getConfirmPassword())) {
                String updatedPassword = passwordEncoder.encode(updateUserRequest.getPassword());
                userRepository.updateUserPassword(updatedPassword, user.getId());
            }else {
                throw new PasswordNotTheSameException();
            }
        }
        if (StringUtils.isNotBlank(updateUserRequest.getName())) {
            userRepository.updateUsername(updateUserRequest.getName(), user.getId());
        }
        return userRepository.findById(user.getId()).orElseThrow(() -> new NotFoundException("User not found"));
    }
}
