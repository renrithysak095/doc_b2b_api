package com.example.docmenuservice.service.interfaces;

import com.example.docmenuservice.model.entity.Department;
import com.example.docmenuservice.model.request.DepartmentRequest;

import java.util.List;

public interface DepartmentService {

    List<Department> getAllDepartment();

    Department getDepartmentById(Long id);

    Department addDepartment(DepartmentRequest departmentRequest);

    Department updateDepartment(Long depId, DepartmentRequest departmentRequest);

    Department deleteDepartment(Long depId);
}
