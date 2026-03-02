package com.Student_CMS.Duo_project.Service;

import com.Student_CMS.Duo_project.Controller.dto.CourseCreateRequest;
import com.Student_CMS.Duo_project.Controller.dto.CourseResponse;

import java.util.List;

public interface CourseService {

    CourseResponse createCourse(CourseCreateRequest request);

    List<CourseResponse> getAllCourses();

    List<CourseResponse> getCoursesByDepartment(int departmentId);
    List<CourseResponse> getCoursesByDepartmentName(String deptName);

    void deleteCourse(Long id);
}