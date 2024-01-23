package com.example.docmenuservice.controller;

import com.example.docmenuservice.model.dto.DepartmentDto;
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
    public ResponseBody<DepartmentDto> getDepartmentById(@PathVariable Long id) {
        var payload= departmentService.getDepartmentById(id);
        return ResponseBody.<DepartmentDto>builder()
                .status(200)
                .payload(payload)
                .time(LocalDateTime.now())
                .build();
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

    @PutMapping("/{id}/Department")
    public ResponseBody<DepartmentDto> updateDataById(@RequestBody DepartmentRequest departmentRequest, @PathVariable Long id){
        var payload= departmentService.updateDepartment(departmentRequest,id);
        return ResponseBody.<DepartmentDto>builder()
                .status(200)
                .payload(payload)
                .time(LocalDateTime.now())
                .build();
    }


    @DeleteMapping("/deleteDepartment/{depId}")
    public ResponseBody<DepartmentDto> deleteDepartment(@PathVariable Long depId) {
        departmentService.deleteDepartment(depId);
        return ResponseBody.<DepartmentDto>builder()
                .status(200)
                .payload(null)
                .time(LocalDateTime.now())
                .build();
    }
}


