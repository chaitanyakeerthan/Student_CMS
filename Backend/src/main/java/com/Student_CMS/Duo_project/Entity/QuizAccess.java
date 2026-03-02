package com.Student_CMS.Duo_project.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "quiz_access",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"quiz_id", "student_id"})
        }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizAccess {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @Column(nullable = false)
    private boolean accessed;

    private LocalDateTime accessedAt;
}
