package ua.edu.khpi.project2023.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.khpi.project2023.model.Group;
import ua.edu.khpi.project2023.repository.GroupRepository;

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

    public List<Group> getAllGroups() {
        log.debug("Find all groups");
        return groupRepository.findAll();
    }
}
