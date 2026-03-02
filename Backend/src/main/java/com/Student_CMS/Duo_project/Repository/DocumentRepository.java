package com.Student_CMS.Duo_project.Repository;

import com.Student_CMS.Duo_project.Entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface DocumentRepository extends JpaRepository<Document, Long> {

    List<Document> findByAssignment_Id(Long assignmentId);

    List<Document> findBySubmission_Id(Long submissionId);

    void deleteByAssignment_Id(Long assignmentId);
    void deleteBySubmission_Assignment_Id(Long assignmentId);
}
