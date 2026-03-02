package com.Student_CMS.Duo_project.Controller.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentAssignmentResponse {

    private Long assignmentId;
    private String title;
    private String description;
    private String department;
    private LocalDate dueDate;
    private boolean questionUploaded;
    private String questionFileName;
    private Long submissionId;
    private boolean answerUploaded;
    private String answerFileName;
}
