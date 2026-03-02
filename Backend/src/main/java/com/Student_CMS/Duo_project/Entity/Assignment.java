package com.Student_CMS.Duo_project.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Assignment {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        @Column(nullable = false)
        private String title;
        @Column(length = 2000)
        private String description;
        private LocalDate dueDate;
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "faculty_id", nullable = false)
        private Faculty faculty;

        @OneToMany(mappedBy = "assignment", cascade = CascadeType.ALL, orphanRemoval = true)
        @ToString.Exclude
        @EqualsAndHashCode.Exclude
        private List<AssignmentSubmission> submissions;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "department_id", nullable = false)
        @ToString.Exclude
        @EqualsAndHashCode.Exclude
        private Department department;

    @OneToOne(mappedBy = "assignment", cascade = CascadeType.ALL, orphanRemoval = true)
    private Document questionDocument;
}
