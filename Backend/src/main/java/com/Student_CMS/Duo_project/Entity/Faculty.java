package com.Student_CMS.Duo_project.Entity;

import com.Student_CMS.Duo_project.Entity.type.*;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "faculty")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Faculty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "faculty_name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private GenderType gender;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleType role = RoleType.FACULTY;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "college_id", nullable = false)
    private College college;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @PrePersist
    public void setDefaultRole() {
        this.role = RoleType.FACULTY;
    }
}
