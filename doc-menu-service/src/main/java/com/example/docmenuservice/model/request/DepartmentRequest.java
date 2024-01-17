package com.example.docmenuservice.model.request;

import com.example.docmenuservice.model.entity.Department;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentRequest {
    private String name;
    public Department toEntity(){
        return new Department(null,this.name);
    }
}
