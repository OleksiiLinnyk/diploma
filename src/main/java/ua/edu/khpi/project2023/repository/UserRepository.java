package ua.edu.khpi.project2023.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.edu.khpi.project2023.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);

    @Query(value = "SELECT * FROM user WHERE email != :email",
            nativeQuery = true)
    List<User> getAllUsers(@Param("email") String email);

    @Query(value = "SELECT * FROM user u \n" +
            "JOIN role r ON r.id = u.role_id \n" +
            "JOIN student_group g ON g.id = u.group_id WHERE r.name = 'ROLE_STUDENT' AND g.name = :groupName ORDER BY u.name",
            nativeQuery = true)
    List<User> findAllStudentsInGroup(@Param("groupName") String groupName);

}
