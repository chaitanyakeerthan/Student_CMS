package com.Student_CMS.Duo_project.Controller.dto;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDashboardResponse {
    private List<StudentAssignmentResponse> assignments;
    private List<StudentQuizResponse> quizzes;
}
