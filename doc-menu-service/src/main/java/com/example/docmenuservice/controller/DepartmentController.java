package com.example.docmenuservice.controller;

import com.example.commonservice.response.ApiResponse;
import com.example.docmenuservice.model.dto.DepartmentDto;
import com.example.docmenuservice.model.request.DepartmentRequest;
import com.example.docmenuservice.service.interfaces.DepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/v1/departments")
@Tag(name = "Department - Service")
@SecurityRequirement(name = "bearerAuth")
@CrossOrigin
public class DepartmentController {
    private final DepartmentService departmentService;
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }
    @GetMapping
    public ResponseEntity<ApiResponse<List<DepartmentDto>>> getDepartment() {
        return new ResponseEntity<>(new ApiResponse<>(
                "fetched all departments successfully",
                departmentService.getAllDepartment(),
                LocalDateTime.now()
        ), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DepartmentDto>>getDepartmentById(@PathVariable Long id) {
        return new ResponseEntity<>(new ApiResponse<>(
                "fetched department by id successfully",
                departmentService.getDepartmentById(id),
                LocalDateTime.now()
        ), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<DepartmentDto>> addDepartment(@RequestBody DepartmentRequest departmentRequest) {
        return new ResponseEntity<>(new ApiResponse<>(
                "department added successfully",
                departmentService.addDepartment(departmentRequest),
                LocalDateTime.now()
        ), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<DepartmentDto>> updateDataById(@RequestBody DepartmentRequest departmentRequest, @PathVariable Long id){
        return new ResponseEntity<>(new ApiResponse<>(
                "department updated successfully",
                departmentService.updateDepartment(departmentRequest,id),
                LocalDateTime.now()
        ), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "delete department")
    public ResponseEntity<ApiResponse<Void>> deleteDepartmentById(@PathVariable Long id){
        return new ResponseEntity<>(new ApiResponse<>(
                "delete department successfully",
                departmentService.deleteDepartment(id),
                LocalDateTime.now()
        ), HttpStatus.OK);
    }
}


