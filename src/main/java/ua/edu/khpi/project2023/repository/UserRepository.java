package ua.edu.khpi.project2023.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.edu.khpi.project2023.model.User;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);

    @Query(value = "SELECT EXISTS(SELECT email FROM user WHERE id != :userId AND email = :email)", nativeQuery = true)
    Integer existsByEmailAndId(@Param("email") String email, @Param("userId") Long id);

    @Query(value = "SELECT * FROM user WHERE email != :email",
            nativeQuery = true)
    List<User> getAllUsers(@Param("email") String email);

    @Query(value = "SELECT * FROM user u \n" +
            "JOIN role r ON r.id = u.role_id \n" +
            "JOIN student_group g ON g.id = u.group_id WHERE g.name = :groupName ORDER BY u.name",
            nativeQuery = true)
    List<User> findAllStudentsInGroup(@Param("groupName") String groupName);

    List<User> findAllStudentsByGroupId(Long groupId);

    @Modifying
    @Query(value = "UPDATE user SET name = :name, email = :email, password = :password WHERE id = :userId", nativeQuery = true)
    @Transactional
    void updateUser(@Param("email") String email, @Param("name") String name, @Param("password") String password, @Param("userId") Long id);
    @Modifying
    @Query(value = "UPDATE user SET password = :password WHERE id = :userId", nativeQuery = true)
    @Transactional
    void updateUserPassword(@Param("password") String password, @Param("userId") Long id);

    @Modifying
    @Query(value = "UPDATE user SET email = :email WHERE id = :userId", nativeQuery = true)
    @Transactional
    void updateUserEmail(@Param("email") String email, @Param("userId") Long id);

    @Modifying
    @Query(value = "UPDATE user SET name = :name WHERE id = :userId", nativeQuery = true)
    @Transactional
    void updateUsername(@Param("name") String name, @Param("userId") Long id);

    @Query(value = "SELECT * FROM user WHERE group_id IS NULL", nativeQuery = true)
    List<User> findUsersWithoutGroup();

    @Modifying
    @Query(value = "INSERT INTO user_has_exercise (user_id, exercise_id) VALUES(:userId, :exerciseId)", nativeQuery = true)
    void assignUserToExercises(@Param("userId") Long userId, @Param("exerciseId") Long exerciseId);
}
