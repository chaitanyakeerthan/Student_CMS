package com.Student_CMS.Duo_project.Controller.dto;

import lombok.*;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;

@Data
@NoArgsConstructor
public class AssignmentCreateRequest {

    private String title;
    private String description;
    private String department;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dueDate;

    private Long facultyId;
}
