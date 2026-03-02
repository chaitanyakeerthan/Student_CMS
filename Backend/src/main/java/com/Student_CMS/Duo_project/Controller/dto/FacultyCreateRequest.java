package com.Student_CMS.Duo_project.Controller.dto;

import lombok.*;

@Data
@NoArgsConstructor
public class FacultyCreateRequest {

    private String name;
    private String gender;
    private String email;
    private String password;
    private Long collegeId;
    private Long departmentId;
}
