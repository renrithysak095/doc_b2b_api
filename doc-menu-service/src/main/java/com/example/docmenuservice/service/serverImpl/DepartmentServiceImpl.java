package com.example.docmenuservice.service.serverImpl;

import com.example.docmenuservice.exception.NotFoundExceptionClass;
import com.example.docmenuservice.exception.NullExceptionClass;
import com.example.docmenuservice.model.dto.DepartmentDto;
import com.example.docmenuservice.model.dto.MainTitleDto;
import com.example.docmenuservice.model.entity.Department;
import com.example.docmenuservice.model.entity.MainTitle;
import com.example.docmenuservice.model.request.DepartmentRequest;
import com.example.docmenuservice.repository.DepartmentRepository;
import com.example.docmenuservice.repository.MainTitleRepository;
import com.example.docmenuservice.service.interfaces.DepartmentService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    public DepartmentDto getDepartmentById(Long id) {
        Department department = departmentRepository.findById(id).orElseThrow(() -> new NotFoundExceptionClass("Id not found"));
        return departmentRepository.findById(id).get().toDto();
    }

    @Override
    public DepartmentDto addDepartment(DepartmentRequest departmentRequest) {

        if (departmentRequest == null || departmentRequest.getName().isBlank() || departmentRequest.getName().isEmpty()) {
            throw new NullExceptionClass("Department name cannot be null ");
        } else {
            // Create a new Company entity
            Department newDepartment = new Department();
            newDepartment.setName(departmentRequest.getName());

            // Retrieve owners based on the provided owner IDs
            List<MainTitle> mainTitles = mainTitleRepository.findAllById(departmentRequest.getMainTitle_id());
            newDepartment.setMainTitles(mainTitles);

            // Update the reference to the Company in each Owner
            for (MainTitle mainTitle1 : mainTitles) {
                mainTitle1.setDepartment(newDepartment);
            }

            // Save the new company entity to the database
            Department savedDepartment = departmentRepository.save(newDepartment);

            // Convert and return the DTO
            return savedDepartment.toDto();
        }

    }

    @Override
    public DepartmentDto updateDepartment( DepartmentRequest departmentRequest,Long id) {
        if (departmentRequest==null || departmentRequest.getName().isBlank() || departmentRequest.getName().isEmpty()){
            throw new NullExceptionClass("department name can not be null ");
        }else{
            Optional<Department> optional = departmentRepository.findById(id);
            if (optional.isPresent()) {
                Department department = optional.get();
                department.setName(departmentRequest.getName());
                List<MainTitle> mainTitles = mainTitleRepository.findAllById(departmentRequest.getMainTitle_id());
                department.setMainTitles(mainTitles);
                for (MainTitle mainTitle : mainTitles) {
                    mainTitle.setDepartment(department);
                }

                return departmentRepository.save(department).toDto();

            } else {
                return null;
            }
        }
    }

    @Override
    public Void deleteDepartment(Long depId) {
        Department department = departmentRepository.findById(depId).orElseThrow(()->new NotFoundExceptionClass("id not found"));
        departmentRepository.deleteById(department.getId());
        return null;
    }
}
