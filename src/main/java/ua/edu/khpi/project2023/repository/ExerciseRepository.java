package ua.edu.khpi.project2023.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.khpi.project2023.model.Exercise;
import ua.edu.khpi.project2023.model.PassedExercise;

import java.util.List;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

    @Modifying
    @Query(value = "insert into exercise (test_id, question, answer) values (:test_id, :question, :answer);", nativeQuery = true)
    void create(@Param("test_id") Long testId, @Param("question") String question, @Param("answer") String answer);

    @Query(value = "SELECT * FROM exercise WHERE test_id = :testId", nativeQuery = true)
    List<Exercise> findAllByTestId(@Param("testId") Long testId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE user_has_exercise SET checked = :checked, given_answer = :answer, taken_points = :points WHERE user_id = :userId AND exercise_id = :exerciseId", nativeQuery = true)
    void passExercise(@Param("checked") boolean isPassedSuccessfully, @Param("answer") String answer, @Param("points")
                        Integer points, @Param("userId") Long userId, @Param("exerciseId") Long exerciseId);

    // TODO: This shit doesn't work
    @Modifying
    @Query(value = "SELECT t.user_id, t.id, e.*, uhe.given_answer FROM test t " +
            "JOIN exercise e ON t.id = e.test_id " +
            "JOIN user_has_exercise uhe ON e.id = uhe.exercise_id WHERE t.user_id = :userId AND uhe.checked = false",
            nativeQuery = true)
    List<PassedExercise> getUnCheckedExercisesByUserId(@Param("userId") Long userId);
}
