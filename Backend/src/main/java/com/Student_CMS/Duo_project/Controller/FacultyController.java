package com.Student_CMS.Duo_project.Controller;

import com.Student_CMS.Duo_project.Controller.dto.*;
import com.Student_CMS.Duo_project.Entity.Faculty;
import com.Student_CMS.Duo_project.Service.AssignmentSubmissionService;
import com.Student_CMS.Duo_project.Service.FacultyService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/faculty")
@RequiredArgsConstructor

public class FacultyController {

    private final FacultyService facultyService;
    private final AssignmentSubmissionService assignmentSubmissionService;

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody FacultyLoginRequest request,
            HttpSession session) {

        var faculty = facultyService.login(
                request.getEmail(),
                request.getPassword()
        );

        if (faculty == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse("Invalid email or password"));
        }

        session.setAttribute("USER_ID", faculty.getId());
        session.setAttribute("ROLE", "FACULTY");

        return ResponseEntity.ok(
                new FacultyLoginResponse(
                        faculty.getId(),
                        faculty.getName(),
                        faculty.getEmail(),
                        faculty.getRole().name(),
                        faculty.getDepartment().getDeptName().name(),
                        faculty.getDepartment().getId(),
                        faculty.getCollege().getName()
                )
        );
    }

    @GetMapping("/assignments/{assignmentId}/stats")
    public ResponseEntity<AssignmentStatsResponse> getAssignmentStats(
            @PathVariable Long assignmentId) {

        return ResponseEntity.ok(
                assignmentSubmissionService.getAssignmentStats(assignmentId)
        );
    }

    @PostMapping("/create")
    public ResponseEntity<?> createFaculty(
            @RequestBody FacultyCreateRequest request) {

        Faculty faculty = facultyService.createFaculty(request);

        return ResponseEntity.ok(
                new ApiResponse("Faculty Created Successfully")
        );
    }
}
