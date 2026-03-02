package com.Student_CMS.Duo_project.Controller;

import com.Student_CMS.Duo_project.Controller.dto.ApiResponse;
import com.Student_CMS.Duo_project.Controller.dto.AssignmentCreateRequest;
import com.Student_CMS.Duo_project.Controller.dto.StudentAssignmentResponse;
import com.Student_CMS.Duo_project.Service.AssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assignments")
@RequiredArgsConstructor
public class AssignmentController {

    private final AssignmentService assignmentService;


    @GetMapping("/branch/{branch}/{studentId}")
    public List<StudentAssignmentResponse> getByDepartment(
            @PathVariable String branch,
            @PathVariable Long studentId
    ) {
        return assignmentService.getAssignmentsByDepartment(branch, studentId);
    }

    @DeleteMapping("/{assignmentId}")
    public ResponseEntity<String> deleteAssignment(
            @PathVariable Long assignmentId
    ) {
        assignmentService.deleteAssignment(assignmentId);
        return ResponseEntity.ok("Assignment deleted successfully");
    }

    @GetMapping("/faculty/{facultyId}")
    public List<StudentAssignmentResponse> getByFaculty(
            @PathVariable Long facultyId
    ) {
        return assignmentService.getAssignmentsByFaculty(facultyId);
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createAssignment(
            @RequestBody AssignmentCreateRequest request
    ) {
        assignmentService.createAssignment(request);
        return ResponseEntity.ok(new ApiResponse("Assignment created successfully"));
    }




}
