package com.Student_CMS.Duo_project.Service;

import com.Student_CMS.Duo_project.Controller.dto.AssignmentCreateRequest;
import com.Student_CMS.Duo_project.Controller.dto.StudentAssignmentResponse;
import com.Student_CMS.Duo_project.Entity.*;
import com.Student_CMS.Duo_project.Entity.type.DepartmentType;
import com.Student_CMS.Duo_project.Entity.type.SubmissionStatus;
import com.Student_CMS.Duo_project.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AssignmentServiceImpl implements AssignmentService {

    private final AssignmentRepository assignmentRepository;
    private final DepartmentRepository departmentRepository;
    private final FacultyRepository facultyRepository;
    private final StudentRepository studentRepository;
    private final AssignmentSubmissionRepository submissionRepository;
    private final DocumentRepository documentRepository;

    @Override
    @CacheEvict(
            value = {
                    "assignmentsByDept",
                    "assignmentsByFaculty"
            },
            allEntries = true
    )
    public Assignment createAssignment(AssignmentCreateRequest request) {

        if (request.getDueDate().isBefore(LocalDate.now())) {
            throw new RuntimeException("Due date must be a future date");
        }

        DepartmentType dept =
                DepartmentType.valueOf(request.getDepartment().toUpperCase());

        Department department = departmentRepository
                .findByDeptName(dept)
                .orElseThrow(() -> new RuntimeException("Department not found"));

        Faculty faculty = facultyRepository
                .findById(request.getFacultyId())
                .orElseThrow(() -> new RuntimeException("Faculty not found"));

        Assignment assignment = assignmentRepository.save(
                Assignment.builder()
                        .title(request.getTitle())
                        .description(request.getDescription())
                        .dueDate(request.getDueDate())
                        .department(department)
                        .faculty(faculty)
                        .build()
        );
        List<Student> students =
                studentRepository.findByDepartment_Id(department.getId());

        List<AssignmentSubmission> submissions =
                students.stream()
                        .map(student ->
                                AssignmentSubmission.builder()
                                        .assignment(assignment)
                                        .student(student)
                                        .status(SubmissionStatus.NOT_SUBMITTED)
                                        .submittedAt(null)
                                        .build()
                        )
                        .toList();

        submissionRepository.saveAll(submissions);

        return assignment;
    }
    @Override
    @Cacheable(
            value = "assignmentsByDept",
            key = "#branch + '_' + #studentId"
    )
    public List<StudentAssignmentResponse> getAssignmentsByDepartment(
            String branch,
            Long studentId
    ) {

        DepartmentType dept =
                DepartmentType.valueOf(branch.toUpperCase());

        return assignmentRepository.findByDepartment_DeptName(dept)
                .stream()
                .map(a -> {

                    AssignmentSubmission submission =
                            submissionRepository
                                    .findByAssignment_IdAndStudent_Id(a.getId(), studentId)
                                    .orElse(null);

                    Document questionDoc =
                            documentRepository.findByAssignment_Id(a.getId())
                                    .stream()
                                    .findFirst()
                                    .orElse(null);

                    Document answerDoc =
                            submission == null ? null :
                                    documentRepository.findBySubmission_Id(submission.getId())
                                            .stream()
                                            .findFirst()
                                            .orElse(null);

                    return new StudentAssignmentResponse(
                            a.getId(),
                            a.getTitle(),
                            a.getDescription(),
                            a.getDepartment().getDeptName().name(),
                            a.getDueDate(),
                            questionDoc != null,
                            questionDoc != null ? questionDoc.getFileName() : null,
                            submission != null ? submission.getId() : null,
                            answerDoc != null,
                            answerDoc != null ? answerDoc.getFileName() : null
                    );
                })
                .toList();
    }
    @Override
    @Cacheable(
            value = "assignmentsByFaculty",
            key = "#facultyId"
    )
    public List<StudentAssignmentResponse> getAssignmentsByFaculty(Long facultyId) {

        return assignmentRepository.findByFaculty_Id(facultyId)
                .stream()
                .map(a -> {

                    Document questionDoc =
                            documentRepository.findByAssignment_Id(a.getId())
                                    .stream()
                                    .findFirst()
                                    .orElse(null);

                    return new StudentAssignmentResponse(
                            a.getId(),
                            a.getTitle(),
                            a.getDescription(),
                            a.getDepartment().getDeptName().name(),
                            a.getDueDate(),
                            questionDoc != null,
                            questionDoc != null ? questionDoc.getFileName() : null,
                            null,
                            false,
                            null
                    );
                })
                .toList();
    }
    @Override
    @CacheEvict(
            value = {
                    "assignmentsByDept",
                    "assignmentsByFaculty",
                    "assignmentStats",
                    "submissionDetails"
            },
            allEntries = true
    )
    public void deleteAssignment(Long assignmentId) {
        documentRepository.deleteBySubmission_Assignment_Id(assignmentId);
        documentRepository.deleteByAssignment_Id(assignmentId);
        submissionRepository.deleteByAssignment_Id(assignmentId);
        assignmentRepository.deleteById(assignmentId);
    }
}