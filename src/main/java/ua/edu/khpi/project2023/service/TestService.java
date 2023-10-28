package ua.edu.khpi.project2023.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ua.edu.khpi.project2023.exception.NotFoundException;
import ua.edu.khpi.project2023.model.Exercise;
import ua.edu.khpi.project2023.model.PassedExerciseDTO;
import ua.edu.khpi.project2023.model.Test;
import ua.edu.khpi.project2023.model.User;
import ua.edu.khpi.project2023.model.request.TestUpsertRequest;
import ua.edu.khpi.project2023.repository.ExerciseRepository;
import ua.edu.khpi.project2023.repository.TestRepository;
import ua.edu.khpi.project2023.repository.UserRepository;
import ua.edu.khpi.project2023.security.model.AuthUser;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
}
