package com.Student_CMS.Duo_project.Service;

import com.Student_CMS.Duo_project.Controller.dto.AssignmentStatsResponse;
import com.Student_CMS.Duo_project.Controller.dto.AssignmentSubmissionDetailResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AssignmentSubmissionService {

    void submitAnswer(
            Long assignmentId,
            Long studentId,
            MultipartFile file
    ) throws Exception;
    AssignmentStatsResponse getAssignmentStats(Long assignmentId);
    List<AssignmentSubmissionDetailResponse>
    getSubmissionDetailsForAssignment(Long assignmentId);
}
