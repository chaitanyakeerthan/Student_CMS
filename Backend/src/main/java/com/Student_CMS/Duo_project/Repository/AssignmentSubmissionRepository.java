package com.Student_CMS.Duo_project.Repository;

import com.Student_CMS.Duo_project.Entity.AssignmentSubmission;
import com.Student_CMS.Duo_project.Entity.type.SubmissionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AssignmentSubmissionRepository
        extends JpaRepository<AssignmentSubmission, Long> {

    long countByAssignment_Id(Long assignmentId);

    long countByAssignment_IdAndStatus(Long assignmentId, SubmissionStatus status);

    boolean existsByAssignment_IdAndStudent_Id(Long assignmentId, Long studentId);

    List<AssignmentSubmission> findByAssignment_Id(Long assignmentId);

    Optional<AssignmentSubmission> findByAssignment_IdAndStudent_Id(
            Long assignmentId,
            Long studentId
    );
    List<AssignmentSubmission> findByStudent_Id(Long studentId);
    void deleteByAssignment_Id(Long assignmentId);
}

