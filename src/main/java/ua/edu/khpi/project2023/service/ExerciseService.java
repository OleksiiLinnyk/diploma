package ua.edu.khpi.project2023.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ua.edu.khpi.project2023.exception.NotFoundException;
import ua.edu.khpi.project2023.exercise.ExerciseType;
import ua.edu.khpi.project2023.exercise.model.OpenExercise;
import ua.edu.khpi.project2023.exercise.model.TestExercise;
import ua.edu.khpi.project2023.exercise.util.ExerciseJsonUtil;
import ua.edu.khpi.project2023.model.ERole;
import ua.edu.khpi.project2023.model.Exercise;
import ua.edu.khpi.project2023.model.Role;
import ua.edu.khpi.project2023.model.Test;
import ua.edu.khpi.project2023.model.request.ExerciseCreateRequest;
import ua.edu.khpi.project2023.model.response.ExerciseResponse;
import ua.edu.khpi.project2023.repository.ExerciseRepository;
import ua.edu.khpi.project2023.security.model.AuthUser;

import javax.transaction.Transactional;

import static ua.edu.khpi.project2023.exercise.util.ExerciseJsonUtil.jsonToExercise;

@Service
@Slf4j
public class ExerciseService {

    @Autowired
    ExerciseRepository exerciseRepository;
    @Autowired
    TestService testService;

    @Transactional
    public void saveExercise(ExerciseCreateRequest request) {
        Exercise.ExerciseBuilder builder = Exercise.builder()
                .answer(request.getAnswer());
        Test test = testService.getTestById(request.getTestId());
        if (request.getExerciseTypedRequest().getType() == ExerciseType.MULTIPLE_ANSWER_EXERCISE ||
                request.getExerciseTypedRequest().getType() == ExerciseType.SINGLE_ANSWER_EXERCISE) {
            TestExercise testExercise = new TestExercise(request.getExerciseTypedRequest().getType(),
                    request.getExerciseTypedRequest().getQuestion(),
                    request.getExerciseTypedRequest().getOptions(), null,
                    request.getExerciseTypedRequest().getPoints());
            builder.question(ExerciseJsonUtil.exerciseToJson(testExercise));
        } else if (request.getExerciseTypedRequest().getType() == ExerciseType.SHORT_OPEN_ANSWER_EXERCISE ||
                request.getExerciseTypedRequest().getType() == ExerciseType.LONG_OPEN_ANSWER_EXERCISE) {
            OpenExercise openExercise = new OpenExercise(request.getExerciseTypedRequest().getType(),
                    request.getExerciseTypedRequest().getQuestion(),
                    null,
                    request.getExerciseTypedRequest().getPoints());
            builder.question(ExerciseJsonUtil.exerciseToJson(openExercise));
        }
        builder.test(test);
        exerciseRepository.save(builder.build());
    }

    public ExerciseResponse getExerciseById(Long id) {
        return exerciseRepository.findById(id)
                .map(exercise -> {
                    AuthUser authUser = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                    String role = authUser.getAuthorities().stream()
                            .map(GrantedAuthority::getAuthority)
                            .findFirst().orElse(ERole.ROLE_STUDENT.name());
                    ExerciseResponse.ExerciseResponseBuilder builder = ExerciseResponse.builder()
                            .exercise(jsonToExercise(exercise.getQuestion()))
                            .id(exercise.getId())
                            .testId(exercise.getTest().getId());
                    if (ERole.ROLE_TEACHER.name().equals(role) || ERole.ROLE_ADMIN.name().equals(role)) {
                        builder.answer(exercise.getAnswer());
                    }
                    return builder.build();
                })
                .orElseThrow(() -> new NotFoundException(String.format("Exercise with id %d has not been found", id)));
    }
}
