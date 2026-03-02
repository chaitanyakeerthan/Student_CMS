package com.Student_CMS.Duo_project.Service;

import com.Student_CMS.Duo_project.Controller.dto.AssignmentCreateRequest;
import com.Student_CMS.Duo_project.Controller.dto.StudentAssignmentResponse;
import com.Student_CMS.Duo_project.Entity.Assignment;

import java.util.List;

public interface AssignmentService {

    Assignment createAssignment(AssignmentCreateRequest request);


    List<StudentAssignmentResponse> getAssignmentsByDepartment(
            String deptName,
            Long studentId
    );

    void deleteAssignment(Long assignmentId);

    List<StudentAssignmentResponse> getAssignmentsByFaculty(Long facultyId);


}


