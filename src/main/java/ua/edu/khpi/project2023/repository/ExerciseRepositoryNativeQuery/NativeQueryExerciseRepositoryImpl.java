package ua.edu.khpi.project2023.repository.ExerciseRepositoryNativeQuery;

import ua.edu.khpi.project2023.model.PassedExerciseDTO;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class NativeQueryExerciseRepositoryImpl implements NativeQueryExerciseRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<PassedExerciseDTO> findAllByStudentAndTestId(Long studentId, Long testId) {
        String sql = String.format("SELECT e.*, uhe.* FROM exercise e JOIN user_has_exercise uhe " +
                "ON e.id = uhe.exercise_id WHERE uhe.user_id = %d AND e.test_id = %d", studentId, testId);

        Query query = entityManager.createNativeQuery(sql);

        List<Object[]> resultSet = query.getResultList();
        List<PassedExerciseDTO> results = new ArrayList<>();

        for (Object[] row : resultSet) {
            PassedExerciseDTO dto = new PassedExerciseDTO();
            dto.setId(((Number) row[0]).longValue());
            dto.setTestId(((Number) row[1]).longValue());
            dto.setQuestion((String) row[2]);
            dto.setAnswer((String) row[3]);
            dto.setUserId(((Number) row[4]).longValue());
            dto.setExercise_id(((Number) row[5]).longValue());
            dto.setGivenAnswer(row[6] != null ? (String) row[6] : null);
            dto.setChecked(row[7] != null ? (Boolean) row[7] : null);
            dto.setTakenPoints(row[8] != null ? ((Number) row[8]).intValue() : null);

            results.add(dto);
        }
        return results;
    }
}
