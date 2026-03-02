package com.Student_CMS.Duo_project.Controller.dto;

import lombok.*;

@Data
@NoArgsConstructor
public class CourseCreateRequest {
    private String courseName;
    private String description;
    private String courseLink;
    private int departmentId;
    private String instructor;
    private int credits;
}
