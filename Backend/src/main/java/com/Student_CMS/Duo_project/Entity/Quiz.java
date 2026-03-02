package com.Student_CMS.Duo_project.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "quizzes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 500)
    private String description;

    @Column(nullable = false, length = 1000)
    private String quizLink;

    @Column(length = 1000)
    private String responseLink;


    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;



    @Column(nullable = false)
    private LocalDate endDate;
}
