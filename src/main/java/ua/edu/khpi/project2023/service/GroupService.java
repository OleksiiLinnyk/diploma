package ua.edu.khpi.project2023.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.khpi.project2023.exception.BadRequestException;
import ua.edu.khpi.project2023.exception.GroupNameAlreadyExistException;
import ua.edu.khpi.project2023.exception.NotFoundException;
import ua.edu.khpi.project2023.model.Group;
import ua.edu.khpi.project2023.model.request.UpdateGroupRequest;
import ua.edu.khpi.project2023.repository.GroupRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    public Optional<Group> getGroupByName(String name) {
        log.debug("Find group by name {}", name);
        return groupRepository.findByName(name);
    }

    public List<Group> getGroupByTestId(Long testId) {
        log.debug("Find group by test id {}", testId);
        return groupRepository.findByTestId(testId);
    }

    @Transactional
    public List<Group> getAvailableGroups(Long testId) {
        log.debug("Find group by test id {}", testId);
        List<Group> assignedGroups = groupRepository.findByTestId(testId);
        List<Group> allGroups = groupRepository.findAll();
        allGroups.removeAll(assignedGroups);
        allGroups.removeIf(it -> it.getName().equals("staff"));
        return allGroups;
    }

    public List<Group> getAllGroups() {
        log.debug("Find all groups");
        return groupRepository.findAll();
    }

    @Transactional
    public void createGroup(String groupName) {
        Optional<Group> group = getGroupByName(groupName);
        if (!group.isPresent()) {
            groupRepository.save(new Group(groupName));
        } else {
            throw new GroupNameAlreadyExistException();
        }
    }

    @Transactional
    public Group updateGroup(UpdateGroupRequest request) {
        Optional<Group> group = getGroupByName(request.getNewGroupName());
        if (!group.isPresent()) {
            groupRepository.updateGroup(request.getNewGroupName(), request.getGroupId());
            return groupRepository.findById(request.getGroupId()).orElseThrow(() -> new NotFoundException("Group not found"));
        } else {
            throw new GroupNameAlreadyExistException();
        }
    }

    @Transactional
    public void deleteGroup(Long groupId) {
        groupRepository.findById(groupId)
                .ifPresentOrElse(value -> groupRepository.deleteById(groupId),
                        () -> {
                            throw new NotFoundException(String.format("Group with id %d not found", groupId));
                        });
    }
}
