package com.example.docmenuservice.service.serverImpl;

import com.example.docmenuservice.exception.NotFoundExceptionClass;
import com.example.docmenuservice.model.entity.Department;
import com.example.docmenuservice.model.request.DepartmentRequest;
import com.example.docmenuservice.repository.DepartmentRepository;
import com.example.docmenuservice.service.interfaces.DepartmentService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public List<Department> getAllDepartment() {
        return departmentRepository.findAll();
    }

    @Override
    public Department getDepartmentById(Long id) {
        return departmentRepository.findById(id).orElseThrow(() -> new NotFoundExceptionClass("Department is not found"));
    }

    @Override
    public Department addDepartment(DepartmentRequest departmentRequest) {
        return departmentRepository.save(departmentRequest.toEntity());
    }

    @Override
    public Department updateDepartment(Long depId, DepartmentRequest departmentRequest) {
        Department existingDepartment = departmentRepository.findById(depId)
                        .orElseThrow(()->new EntityNotFoundException("Department not found with id: " + depId));
        existingDepartment.setName(departmentRequest.getName());
        System.out.println(existingDepartment);
        return departmentRepository.save(existingDepartment);
    }

    @Override
    public Department deleteDepartment(Long depId) {
        Department departmentToDelete = departmentRepository.findById(depId)
                .orElseThrow(()->new EntityNotFoundException("Could not find id: " + depId));
        departmentRepository.deleteById(depId);
        return departmentToDelete;
    }
}
