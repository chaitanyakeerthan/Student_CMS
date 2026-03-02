package com.Student_CMS.Duo_project.Service;

import com.Student_CMS.Duo_project.Controller.dto.FacultyCreateRequest;
import com.Student_CMS.Duo_project.Entity.*;
import com.Student_CMS.Duo_project.Entity.type.GenderType;
import com.Student_CMS.Duo_project.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FacultyServiceImpl implements FacultyService {

    private final FacultyRepository facultyRepository;
    private final CollegeRepository collegeRepository;
    private final DepartmentRepository departmentRepository;

    @Override
    public Faculty createFaculty(FacultyCreateRequest request) {

        College college = collegeRepository.findById(request.getCollegeId())
                .orElseThrow(() -> new RuntimeException("College not found"));

        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found"));

        Faculty faculty = new Faculty();
        faculty.setName(request.getName());
        faculty.setGender(GenderType.valueOf(request.getGender()));
        faculty.setEmail(request.getEmail());
        faculty.setPassword(request.getPassword());
        faculty.setCollege(college);
        faculty.setDepartment(department);

        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty login(String email, String password) {

        Faculty faculty = facultyRepository.findByEmail(email);

        if (faculty == null) return null;
        if (!faculty.getPassword().equals(password)) return null;

        return faculty;
    }
}