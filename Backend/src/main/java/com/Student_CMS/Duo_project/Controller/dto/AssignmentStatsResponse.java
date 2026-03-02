package com.Student_CMS.Duo_project.Controller.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssignmentStatsResponse {
    private Long assignmentId;
    private Long totalStudents;
    private Long submitted;
    private Long pending;
}
