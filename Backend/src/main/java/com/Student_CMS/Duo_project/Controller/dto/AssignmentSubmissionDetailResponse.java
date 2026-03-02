package com.Student_CMS.Duo_project.Controller.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssignmentSubmissionDetailResponse {

    private Long submissionId;
    private Long studentId;
    private String studentName;
    private String rollNo;

    private String status;
    private LocalDateTime submittedAt;
    private boolean answerUploaded;
    private String answerFileName;
}

