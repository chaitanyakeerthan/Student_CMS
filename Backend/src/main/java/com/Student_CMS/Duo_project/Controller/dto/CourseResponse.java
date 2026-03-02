package com.Student_CMS.Duo_project.Controller.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseResponse {

    private Long id;
    private String courseName;
    private String description;
    private String courseLink;
    private String instructor;
    private int credits;
    private String departmentName;
}