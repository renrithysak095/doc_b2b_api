package com.example.docmenuservice.model.request;

import com.example.docmenuservice.model.entity.FileUpload;
import com.example.docmenuservice.model.entity.MainTitle;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FileUploadRequest {

    private String fileName;
    private String fileUrl;
    private String fileType;
    private Long size;

    public FileUpload toEntity(MainTitle mainTitle){
        return new FileUpload(null,this.fileName,this.fileUrl,this.fileType,this.size,mainTitle);
    }
}
