package com.Student_CMS.Duo_project.Service;

import com.Student_CMS.Duo_project.Controller.dto.DepartmentRequest;
import com.Student_CMS.Duo_project.Controller.dto.DepartmentResponse;

import java.util.List;

public interface DepartmentService {

    DepartmentResponse createDepartment(DepartmentRequest request);

    DepartmentResponse getDepartmentById(Long id);

    List<DepartmentResponse> getAllDepartments();

    DepartmentResponse updateDepartment(Long id, DepartmentRequest request);

    void deleteDepartment(Long id);
}