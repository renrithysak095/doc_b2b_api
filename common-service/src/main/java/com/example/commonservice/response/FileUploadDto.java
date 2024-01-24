package com.example.commonservice.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FileUploadDto {

    private Long id;
    private String fileName;
    private String fileUrl;
    private String fileType;
    private Long size;
}
