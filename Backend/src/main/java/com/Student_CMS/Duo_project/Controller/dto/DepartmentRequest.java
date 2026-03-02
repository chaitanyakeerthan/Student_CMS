package com.Student_CMS.Duo_project.Controller.dto;

import com.Student_CMS.Duo_project.Entity.type.DepartmentType;
import lombok.*;

@Data
@NoArgsConstructor
public class DepartmentRequest {
    private DepartmentType deptName;
    private Long collegeId;
}
