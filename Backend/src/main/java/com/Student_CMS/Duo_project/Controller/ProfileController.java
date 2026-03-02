package com.Student_CMS.Duo_project.Controller;

import com.Student_CMS.Duo_project.Controller.dto.StudentLoginResponse;
import com.Student_CMS.Duo_project.Controller.dto.FacultyLoginResponse;
import com.Student_CMS.Duo_project.Entity.Student;
import com.Student_CMS.Duo_project.Entity.Faculty;
import com.Student_CMS.Duo_project.Repository.StudentRepository;
import com.Student_CMS.Duo_project.Repository.FacultyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProfileController {

    private final StudentRepository studentRepository;
    private final FacultyRepository facultyRepository;

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(
            @RequestParam String email,
            @RequestParam String role
    ) {

        if ("STUDENT".equalsIgnoreCase(role)) {

            Student student = studentRepository.findByEmail(email);

            if (student == null) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body("Student not found");
            }

            StudentLoginResponse dto = new StudentLoginResponse();
            dto.setId(student.getId());
            dto.setStudentFullName(student.getStudentFullName());
            dto.setRollNo(student.getRollNo());
            dto.setEmail(student.getEmail());
            dto.setGender(student.getGender() != null ? student.getGender().name() : null);
            dto.setAge(student.getAge());
            dto.setJoinedYear(student.getJoinedYear());
            dto.setRole(student.getRole().name());
            dto.setDepartment(
                    student.getDepartment() != null
                            ? student.getDepartment().getDeptName().name()
                            : null
            );
            dto.setCollegeName(
                    student.getCollege() != null
                            ? student.getCollege().getName()
                            : null
            );

            return ResponseEntity.ok(dto);
        }

        if ("FACULTY".equalsIgnoreCase(role)) {

            Faculty faculty = facultyRepository.findByEmail(email);

            if (faculty == null) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body("Faculty not found");
            }

            FacultyLoginResponse dto = new FacultyLoginResponse(
                    faculty.getId(),
                    faculty.getName(),
                    faculty.getEmail(),
                    faculty.getRole().name(),
                    faculty.getDepartment().getDeptName().name(),
                    faculty.getDepartment().getId(),
                    faculty.getCollege().getName()
            );

            return ResponseEntity.ok(dto);
        }

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Invalid role");
    }
}
