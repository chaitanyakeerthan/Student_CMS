package com.Student_CMS.Duo_project.Controller.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FacultyLoginResponse {
    private Long id;
    private String name;
    private String email;
    private String role;
    private String department;
    private Long departmentId;
    private String collegeName;
}
