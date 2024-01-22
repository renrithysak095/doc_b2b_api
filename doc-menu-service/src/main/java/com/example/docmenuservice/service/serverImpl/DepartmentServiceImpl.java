package com.example.docmenuservice.service.serverImpl;

import com.example.docmenuservice.exception.InternalServerErrorException;
import com.example.docmenuservice.exception.NotFoundExceptionClass;
import com.example.docmenuservice.model.dto.DepartmentDto;
import com.example.docmenuservice.model.dto.MainTitleDto;
import com.example.docmenuservice.model.entity.Department;
import com.example.docmenuservice.model.entity.MainTitle;
import com.example.docmenuservice.model.request.DepartmentRequest;
import com.example.docmenuservice.repository.DepartmentRepository;
import com.example.docmenuservice.repository.MainTitleRepository;
import com.example.docmenuservice.service.interfaces.DepartmentService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final MainTitleRepository mainTitleRepository;


    public DepartmentServiceImpl(DepartmentRepository departmentRepository, MainTitleRepository mainTitleRepository) {
        this.departmentRepository = departmentRepository;
        this.mainTitleRepository = mainTitleRepository;
    }

    @Override
    public List<DepartmentDto> getAllDepartment() {
        List<Department> departments = departmentRepository.findAll();
        List<DepartmentDto> departmentDtos = new ArrayList<>();

        for (Department department : departments) {
            DepartmentDto departmentDto = department.toDto();

            // Convert the list of MainTitle entities to MainTitleDto
            List<MainTitleDto> mainTitleDtos = department.getMainTitles()
                    .stream()
                    .map(MainTitle::toDto)
                    .collect(Collectors.toList());

            departmentDto.setMainTitles(mainTitleDtos);
            departmentDtos.add(departmentDto);
        }

        return departmentDtos;
    }

    @Override
    public Department getDepartmentById(Long id) {
        return departmentRepository.findById(id).orElseThrow(() -> new NotFoundExceptionClass("Department is not found"));
    }

    @Override
    public DepartmentDto addDepartment(DepartmentRequest departmentRequest) {
        if (departmentRequest == null || departmentRequest.getName().isEmpty() || departmentRequest.getName().isBlank()) {
            throw new InternalServerErrorException("department can not be null or empty");
        }
        List<MainTitle>mainTitles=mainTitleRepository.findAllById(departmentRequest.getMainTitle_id());
      Department  departEntity = departmentRequest.toEntity(mainTitles);
            departEntity.setMainTitles(mainTitles);
        return departmentRepository.save(departEntity).toDto();
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
