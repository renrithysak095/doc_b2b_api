package com.example.authservice.repository;

import com.example.authservice.enitity.FileImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<FileImage,Long> {

}
