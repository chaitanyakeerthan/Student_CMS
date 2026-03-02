package com.Student_CMS.Duo_project.Controller;

import com.Student_CMS.Duo_project.Controller.dto.*;
import com.Student_CMS.Duo_project.Service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @PostMapping
    public DepartmentResponse create(
            @RequestBody DepartmentRequest request) {

        return departmentService.createDepartment(request);
    }

    @GetMapping("/{id}")
    public DepartmentResponse getById(@PathVariable Long id) {

        return departmentService.getDepartmentById(id);
    }

    @GetMapping
    public List<DepartmentResponse> getAll() {

        return departmentService.getAllDepartments();
    }

    @PutMapping("/{id}")
    public DepartmentResponse update(
            @PathVariable Long id,
            @RequestBody DepartmentRequest request) {

        return departmentService.updateDepartment(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {

        departmentService.deleteDepartment(id);
    }
}