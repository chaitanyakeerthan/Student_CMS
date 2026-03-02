package com.Student_CMS.Duo_project.Service;

import com.Student_CMS.Duo_project.Controller.dto.CourseCreateRequest;
import com.Student_CMS.Duo_project.Controller.dto.CourseResponse;
import com.Student_CMS.Duo_project.Entity.Course;
import com.Student_CMS.Duo_project.Entity.Department;
import com.Student_CMS.Duo_project.Entity.type.DepartmentType;
import com.Student_CMS.Duo_project.Repository.CourseRepository;
import com.Student_CMS.Duo_project.Repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final DepartmentRepository departmentRepository;

    @Override
    @CacheEvict(
            value = {
                    "allCourses",
                    "coursesByDeptId",
                    "coursesByDeptName"
            },
            allEntries = true
    )
    public CourseResponse createCourse(CourseCreateRequest request) {

        Department department = departmentRepository.findById((long) request.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found"));

        Course course = new Course();
        course.setCourseName(request.getCourseName());
        course.setDescription(request.getDescription());
        course.setCourseLink(request.getCourseLink());
        course.setInstructor(request.getInstructor());
        course.setCredits(request.getCredits());
        course.setDepartment(department);

        Course saved = courseRepository.save(course);

        return mapToResponse(saved);
    }

    @Override
    @Cacheable("allCourses")
    public List<CourseResponse> getAllCourses() {
        return courseRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(
            value = "coursesByDeptId",
            key = "#departmentId"
    )
    public List<CourseResponse> getCoursesByDepartment(int departmentId) {
        return courseRepository.findByDepartment_Id(departmentId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @CacheEvict(
            value = {
                    "allCourses",
                    "coursesByDeptId",
                    "coursesByDeptName"
            },
            allEntries = true
    )
    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }

    private CourseResponse mapToResponse(Course course) {
        return new CourseResponse(
                course.getId(),
                course.getCourseName(),
                course.getDescription(),
                course.getCourseLink(),
                course.getInstructor(),
                course.getCredits(),
                course.getDepartment().getDeptName().name()
        );
    }

    @Override
    @Cacheable(
            value = "coursesByDeptName",
            key = "#deptName"
    )
    public List<CourseResponse> getCoursesByDepartmentName(String deptName) {

        return courseRepository
                .findByDepartment_DeptName(
                        DepartmentType.valueOf(deptName)
                )
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
}