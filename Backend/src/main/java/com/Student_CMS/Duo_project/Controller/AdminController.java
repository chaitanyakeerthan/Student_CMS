package com.Student_CMS.Duo_project.Controller;

import com.Student_CMS.Duo_project.Controller.dto.AssignmentStatsResponse;
import com.Student_CMS.Duo_project.Entity.Assignment;
import com.Student_CMS.Duo_project.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AssignmentRepository assignmentRepository;
    private final AssignmentSubmissionRepository submissionRepository;
    private final StudentRepository studentRepository;

    @GetMapping("/assignments/{assignmentId}/stats")
    public AssignmentStatsResponse getStats(@PathVariable Long assignmentId) {

        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new RuntimeException("Assignment not found"));

        long submitted =
                submissionRepository.countByAssignment_Id(assignmentId);

        long totalStudents =
                studentRepository.countByDepartment_Id(
                        assignment.getDepartment().getId()
                );

        return new AssignmentStatsResponse(
                assignmentId,
                totalStudents,
                submitted,
                totalStudents - submitted
        );
    }
}
