package com.Student_CMS.Duo_project.Controller;

import com.Student_CMS.Duo_project.Controller.dto.QuizCreateRequest;
import com.Student_CMS.Duo_project.Entity.Department;
import com.Student_CMS.Duo_project.Entity.Quiz;
import com.Student_CMS.Duo_project.Entity.type.DepartmentType;
import com.Student_CMS.Duo_project.Repository.DepartmentRepository;
import com.Student_CMS.Duo_project.Service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/quizzes")
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;
    private final DepartmentRepository departmentRepository;

    @PostMapping("/create")
    public ResponseEntity<Quiz> createQuiz(
            @RequestBody QuizCreateRequest request) {

        DepartmentType deptType =
                DepartmentType.valueOf(request.getDepartment().toUpperCase());

        Department department = departmentRepository
                .findByDeptName(deptType)
                .orElseThrow(() -> new RuntimeException("Department not found"));

        Quiz quiz = new Quiz();
        quiz.setTitle(request.getTitle());
        quiz.setDescription(request.getDescription());
        quiz.setQuizLink(request.getQuizLink());
        quiz.setResponseLink(request.getResponseLink());
        quiz.setDepartment(department);
        quiz.setEndDate(LocalDate.parse(request.getEndDate()));

        return ResponseEntity.ok(quizService.createQuiz(quiz));
    }

    @GetMapping("/branch/{branch}")
    public ResponseEntity<List<Quiz>> getByBranch(
            @PathVariable String branch) {

        return ResponseEntity.ok(
                quizService.getQuizzesByBranch(branch)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteQuiz(@PathVariable Long id) {

        quizService.deleteQuiz(id);

        return ResponseEntity.ok("Quiz deleted successfully");
    }
}
