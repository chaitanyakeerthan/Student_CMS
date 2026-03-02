package com.Student_CMS.Duo_project.Service;

import com.Student_CMS.Duo_project.Entity.Assignment;
import com.Student_CMS.Duo_project.Entity.Document;
import com.Student_CMS.Duo_project.Repository.AssignmentRepository;
import com.Student_CMS.Duo_project.Repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class AssignmentDocumentService {

    private final AssignmentRepository assignmentRepository;
    private final DocumentRepository documentRepository;

    public void uploadQuestion(Long assignmentId, MultipartFile file) throws Exception {

        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new RuntimeException("Assignment not found"));

        Document document = Document.builder()
                .fileName(file.getOriginalFilename())
                .fileType(file.getContentType())
                .data(file.getBytes())
                .assignment(assignment)
                .build();

        documentRepository.save(document);
    }
}
