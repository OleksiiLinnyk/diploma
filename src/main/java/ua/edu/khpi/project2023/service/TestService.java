package ua.edu.khpi.project2023.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.khpi.project2023.exception.NotFoundException;
import ua.edu.khpi.project2023.model.Test;
import ua.edu.khpi.project2023.repository.TestRepository;

@Service
public class TestService {

    @Autowired
    TestRepository testRepository;

    public Test getTestById(Long id) {
        return testRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Test by id %d was not found", id)));
    }
}
