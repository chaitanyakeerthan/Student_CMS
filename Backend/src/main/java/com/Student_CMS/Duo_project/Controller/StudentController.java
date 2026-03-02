package com.Student_CMS.Duo_project.Controller;

import com.Student_CMS.Duo_project.Controller.dto.*;
import com.Student_CMS.Duo_project.Entity.Student;
import com.Student_CMS.Duo_project.Service.QuizAccessService;
import com.Student_CMS.Duo_project.Service.StudentService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;
    private final QuizAccessService quizAccessService;

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody StudentLoginRequest request,
            HttpSession session) {

        Student student = studentService.login(
                request.getEmail(),
                request.getRollNo()
        );

        if (student == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse("Invalid email or roll number"));
        }

        session.setAttribute("USER_ID", student.getId());
        session.setAttribute("ROLE", "STUDENT");

        return ResponseEntity.ok(
                new StudentLoginResponse(
                        student.getId(),
                        student.getStudentFullName(),
                        student.getRollNo(),
                        student.getEmail(),
                        student.getGender().name(),
                        student.getJoinedYear(),
                        student.getAge(),
                        student.getRole().name(),
                        student.getDepartment().getDeptName().name(),
                        student.getCollege().getName()
                )
        );
    }

    @GetMapping("/quizzes/{quizId}/open")
    public ResponseEntity<?> openQuiz(
            @PathVariable Long quizId,
            HttpSession session) {

        Long studentId = (Long) session.getAttribute("USER_ID");
        String role = (String) session.getAttribute("ROLE");

        if (studentId == null || !"STUDENT".equals(role)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Unauthorized");
        }

        OpenQuizResponse response =
                quizAccessService.openQuiz(quizId, studentId);

        return ResponseEntity.ok(response);
    }
    @PostMapping("/create")
    public ResponseEntity<?> createStudent(
            @RequestBody StudentCreateRequest request) {

        Student student = studentService.createStudent(request);

        return ResponseEntity.ok(student);
    }
}
