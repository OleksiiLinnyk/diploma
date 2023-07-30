package ua.edu.khpi.project2023.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.edu.khpi.project2023.model.request.ExerciseCreateRequest;

@RestController
@RequestMapping("/api/exercise")
public class ExerciseController {


    @PostMapping
    ResponseEntity<Void> assignExercise(@RequestBody ExerciseCreateRequest request) {
        System.out.println(request);
        return ResponseEntity.ok().build();
    }

}
