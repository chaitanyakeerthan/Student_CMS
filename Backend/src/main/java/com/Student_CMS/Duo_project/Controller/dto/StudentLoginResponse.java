package com.Student_CMS.Duo_project.Controller.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentLoginResponse {
    private Long id;
    private String studentFullName;
    private String rollNo;
    private String email;
    private String gender;
    private int joinedYear;
    private int age;
    private String role;
    private String department;
    private String collegeName;
}
