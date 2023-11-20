package ua.edu.khpi.project2023.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ua.edu.khpi.project2023.exception.NotFoundException;
import ua.edu.khpi.project2023.model.Exercise;
import ua.edu.khpi.project2023.model.GroupTestDTO;
import ua.edu.khpi.project2023.model.PassedExerciseDTO;
import ua.edu.khpi.project2023.model.Test;
import ua.edu.khpi.project2023.model.User;
import ua.edu.khpi.project2023.model.request.TestUpsertRequest;
import ua.edu.khpi.project2023.model.response.GroupsProgressResponse;
import ua.edu.khpi.project2023.model.response.UserProgressResponse;
import ua.edu.khpi.project2023.repository.ExerciseRepository;
import ua.edu.khpi.project2023.repository.TestRepository;
import ua.edu.khpi.project2023.repository.UserRepository;
import ua.edu.khpi.project2023.security.model.AuthUser;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static ua.edu.khpi.project2023.exercise.util.ExerciseJsonUtil.jsonToExercise;

@Slf4j
@Service
public class TestService {

    @Autowired
    TestRepository testRepository;
    @Autowired
    UserService userService;
    @Autowired
    ExerciseRepository exerciseRepository;
    @Autowired
    UserRepository userRepository;

    public Test getTestById(Long id) {
        return testRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Test by id %d was not found", id)));
    }

    public Test createTest(TestUpsertRequest request) {
        AuthUser authUser = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return testRepository.save(new Test(request.getSubject(), request.getTheme(), new User(authUser.getId(), authUser.getName(),
                authUser.getEmail(), authUser.getPassword(), authUser.getSubject())));
    }

    @Transactional
    public Test updateTest(TestUpsertRequest request, Long testId) {
        Test test = getTestById(testId);
        testRepository.updateTest(request.getSubject(), request.getTheme(), testId);
        return getTestById(testId);
    }

    @Transactional
    public Test enableTest(boolean isEnabled, Long testId) {
        testRepository.enableTest(isEnabled, testId);
        return getTestById(testId);
    }

    @Transactional
    public void assignTestToGroup(Long testId, Long groupId) {
        List<User> users = userService.getAllStudentsInGroupByGroupId(groupId);
        List<Exercise> exercises = exerciseRepository.findAllByTestId(testId);
        testRepository.assignTestToGroupId(testId, groupId);
        for (User user : users) {
            for (Exercise exercise : exercises) {
                userRepository.assignUserToExercises(user.getId(), exercise.getId());
            }
        }
    }

    @Transactional
    public void deleteTest(Long testId) {
        getTestById(testId);
        testRepository.deleteById(testId);
    }

    public List<Test> getAllTests() {
        return testRepository.findAll();
    }

    public List<Test> getMyTests() {
        AuthUser authUser = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return testRepository.getMyTests(authUser.getId());
    }
    public List<Test> getMyStudentTest(String status) {
        AuthUser authUser = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Test> tests =  testRepository.getMyStudentTests(authUser.getGroupId());
        if (Objects.nonNull(status)) {
            tests = filterTestsByStatus(tests, authUser.getId(), status);
        }
        return tests;
    }

    public List<GroupTestDTO> findGroupsByTestId(Long testId) {
        return testRepository.findGroupsByTestId(testId);
    }
    public List<GroupsProgressResponse> getGroupsProgressByTestId(Long testId) {
        List<GroupsProgressResponse> groupsProgressResponseList = new ArrayList<>();
        List<GroupTestDTO> groupTestDTOS = testRepository.findGroupsByTestId(testId);
        for (GroupTestDTO groupTestDTO : groupTestDTOS) {
            List<User> groupUsers = userRepository.findAllStudentsByGroupId(groupTestDTO.getGroupId());
            int awaitCounter = 0;
            int toCheckCounter = 0;
            int doneCounter = 0;
            for (User u : groupUsers) {
                String status = resolveTestStatusForUser(testId, u.getId());
                if (status.equals("done")) doneCounter++;
                if (status.equals("review")) toCheckCounter++;
                if (status.equals("todo")) awaitCounter++;
            }
            GroupsProgressResponse groupsProgressResponse = GroupsProgressResponse.builder()
                    .groupId(groupTestDTO.getGroupId())
                    .groupName(groupTestDTO.getGroupName())
                    .totalUsers(groupUsers.size())
                    .awaiting(awaitCounter)
                    .toCheck(toCheckCounter)
                    .done(doneCounter)
                    .build();
            groupsProgressResponseList.add(groupsProgressResponse);
        }
        return groupsProgressResponseList;
    }

    public List<UserProgressResponse> getUserProgressResponseByTestAndGroupId(Long testId, Long groupId) {
        List<User> groupUsers = userRepository.findAllStudentsByGroupId(groupId);
        List<UserProgressResponse> usersProgress = new ArrayList<>();
        for (User user : groupUsers) {
            int takenPointCounter = 0;
            int totalPointsCounter = 0;
            List<PassedExerciseDTO> passedExerciseDTOList =
                    exerciseRepository.findAllByStudentAndTestId(user.getId(), testId);
            for (PassedExerciseDTO passedExerciseDTO : passedExerciseDTOList) {
                Integer takenPoints = passedExerciseDTO.getTakenPoints();
                if (takenPoints != null) {
                    takenPointCounter += takenPoints;
                }
                totalPointsCounter += jsonToExercise(passedExerciseDTO.getQuestion()).getPoints();
            }
            String status = resolveTestStatusForUser(testId, user.getId());

            usersProgress.add(UserProgressResponse.builder()
                    .id(user.getId())
                    .name(user.getName())
                    .status(status)
                    .takenPoints(takenPointCounter)
                    .totalPoints(totalPointsCounter)
                    .build());
        }
        return usersProgress;
    }

    private List<Test> filterTestsByStatus(List<Test> tests, Long userId, String status) {
        return tests.stream().filter(t -> filterPredicate(t.getId(), userId, status)).collect(Collectors.toList());
    }

    private boolean filterPredicate(Long testId, Long userId, String status) {
        List<PassedExerciseDTO> testExercises = exerciseRepository.findAllByStudentAndTestId(userId, testId);
        if (status.equals("todo")) {
            for (PassedExerciseDTO e : testExercises) {
                if (e.getGivenAnswer() == null) {
                    return true;
                }
            }
            return false;
        }
        if (status.equals("review")) {
            boolean result = false;
            for (PassedExerciseDTO e : testExercises) {
                if (e.getGivenAnswer() == null) {
                    return false;
                }
                if (e.getChecked() == null || !e.getChecked()) {
                    result = true;
                }
            }
            return result;
        }
        if (status.equals("done")) {
            for (PassedExerciseDTO e : testExercises) {
                if (e.getChecked() == null || !e.getChecked()) {
                    return false;
                }
            }
            return true;
        }
        return true;
    }

    private String resolveTestStatusForUser(Long testId, Long userId) {
        String status = "done";
        List<PassedExerciseDTO> testExercises = exerciseRepository.findAllByStudentAndTestId(userId, testId);
        for (PassedExerciseDTO dto : testExercises) {
            if (status.equals("done") && (dto.getChecked() == null || !dto.getChecked())) {
                status = "review";
            }
            if ((status.equals("done") || status.equals("review")) && dto.getGivenAnswer() == null) {
                status = "todo";
            }
        }
        return status;
    }
}
