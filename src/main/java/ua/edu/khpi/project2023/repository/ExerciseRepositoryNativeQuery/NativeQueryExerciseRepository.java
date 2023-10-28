package ua.edu.khpi.project2023.repository.ExerciseRepositoryNativeQuery;

import org.springframework.data.repository.query.Param;
import ua.edu.khpi.project2023.model.PassedExerciseDTO;

import java.util.ArrayList;
import java.util.List;

public interface NativeQueryExerciseRepository {
    List<PassedExerciseDTO> findAllByStudentAndTestId(Long studentId, Long testId);
}
