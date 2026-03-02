package com.Student_CMS.Duo_project.Controller;

import com.Student_CMS.Duo_project.Controller.dto.CourseCreateRequest;
import com.Student_CMS.Duo_project.Controller.dto.CourseResponse;
import com.Student_CMS.Duo_project.Service.CourseService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @PostMapping("/create")
    public CourseResponse createCourse(@RequestBody CourseCreateRequest request) {
        return courseService.createCourse(request);
    }

    @GetMapping("/all")
    public List<CourseResponse> getAllCourses() {
        return courseService.getAllCourses();
    }

    @GetMapping("/department/{deptId}")
    public List<CourseResponse> getByDepartment(@PathVariable int deptId) {
        return courseService.getCoursesByDepartment(deptId);
    }

    @GetMapping("/department/name/{deptName}")
    public List<CourseResponse> getByDepartmentName(
            @PathVariable String deptName) {

        return courseService.getCoursesByDepartmentName(deptName);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable Long id,
                                          HttpSession session) {

        String role = (String) session.getAttribute("ROLE");

        if (role == null) {
            return ResponseEntity.status(401).body("Not logged in");
        }

        if (!role.equals("FACULTY")) {
            return ResponseEntity.status(403).body("Only faculty can delete");
        }

        courseService.deleteCourse(id);
        return ResponseEntity.ok("Deleted successfully");
    }
}