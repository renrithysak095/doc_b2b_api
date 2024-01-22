package com.example.docmenuservice.controller;

import com.example.docmenuservice.model.dto.DepartmentDto;
import com.example.docmenuservice.model.entity.Department;
import com.example.docmenuservice.model.request.DepartmentRequest;
import com.example.docmenuservice.model.response.ResponseBody;
import com.example.docmenuservice.service.interfaces.DepartmentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/v1/docs")
@Tag(name = "Department - Service")
@CrossOrigin
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/getDepartment")
    public ResponseEntity<List<DepartmentDto>> getDepartment() {
        List<DepartmentDto> departments = departmentService.getAllDepartment();

        return new ResponseEntity<>(departments, HttpStatus.OK);
    }

    @GetMapping("/getDepartmentById/{id}")
    public ResponseEntity<?> getDepartmentById(@PathVariable Long id) {
        Department department = departmentService.getDepartmentById(id);
        ResponseBody<Object> responseBody = ResponseBody.builder().
                payload(department).
                status(200).
                time(LocalDateTime.now()).
                build();
        return ResponseEntity.ok(responseBody);
    }

    @PostMapping("/addDepartment")
    public ResponseBody<DepartmentDto> addDepartment(@RequestBody DepartmentRequest departmentRequest) {
        var payload= departmentService.addDepartment(departmentRequest);
        return ResponseBody.<DepartmentDto>builder()
                .status(200)
                .payload(payload)
                .time(LocalDateTime.now())
                .build();
    }

    @PutMapping("/updateDepartment/{depId}")
    public ResponseEntity<?> updateDepartment(@PathVariable Long depId, @RequestBody DepartmentRequest departmentRequest) {
        Department department = departmentService.updateDepartment(depId, departmentRequest);
        ResponseBody<Object> responseBody = ResponseBody.builder().
                payload(department).
                status(200).
                time(LocalDateTime.now()).
                build();
        return ResponseEntity.ok(responseBody);
    }

    @DeleteMapping("/deleteDepartment/{depId}")
    public ResponseEntity<?> deleteDepartment(@PathVariable Long depId) {
        Department department = departmentService.deleteDepartment(depId);
        ResponseBody<Object> responseBody = ResponseBody.builder().
                payload(department).
                status(200).
                time(LocalDateTime.now()).
                build();
        return ResponseEntity.ok(responseBody);
    }
}


