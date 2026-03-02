package com.Student_CMS.Duo_project.Controller.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentUploadResponse {

    private Long documentId;
    private String fileName;
    private String message;
}
