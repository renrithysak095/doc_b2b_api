package com.example.docmenuservice.model.entity;

import com.example.docmenuservice.model.dto.DepartmentDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "departments", schema = "public")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<MainTitle> mainTitles=new ArrayList<>();

    public DepartmentDto toDto(){
        return new DepartmentDto(this.id,this.name,this.mainTitles.stream().map(MainTitle::toDto).collect(Collectors.toList()));
    }
}
