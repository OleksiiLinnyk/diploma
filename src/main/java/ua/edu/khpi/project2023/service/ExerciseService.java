package ua.edu.khpi.project2023.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import ua.edu.khpi.project2023.exception.BadRequestException;
import ua.edu.khpi.project2023.exception.NotFoundException;
import ua.edu.khpi.project2023.exception.UnsupportedExerciseInspection;
import ua.edu.khpi.project2023.exercise.ExerciseType;
import ua.edu.khpi.project2023.exercise.inspector.ExerciseInspector;
import ua.edu.khpi.project2023.exercise.model.IExercise;
import ua.edu.khpi.project2023.exercise.model.OpenExercise;
import ua.edu.khpi.project2023.exercise.model.TestExercise;
import ua.edu.khpi.project2023.exercise.util.ExerciseJsonUtil;
import ua.edu.khpi.project2023.model.ERole;
import ua.edu.khpi.project2023.model.Exercise;
import ua.edu.khpi.project2023.model.PassedExerciseDTO;
import ua.edu.khpi.project2023.model.Test;
import ua.edu.khpi.project2023.model.request.ExerciseCreateRequest;
import ua.edu.khpi.project2023.model.request.PassExerciseRequest;
import ua.edu.khpi.project2023.model.response.ExerciseResponse;
import ua.edu.khpi.project2023.model.response.PassExerciseResponse;
import ua.edu.khpi.project2023.model.response.UserPassedExerciseResponse;
import ua.edu.khpi.project2023.repository.ExerciseRepository;
import ua.edu.khpi.project2023.security.model.AuthUser;
import ua.edu.khpi.project2023.security.util.SecurityUtil;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

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
        Exercise ex = builder.build();
        exerciseRepository.create(ex.getTest().getId(), ex.getQuestion(), ex.getAnswer());
    }

    public ExerciseResponse getExerciseById(Long id) {
        return exerciseRepository.findById(id)
                .map(exercise -> {
                    AuthUser authUser = SecurityUtil.getAuthUser();
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

    public List<ExerciseResponse> getAllByTestId(Long testId) {
        return exerciseRepository.findAllByTestId(testId).stream()
                .map(exercise -> {
                    AuthUser authUser = SecurityUtil.getAuthUser();
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
                }).collect(Collectors.toList());
    }

    public void passExercise(PassExerciseRequest request) {
        Exercise exercise = exerciseRepository.findById(request.getExerciseId())
                .orElseThrow(() -> new NotFoundException(String.format("Exercise with id %d has not been found", request.getExerciseId())));
        IExercise iExercise = ExerciseJsonUtil.jsonToExercise(exercise.getQuestion());
        ExerciseJsonUtil.writeGivenAnswerToExercise(iExercise, request.getAnswer());
        String rightAnswer = exercise.getAnswer();
        try {
            int point = ExerciseInspector.inspectExercise(iExercise, rightAnswer);
            exerciseRepository.passExercise(true, request.getAnswer(), point,
                    SecurityUtil.getAuthUser().getId(), exercise.getId());
        } catch (UnsupportedExerciseInspection e) {
            exerciseRepository.passExercise(false, request.getAnswer(), null,
                    SecurityUtil.getAuthUser().getId(), exercise.getId());
        }
    }

    public List<PassExerciseResponse> getExercisesByUserAndTestId(Long userId, Long testId) {
        List<PassedExerciseDTO> exerciseDTOS = exerciseRepository.findAllByStudentAndTestId(userId, testId);
        return exerciseDTOS.stream().map(dto -> PassExerciseResponse.builder()
                .id(dto.getExercise_id())
                .testId(dto.getTestId())
                .exercise(jsonToExercise(dto.getQuestion()))
                .checked(dto.getChecked())
                .givenAnswer(dto.getGivenAnswer())
                .takenPoints(dto.getTakenPoints())
                .build()
        ).collect(Collectors.toList());
    }

    public List<PassExerciseResponse> getStudentsExercisesByTestId(Long testId) {
        long studentId = SecurityUtil.getAuthUser().getId();
        return getExercisesByUserAndTestId(studentId, testId);
    }

    public List<UserPassedExerciseResponse> getUncheckedExercises() {
        AuthUser authUser = SecurityUtil.getAuthUser();
        List<PassedExerciseDTO> uncheckedExercises = exerciseRepository.getUnCheckedExercisesByUserId(authUser.getId());
        return uncheckedExercises.stream()
                .map(exercise -> {
                    return new UserPassedExerciseResponse(exercise.getId(), exercise.getTestId(), exercise.getAnswer(),
                            jsonToExercise(exercise.getQuestion()), exercise.getUserId(), exercise.getGivenAnswer());
                }).collect(Collectors.toList());
    }

    public void removeExercise(Long id) {
        if (id != null) {
            exerciseRepository.deleteById(id);
        } else {
            throw new BadRequestException("Id of exercise cannot be null");
        }
    }
}
