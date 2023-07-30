package ua.edu.khpi.project2023.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.edu.khpi.project2023.model.Test;

public interface TestRepository extends JpaRepository<Test, Long> {
}
