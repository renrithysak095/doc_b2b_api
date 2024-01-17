package com.example.docmenuservice.repository;

import com.example.docmenuservice.model.entity.Department;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

}
