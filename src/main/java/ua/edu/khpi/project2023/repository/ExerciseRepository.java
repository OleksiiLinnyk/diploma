package ua.edu.khpi.project2023.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.edu.khpi.project2023.model.Exercise;
import ua.edu.khpi.project2023.model.PassedExercise;

import java.util.List;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

    List<Exercise> findAllByTestId(Long testId);

    @Modifying
    @Query(value = "UPDATE user_has_exercise SET checked = :checked, given_answer = :answer, taken_points = :points", nativeQuery = true)
    void passExercise(@Param("checked") boolean isPassedSuccessfully, @Param("answer") String answer, @Param("points") Integer points);

    @Modifying
    @Query(value = "SELECT t.user_id, t.id, e.*, uhe.given_answer FROM test t " +
            "JOIN exercise e ON t.id = e.test_id " +
            "JOIN user_has_exercise uhe ON e.id = uhe.exercise_id WHERE t.user_id = :userId AND uhe.checked = false",
            nativeQuery = true)
    List<PassedExercise> getUnCheckedExercisesByUserId(@Param("userId") Long userId);
}
