package com.Student_CMS.Duo_project.Service;

import com.Student_CMS.Duo_project.Controller.dto.DocumentResponse;
import com.Student_CMS.Duo_project.Entity.Document;
import com.Student_CMS.Duo_project.Repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;

    @Override
    public DocumentResponse getAssignmentQuestion(Long assignmentId) {

        List<Document> docs =
                documentRepository.findByAssignment_Id(assignmentId);

        if (docs.isEmpty()) {
            throw new RuntimeException("Question not uploaded");
        }
        Document document = docs.get(docs.size() - 1);

        return new DocumentResponse(
                document.getFileName(),
                document.getFileType(),
                document.getData()
        );
    }
    public DocumentResponse getAssignmentSubmissionFile(Long submissionId) {

        Document doc = documentRepository
                .findBySubmission_Id(submissionId)
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("File not found"));

        return new DocumentResponse(
                doc.getFileName(),
                doc.getFileType(),
                doc.getData()
        );
    }

}
