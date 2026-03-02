package com.Student_CMS.Duo_project.Controller.dto;

import lombok.*;

@Data
@NoArgsConstructor
public class StudentCreateRequest {

    private String studentFullName;
    private int age;
    private String gender;
    private int joinedYear;
    private String email;
    private String rollNo;
    private Long collegeId;
    private Long departmentId;
}
