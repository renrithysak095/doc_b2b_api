package com.example.docmenuservice.model.entity;

import com.example.docmenuservice.model.dto.FileUploadDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "file_upload", schema = "public")
public class FileUpload {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_url")
    private String fileUrl;

    @Column(name = "file_type")
    private String fileType;

    @Column(name = "size")
    private Long size;

    @ManyToOne
    @JoinColumn(name = "mainTitle_id")
    private MainTitle mainTitle;

    public FileUploadDto toDto(){
        return new FileUploadDto(this.id,this.fileName,this.fileUrl,this.fileType,this.size);
    }



}
