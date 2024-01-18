package com.example.authservice.enitity;

import com.example.authservice.response.FileResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "files", schema = "public")
public class FileImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fileName;
    private String fileType;
    private Long size;

    public FileResponse toDto(){
        return new FileResponse(fileName,fileType,size);
    }
}
