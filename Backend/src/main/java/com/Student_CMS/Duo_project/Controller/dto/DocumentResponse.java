package com.Student_CMS.Duo_project.Controller.dto;

import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentResponse {

    private String fileName;
    private String fileType;
    private byte[] data;
}
