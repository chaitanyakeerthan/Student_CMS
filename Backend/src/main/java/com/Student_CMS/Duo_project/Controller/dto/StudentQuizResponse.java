package com.Student_CMS.Duo_project.Controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentQuizResponse {

    private Long id;
    private String title;
    private String description;
    private String quizLink;
    private String department;
    private LocalDate endDate;
}
