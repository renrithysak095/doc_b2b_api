package com.example.docmenuservice.model.entity;
import com.example.docmenuservice.model.dto.MainTitleDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "maintitles", schema = "public")
public class MainTitle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String mainTitle;

    @ManyToOne
    @JoinColumn(name = "department_Id")
    private Department department;

    @OneToMany(mappedBy = "mainTitle", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<SubTitle> subTitles=new ArrayList<>();

    @OneToMany(mappedBy = "mainTitle", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<FileUpload> fileUploads=new ArrayList<>();

    public MainTitle(Long id, String mainTitle){
        this.id=id;
        this.mainTitle=mainTitle;
    }

    public MainTitleDto toDto(){
        return new MainTitleDto(this.id, this.mainTitle,this.subTitles.stream().map(SubTitle::toDto).collect(Collectors.toList()),this.fileUploads.stream().map(FileUpload::toDto).collect(Collectors.toList()));
    }

}
