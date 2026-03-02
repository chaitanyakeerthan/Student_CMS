package com.Student_CMS.Duo_project.Service;

import com.Student_CMS.Duo_project.Controller.dto.AssignmentStatsResponse;
import com.Student_CMS.Duo_project.Controller.dto.AssignmentSubmissionDetailResponse;
import com.Student_CMS.Duo_project.Entity.*;
import com.Student_CMS.Duo_project.Entity.type.SubmissionStatus;
import com.Student_CMS.Duo_project.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.cache.annotation.Cacheable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AssignmentSubmissionServiceImpl implements AssignmentSubmissionService {

    private final AssignmentSubmissionRepository submissionRepository;
    private final AssignmentRepository assignmentRepository;
    private final DocumentRepository documentRepository;

    @Override
    @CacheEvict(
            value = {
                    "assignmentsByDept",
                    "assignmentStats",
                    "submissionDetails"
            },
            allEntries = true
    )
    public void submitAnswer(Long assignmentId, Long studentId, MultipartFile file)
            throws Exception {

        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new RuntimeException("Assignment not found"));
        AssignmentSubmission submission =
                submissionRepository
                        .findByAssignment_IdAndStudent_Id(assignmentId, studentId)
                        .orElseThrow(() ->
                                new RuntimeException("Submission row not found. Data integrity issue")
                        );
        if (!documentRepository.findBySubmission_Id(submission.getId()).isEmpty()) {
            throw new RuntimeException("Assignment already submitted");
        }

        SubmissionStatus status =
                LocalDate.now().isAfter(assignment.getDueDate())
                        ? SubmissionStatus.LATE
                        : SubmissionStatus.SUBMITTED;

        submission.setStatus(status);
        submission.setSubmittedAt(LocalDateTime.now());

        submissionRepository.save(submission);

        Document document = Document.builder()
                .fileName(file.getOriginalFilename())
                .fileType(file.getContentType())
                .data(file.getBytes())
                .submission(submission)
                .build();

        documentRepository.save(document);
    }
    @Override
    @Cacheable(
            value = "assignmentStats",
            key = "#assignmentId"
    )
    @Transactional(readOnly = true)
    public AssignmentStatsResponse getAssignmentStats(Long assignmentId) {

        long total = submissionRepository.countByAssignment_Id(assignmentId);

        long submitted = submissionRepository.countByAssignment_IdAndStatus(
                assignmentId, SubmissionStatus.SUBMITTED
        );

        long late = submissionRepository.countByAssignment_IdAndStatus(
                assignmentId, SubmissionStatus.LATE
        );

        long pending = total - (submitted + late);

        return new AssignmentStatsResponse(
                assignmentId,
                total,
                submitted + late,
                pending
        );
    }

    @Override
    @Cacheable(
            value = "submissionDetails",
            key = "#assignmentId"
    )
    @Transactional(readOnly = true)
    public List<AssignmentSubmissionDetailResponse>
    getSubmissionDetailsForAssignment(Long assignmentId) {

        return submissionRepository.findByAssignment_Id(assignmentId)
                .stream()
                .map(sub -> {

                    Document answer =
                            documentRepository.findBySubmission_Id(sub.getId())
                                    .stream()
                                    .findFirst()
                                    .orElse(null);

                    return new AssignmentSubmissionDetailResponse(
                            sub.getId(),
                            sub.getStudent().getId(),
                            sub.getStudent().getStudentFullName(),
                            sub.getStudent().getRollNo(),
                            sub.getStatus().name(),
                            sub.getSubmittedAt(),
                            answer != null,
                            answer != null ? answer.getFileName() : null
                    );
                })
                .toList();
    }
}
