package com.Student_CMS.Duo_project.Service;

import com.Student_CMS.Duo_project.Controller.dto.DocumentResponse;

public interface DocumentService {

    DocumentResponse getAssignmentQuestion(Long assignmentId);

    DocumentResponse getAssignmentSubmissionFile(Long submissionId);
}


