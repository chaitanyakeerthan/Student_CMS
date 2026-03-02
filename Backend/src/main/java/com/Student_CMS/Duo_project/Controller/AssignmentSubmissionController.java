package com.Student_CMS.Duo_project.Controller;

import com.Student_CMS.Duo_project.Controller.dto.ApiResponse;
import com.Student_CMS.Duo_project.Controller.dto.DocumentResponse;
import com.Student_CMS.Duo_project.Service.AssignmentSubmissionService;
import com.Student_CMS.Duo_project.Service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/assignment-submissions")
@RequiredArgsConstructor
public class AssignmentSubmissionController {

    private final AssignmentSubmissionService assignmentSubmissionService;
    private final DocumentService documentService;

    @PostMapping("/{assignmentId}/student/{studentId}/submit")
    public ResponseEntity<?> submitAssignment(
            @PathVariable Long assignmentId,
            @PathVariable Long studentId,
            @RequestParam("file") MultipartFile file
    ) throws Exception {

        assignmentSubmissionService.submitAnswer(
                assignmentId,
                studentId,
                file
        );

        return ResponseEntity.ok(
                new ApiResponse("Assignment submitted successfully")
        );
    }
    @GetMapping("/{assignmentId}/submissions")
    public ResponseEntity<?> getAssignmentSubmissions(
            @PathVariable Long assignmentId) {

        return ResponseEntity.ok(
                assignmentSubmissionService
                        .getSubmissionDetailsForAssignment(assignmentId)
        );
    }
    @GetMapping("/submissions/{submissionId}/download")
    public ResponseEntity<byte[]> downloadStudentAnswer(
            @PathVariable Long submissionId) {

        DocumentResponse doc =
                documentService.getAssignmentSubmissionFile(submissionId);

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + doc.getFileName() + "\""
                )
                .contentType(MediaType.parseMediaType(doc.getFileType()))
                .body(doc.getData());
    }
}
