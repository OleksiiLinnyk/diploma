package ua.edu.khpi.project2023.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.edu.khpi.project2023.model.Group;

import java.util.List;
import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Long> {

    Optional<Group> findByName(String name);

    @Query(value = "SELECT * FROM student_group as g JOIN group_has_test as gt ON g.id = gt.group_id WHERE gt.test_id = :testId", nativeQuery = true)
    List<Group> findByTestId(@Param("testId") Long testId);

    @Modifying
    @Query(value = "UPDATE student_group SET name = :groupName WHERE id = :groupId", nativeQuery = true)
    void updateGroup(@Param("groupName") String groupName, @Param("groupId") Long groupId);
}
