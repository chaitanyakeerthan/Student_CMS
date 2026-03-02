package com.Student_CMS.Duo_project.Controller;

import com.Student_CMS.Duo_project.Controller.dto.DocumentResponse;
import com.Student_CMS.Duo_project.Controller.dto.DocumentUploadResponse;
import com.Student_CMS.Duo_project.Entity.Assignment;
import com.Student_CMS.Duo_project.Entity.AssignmentSubmission;
import com.Student_CMS.Duo_project.Entity.Document;
import com.Student_CMS.Duo_project.Repository.AssignmentRepository;
import com.Student_CMS.Duo_project.Repository.AssignmentSubmissionRepository;
import com.Student_CMS.Duo_project.Repository.DocumentRepository;
import com.Student_CMS.Duo_project.Service.DocumentService;

import lombok.RequiredArgsConstructor;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;
    private final DocumentRepository documentRepository;
    private final AssignmentRepository assignmentRepository;
    private final AssignmentSubmissionRepository submissionRepository;

    @PostMapping(
            value = "/assignment/{assignmentId}/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    @CacheEvict(
            value = {
                    "assignmentsByDept",
                    "assignmentsByFaculty"
            },
            allEntries = true
    )
    public ResponseEntity<DocumentUploadResponse> uploadAssignmentQuestion(
            @PathVariable Long assignmentId,
            @RequestParam("file") MultipartFile file
    ) throws Exception {

        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Assignment not found"));
        if (!documentRepository.findByAssignment_Id(assignmentId).isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Question already uploaded for this assignment"
            );
        }

        Document document = Document.builder()
                .fileName(file.getOriginalFilename())
                .fileType(file.getContentType())
                .data(file.getBytes())
                .assignment(assignment)
                .build();

        Document saved = documentRepository.save(document);

        return ResponseEntity.ok(
                new DocumentUploadResponse(
                        saved.getId(),
                        saved.getFileName(),
                        "Assignment question uploaded successfully"
                )
        );
    }

    @PostMapping(
            value = "/submission/{submissionId}/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    @CacheEvict(
            value = {
                    "assignmentsByDept",
                    "assignmentsByFaculty",
                    "assignmentStats",
                    "submissionDetails"
            },
            allEntries = true
    )
    public ResponseEntity<DocumentUploadResponse> uploadSubmissionAnswer(
            @PathVariable Long submissionId,
            @RequestParam("file") MultipartFile file
    ) throws Exception {

        AssignmentSubmission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Submission not found"));
        if (!documentRepository.findBySubmission_Id(submissionId).isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Assignment already submitted"
            );
        }

        Document document = Document.builder()
                .fileName(file.getOriginalFilename())
                .fileType(file.getContentType())
                .data(file.getBytes())
                .submission(submission)
                .build();

        Document saved = documentRepository.save(document);

        return ResponseEntity.ok(
                new DocumentUploadResponse(
                        saved.getId(),
                        saved.getFileName(),
                        "Answer submitted successfully"
                )
        );
    }

    @GetMapping("/assignment/{assignmentId}")
    public ResponseEntity<byte[]> downloadAssignmentQuestion(
            @PathVariable Long assignmentId
    ) {

        DocumentResponse doc =
                documentService.getAssignmentQuestion(assignmentId);

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + doc.getFileName() + "\""
                )
                .contentType(MediaType.parseMediaType(doc.getFileType()))
                .body(doc.getData());
    }
    @GetMapping("/submission/{submissionId}")
    public ResponseEntity<byte[]> downloadSubmissionFile(
            @PathVariable Long submissionId
    ) {

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