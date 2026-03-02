package com.Student_CMS.Duo_project.Service;

import com.Student_CMS.Duo_project.Controller.dto.DepartmentRequest;
import com.Student_CMS.Duo_project.Controller.dto.DepartmentResponse;
import com.Student_CMS.Duo_project.Entity.*;
import com.Student_CMS.Duo_project.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final CollegeRepository collegeRepository;

    @Override
    @CacheEvict(value = {"departments", "departmentById"}, allEntries = true)
    public DepartmentResponse createDepartment(DepartmentRequest request) {

        College college = collegeRepository.findById(request.getCollegeId())
                .orElseThrow(() -> new RuntimeException("College not found"));

        Department department = new Department();
        department.setDeptName(request.getDeptName());
        department.setCollege(college);

        Department saved = departmentRepository.save(department);

        return new DepartmentResponse(
                saved.getId(),
                saved.getDeptName(),
                saved.getCollege().getName()
        );
    }

    @Override
    @Cacheable(value = "departmentById", key = "#id")
    public DepartmentResponse getDepartmentById(Long id) {

        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found"));

        return new DepartmentResponse(
                department.getId(),
                department.getDeptName(),
                department.getCollege().getName()
        );
    }

    @Override
    @Cacheable("departments")
    public List<DepartmentResponse> getAllDepartments() {

        return departmentRepository.findAll()
                .stream()
                .map(dept -> new DepartmentResponse(
                        dept.getId(),
                        dept.getDeptName(),
                        dept.getCollege().getName()
                ))
                .collect(Collectors.toList());
    }

    @Override
    @CacheEvict(value = {"departments", "departmentById"}, allEntries = true)
    public DepartmentResponse updateDepartment(Long id, DepartmentRequest request) {

        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found"));

        College college = collegeRepository.findById(request.getCollegeId())
                .orElseThrow(() -> new RuntimeException("College not found"));

        department.setDeptName(request.getDeptName());
        department.setCollege(college);

        Department updated = departmentRepository.save(department);

        return new DepartmentResponse(
                updated.getId(),
                updated.getDeptName(),
                updated.getCollege().getName()
        );
    }

    @Override
    @CacheEvict(value = {"departments", "departmentById"}, allEntries = true)
    public void deleteDepartment(Long id) {

        departmentRepository.deleteById(id);
    }
}