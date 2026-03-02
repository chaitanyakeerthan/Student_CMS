package com.Student_CMS.Duo_project.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "college")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"students", "faculty", "departments"})
public class College {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "college_name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "college")
    private List<Student> students;

    @OneToMany(mappedBy = "college")
    private List<Faculty> faculty;

    @OneToMany(mappedBy = "college")
    private List<Department> departments;
}
