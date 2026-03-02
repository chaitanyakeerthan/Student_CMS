package com.Student_CMS.Duo_project.Service;

import com.Student_CMS.Duo_project.Controller.dto.StudentCreateRequest;
import com.Student_CMS.Duo_project.Entity.*;
import com.Student_CMS.Duo_project.Entity.type.GenderType;
import com.Student_CMS.Duo_project.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final CollegeRepository collegeRepository;
    private final DepartmentRepository departmentRepository;

    @Override
    public Student createStudent(StudentCreateRequest request) {

        College college = collegeRepository.findById(request.getCollegeId())
                .orElseThrow(() -> new RuntimeException("College not found"));

        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found"));

        Student student = new Student();
        student.setStudentFullName(request.getStudentFullName());
        student.setAge(request.getAge());
        student.setGender(GenderType.valueOf(request.getGender()));
        student.setJoinedYear(request.getJoinedYear());
        student.setEmail(request.getEmail());
        student.setRollNo(request.getRollNo());
        student.setCollege(college);
        student.setDepartment(department);

        return studentRepository.save(student);
    }

    @Override
    public Student login(String email, String rollNo) {
        Student student = studentRepository.findByEmail(email);
        if (student == null) return null;
        if (!student.getRollNo().equals(rollNo)) return null;
        return student;
    }
}
