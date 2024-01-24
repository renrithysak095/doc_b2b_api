package com.example.docmenuservice.service.interfaces;

import com.example.docmenuservice.model.dto.DepartmentDto;
import com.example.docmenuservice.model.entity.Department;
import com.example.docmenuservice.model.request.DepartmentRequest;

import java.util.List;

public interface DepartmentService {

    List<DepartmentDto> getAllDepartment();

    DepartmentDto getDepartmentById(Long id);

    DepartmentDto addDepartment(DepartmentRequest departmentRequest);

    DepartmentDto updateDepartment( DepartmentRequest departmentRequest, Long id);

    Void deleteDepartment(Long depId);
}
