package com.example.authservice.controller;

import com.example.authservice.response.FileResponse;
import com.example.authservice.service.image.ImageService;
import com.example.commonservice.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

@RestController
@RequestMapping("api/v1/image")
@Tag(name = "Image Upload")
@CrossOrigin
public class FileController {
    private final ImageService imageService;

    public FileController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping(value = "/{userId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<FileResponse>> saveFile(@RequestParam(required = false) MultipartFile file,
                                                              HttpServletRequest request,
                                                              @PathVariable Long userId) throws Exception {
        return new ResponseEntity<>(new ApiResponse<>(
                "image upload to user information successfully",
                imageService.saveImage(userId,file,request),
                LocalDateTime.now()
        ), HttpStatus.OK);
    }

    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<FileResponse>> uploadImage(@RequestParam(required = false) MultipartFile file,
                                                              HttpServletRequest request) throws Exception {
        return new ResponseEntity<>(new ApiResponse<>(
                "image upload successfully",
                imageService.uploadImage(file,request),
                LocalDateTime.now()
        ), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<ByteArrayResource> getFileByFileName(@RequestParam String fileName) throws IOException {
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(imageService.getImage(fileName));
    }

}
