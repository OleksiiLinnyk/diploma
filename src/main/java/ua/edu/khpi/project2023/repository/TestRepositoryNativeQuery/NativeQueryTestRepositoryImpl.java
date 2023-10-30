package ua.edu.khpi.project2023.repository.TestRepositoryNativeQuery;

import ua.edu.khpi.project2023.model.GroupTestDTO;
import ua.edu.khpi.project2023.model.PassedExerciseDTO;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class NativeQueryTestRepositoryImpl implements NativeQueryTestRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<GroupTestDTO> findGroupsByTestId(Long testId) {
        String sql = String.format("SELECT t.id test_id, t.subject, t.theme, g.id groupd_id, g.name FROM test t " +
                "JOIN group_has_test ght ON t.id = ght.test_id JOIN student_group g ON g.id = ght.group_id WHERE t.id = %d;",  testId);

        Query query = entityManager.createNativeQuery(sql);

        List<Object[]> resultSet = query.getResultList();
        List<GroupTestDTO> results = new ArrayList<>();

        for (Object[] row : resultSet) {
            GroupTestDTO dto = new GroupTestDTO();
            dto.setTestId(((Number) row[0]).longValue());
            dto.setSubject((String) row[1]);
            dto.setTheme((String) row[2]);
            dto.setGroupId(((Number) row[3]).longValue());
            dto.setGroupName((String) row[4]);

            results.add(dto);
        }
        return results;
    }
}
