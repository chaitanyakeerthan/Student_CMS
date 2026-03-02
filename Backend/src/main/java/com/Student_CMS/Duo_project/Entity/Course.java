package com.Student_CMS.Duo_project.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String courseName;
    private String description;
    private String courseLink;
    private String instructor;
    private int credits;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
}
