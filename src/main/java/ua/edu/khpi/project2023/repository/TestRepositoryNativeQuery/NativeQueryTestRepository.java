package ua.edu.khpi.project2023.repository.TestRepositoryNativeQuery;

import ua.edu.khpi.project2023.model.GroupTestDTO;
import ua.edu.khpi.project2023.model.PassedExerciseDTO;

import java.util.List;

public interface NativeQueryTestRepository {
    List<GroupTestDTO> findGroupsByTestId(Long testId);
}
