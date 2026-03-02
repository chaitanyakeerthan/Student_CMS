package com.Student_CMS.Duo_project.Controller.dto;

import com.Student_CMS.Duo_project.Entity.type.DepartmentType;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentResponse {
    private Long id;
    private DepartmentType deptName;
    private String collegeName;
}
