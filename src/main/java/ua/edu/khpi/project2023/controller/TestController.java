package ua.edu.khpi.project2023.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.edu.khpi.project2023.model.Test;
import ua.edu.khpi.project2023.model.request.TestUpsertRequest;
import ua.edu.khpi.project2023.model.response.GroupsProgressResponse;
import ua.edu.khpi.project2023.model.response.UserProgressResponse;
import ua.edu.khpi.project2023.service.TestService;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/test")
public class TestController {

    @Autowired
    TestService testService;

    @PostMapping
    @PreAuthorize("hasRole('TEACHER')")
    ResponseEntity<Test> createTest(@RequestBody @Valid TestUpsertRequest request) {
        return ResponseEntity.ok(testService.createTest(request));
    }

    @PutMapping("/{testId}")
    @PreAuthorize("hasRole('TEACHER')")
    ResponseEntity<Test> updateTest(@RequestBody @Valid TestUpsertRequest request, @PathVariable("testId") Long testId) {
        return ResponseEntity.ok(testService.updateTest(request, testId));
    }

    @DeleteMapping("/{testId}")
    @PreAuthorize("hasRole('TEACHER')")
    ResponseEntity<Void> deleteTest(@PathVariable("testId") Long testId) {
        testService.deleteTest(testId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    @PreAuthorize("hasRole('TEACHER')")
    ResponseEntity<List<Test>> getAllTest() {
        return ResponseEntity.ok(testService.getAllTests());
    }

    @GetMapping("/my")
    @PreAuthorize("hasRole('TEACHER')")
    ResponseEntity<List<Test>> getMyTest() {
        return ResponseEntity.ok(testService.getMyTests());
    }

    @GetMapping("/{testId}")
    @PreAuthorize("hasRole('TEACHER')")
    ResponseEntity<Test> getTestById(@PathVariable("testId") Long id) {
        return ResponseEntity.ok(testService.getTestById(id));
    }

    @GetMapping("/my/test")
    @PreAuthorize("hasRole('STUDENT')")
    ResponseEntity<List<Test>> getMyStudentTest(@RequestParam(value = "status", required = false) String status) {
        return ResponseEntity.ok(testService.getMyStudentTest(status));
    }

    @GetMapping("/progress/{testId}")
    @PreAuthorize("hasRole('TEACHER')")
    ResponseEntity<List<GroupsProgressResponse>> getGroupResultsByTestId(@PathVariable("testId") Long testId) {
        return ResponseEntity.ok(testService.getGroupsProgressByTestId(testId));
    }

    @GetMapping("/userProgress")
    @PreAuthorize("hasRole('TEACHER')")
    ResponseEntity<List<UserProgressResponse>> getUserResultsByTestAndGroupId(@RequestParam(value = "groupId") Long groupId,
                                                                              @RequestParam(value = "testId") Long testId) {

        return ResponseEntity.ok(testService.getUserProgressResponseByTestAndGroupId(testId, groupId));
    }

    @PutMapping("/enable/{testId}/{enabled}")
    @PreAuthorize("hasRole('TEACHER')")
    ResponseEntity<Test> enableTest(@PathVariable("testId") Long testId, @PathVariable("enabled") boolean enabled) {
        return ResponseEntity.ok(testService.enableTest(enabled, testId));
    }

    @PutMapping("/assign-to-group")
    @PreAuthorize("hasRole('TEACHER')")
    ResponseEntity<Void> assignTestToGroup(@RequestParam("testId") Long testId, @RequestParam("groupId") Long groupId) {
        testService.assignTestToGroup(testId, groupId);
        return ResponseEntity.ok().build();
    }
}
