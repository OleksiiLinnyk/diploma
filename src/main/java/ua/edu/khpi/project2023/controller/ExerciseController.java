package ua.edu.khpi.project2023.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.edu.khpi.project2023.model.request.ExerciseCreateRequest;
import ua.edu.khpi.project2023.model.response.ExerciseResponse;
import ua.edu.khpi.project2023.service.ExerciseService;

@RestController
@RequestMapping("/api/exercise")
public class ExerciseController {

    @Autowired
    ExerciseService exerciseService;

    @PostMapping
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    ResponseEntity<Void> assignExercise(@RequestBody ExerciseCreateRequest request) {
        exerciseService.saveExercise(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    @PreAuthorize("hasRole('STUDENT') or hasRole('TEACHER') or hasRole('ADMIN')")
    ResponseEntity<ExerciseResponse> getExerciseById(@RequestParam(name = "exerciseId") Long exerciseId) {
        return ResponseEntity.ok(exerciseService.getExerciseById(exerciseId));
    }

}
