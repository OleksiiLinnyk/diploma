package ua.edu.khpi.project2023.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.edu.khpi.project2023.model.ERole;
import ua.edu.khpi.project2023.model.Role;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);

    @Query(value = "SELECT * FROM role WHERE name != 'ROLE_ADMIN'",
            nativeQuery = true)
    List<Role> getAllRoles();
}
