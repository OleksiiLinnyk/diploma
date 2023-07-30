package ua.edu.khpi.project2023.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.khpi.project2023.exercise.model.IExercise;
import ua.edu.khpi.project2023.model.Exercise;
import ua.edu.khpi.project2023.repository.ExerciseRepository;

@Service
public class ExerciseService {

    @Autowired
    ExerciseRepository exerciseRepository;


/*    public void saveExercise( ) {
        Exercise exercise = Exercise.builder()
                .
                .build();
        exerciseRepository.save(exercise);
    }*/
}
