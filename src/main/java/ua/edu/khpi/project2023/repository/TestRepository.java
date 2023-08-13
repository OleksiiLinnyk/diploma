package ua.edu.khpi.project2023.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.edu.khpi.project2023.model.Test;

import java.util.List;

public interface TestRepository extends JpaRepository<Test, Long> {

    @Modifying
    @Query(value = "UPDATE test SET subject = :subject, theme = :theme WHERE id = :testId", nativeQuery = true)
    void updateTest(@Param("subject") String subject, @Param("theme") String theme, @Param("testId") Long testId);

    @Modifying
    @Query(value = "UPDATE test SET enable = :enable WHERE id = :testId", nativeQuery = true)
    void enableTest(@Param("enable") boolean isEnabled, @Param("testId") Long testId);

    @Query(value = "SELECT * FROM test WHERE userId = :userId", nativeQuery = true)
    List<Test> getMyTests(@Param("userId") Long userId);

    @Query(value = "SELECT * FROM test as t JOIN group_has_test as g ON t.id = g.test_id WHERE g.group_id = :groupId", nativeQuery = true)
    List<Test> getMyStudentTests(@Param("groupId") Long groupId);

    @Modifying
    @Query(value = "INSERT INTO group_has_test(group_id, test_id) VALUES(:groupId, :testId)", nativeQuery = true)
    void assignTestToGroupId(@Param("testId") Long testId, @Param("groupId") Long groupId);
}
