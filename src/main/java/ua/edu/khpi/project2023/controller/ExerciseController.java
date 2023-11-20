package ua.edu.khpi.project2023.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.edu.khpi.project2023.model.PassedExerciseDTO;
import ua.edu.khpi.project2023.model.request.EstimateExerciseRequest;
import ua.edu.khpi.project2023.model.request.ExerciseCreateRequest;
import ua.edu.khpi.project2023.model.request.PassExerciseRequest;
import ua.edu.khpi.project2023.model.response.ExerciseResponse;
import ua.edu.khpi.project2023.model.response.PassExerciseResponse;
import ua.edu.khpi.project2023.model.response.UserPassedExerciseResponse;
import ua.edu.khpi.project2023.service.ExerciseService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/exercise")
public class ExerciseController {

    @Autowired
    ExerciseService exerciseService;

    @PostMapping
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    ResponseEntity<Void> createExercise(@RequestBody ExerciseCreateRequest request) {
        exerciseService.saveExercise(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    @PreAuthorize("hasRole('STUDENT') or hasRole('TEACHER') or hasRole('ADMIN')")
    ResponseEntity<ExerciseResponse> getExerciseById(@RequestParam(name = "exerciseId") Long exerciseId) {
        return ResponseEntity.ok(exerciseService.getExerciseById(exerciseId));
    }

    @DeleteMapping
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    ResponseEntity<Void> removeExercise(@RequestParam(name = "exerciseId") Long exerciseId) {
        exerciseService.removeExercise(exerciseId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/all/test/{testId}")
    @PreAuthorize("hasRole('STUDENT') or hasRole('TEACHER') or hasRole('ADMIN')")
    ResponseEntity<List<ExerciseResponse>> getAllByTestId(@PathVariable(name = "testId") Long testId) {
        return ResponseEntity.ok(exerciseService.getAllByTestId(testId));
    }

    @GetMapping("/all/pass/{testId}")
    @PreAuthorize("hasRole('STUDENT')")
    ResponseEntity<List<PassExerciseResponse>> getStudentExercisesByTestId(@PathVariable(name = "testId") Long testId) {
        return ResponseEntity.ok(exerciseService.getStudentsExercisesByTestId(testId));
    }

    @GetMapping("/all/review")
    @PreAuthorize("hasRole('TEACHER')")
    ResponseEntity<List<PassExerciseResponse>> getExercisesByStudentAndTestId(@RequestParam(name = "testId") Long testId,
                                                                           @RequestParam(name = "userId") Long userId) {
        return ResponseEntity.ok(exerciseService.getExercisesByUserAndTestId(userId, testId));
    }

    @PostMapping("/pass/exercise")
    @PreAuthorize("hasRole('STUDENT')")
    ResponseEntity<Void> passExercise(@RequestBody PassExerciseRequest request) {
        exerciseService.passExercise(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/estimate/exercise")
    @PreAuthorize("hasRole('TEACHER')")
    ResponseEntity<Void> estimateExercise(@RequestBody EstimateExerciseRequest request) {
        exerciseService.estimateExercise(request.getExerciseId(), request.getUserId(), request.getPoints());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/unchecked/exercise")
    @PreAuthorize("hasRole('TEACHER')")
    ResponseEntity<List<UserPassedExerciseResponse>> getUncheckedExercise() {
        return ResponseEntity.ok(exerciseService.getUncheckedExercises());
    }
}
