package com.example.docmenuservice.model.request;

import com.example.docmenuservice.model.entity.Department;
import com.example.docmenuservice.model.entity.MainTitle;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentRequest {
    private String name;
    private List<Long> mainTitle_id;
    public Department toEntity(List<MainTitle> mainTitles){
        return new Department(null,this.name,mainTitles);
    }
}
