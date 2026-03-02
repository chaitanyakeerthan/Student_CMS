package com.Student_CMS.Duo_project.Controller.dto;

import lombok.*;

@Data
@NoArgsConstructor
public class QuizCreateRequest {

    private String title;
    private String description;

    private String quizLink;
    private String responseLink;

    private String department;
    private String endDate;
}


