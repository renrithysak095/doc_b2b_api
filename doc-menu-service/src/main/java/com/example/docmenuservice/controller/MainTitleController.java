package com.example.docmenuservice.controller;

import com.example.commonservice.response.ApiResponse;
import com.example.docmenuservice.exception.InternalServerErrorException;
import com.example.docmenuservice.exception.NotFoundExceptionClass;
import com.example.docmenuservice.model.dto.MainTitleDto;
import com.example.docmenuservice.model.dto.SubTitleDto;
import com.example.docmenuservice.model.request.FileUploadRequest;
import com.example.docmenuservice.model.request.MainTitleRequest;
import com.example.docmenuservice.model.request.SubTitleRequest;
import com.example.docmenuservice.service.interfaces.MainTitleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/v1/main-titles")
@Tag(name = "Main Title - Service")
@CrossOrigin
public class MainTitleController {
    private final MainTitleService mainTitleService;

    public MainTitleController(MainTitleService mainTitleService) {
        this.mainTitleService = mainTitleService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<MainTitleDto>> saveMainTitle(@RequestBody MainTitleRequest mainTitleRequest) {
        return new ResponseEntity<>(new ApiResponse<>(
                "department added successfully",
                mainTitleService.addNewMainTitle(mainTitleRequest),
                LocalDateTime.now()
        ), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<MainTitleDto>>> getAllMainTitle() {
        return new ResponseEntity<>(new ApiResponse<>(
                "fetched all main titles successfully",
                mainTitleService.geAllMainTitle(),
                LocalDateTime.now()
        ), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "delete main title")
    public ResponseEntity<ApiResponse<Void>> deleteData(@PathVariable Long id){
        return new ResponseEntity<>(new ApiResponse<>(
                "delete main title successfully",
                mainTitleService.deleteMainTitleById(id),
                LocalDateTime.now()
        ), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<MainTitleDto>> updateData(@PathVariable("id") Long id, @RequestBody MainTitleRequest mainTitleRequest) {
        return new ResponseEntity<>(new ApiResponse<>(
                "main titles updated successfully",
                mainTitleService.updateMainTitleById(mainTitleRequest, id),
                LocalDateTime.now()
        ), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MainTitleDto>> getMainTitleById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(new ApiResponse<>(
                "get main titles by id successfully",
                mainTitleService.getMainTitleById(id),
                LocalDateTime.now()
        ), HttpStatus.OK);
    }

    @PostMapping("/sub-title/{id}")
    public ResponseEntity<ApiResponse<SubTitleDto>> addNewSubTitleToMainTitle(@RequestBody SubTitleRequest subTitleRequest, @PathVariable Long id) {
        return new ResponseEntity<>(new ApiResponse<>(
                "added sub-title to main-title successfully",
                mainTitleService.addNewSubTitleTOMainTitle(subTitleRequest, id),
                LocalDateTime.now()
        ), HttpStatus.CREATED);
    }

    @PutMapping("/sub-title/{id}")
    public ResponseEntity<ApiResponse<SubTitleDto>> updateSubTitleToMainTitle(@RequestBody SubTitleRequest subTitleRequest, @PathVariable Long id) {
        return new ResponseEntity<>(new ApiResponse<>(
                "updated sub-title to main-title successfully",
                mainTitleService.updateSubTitleInMainTitleId(subTitleRequest, id),
                LocalDateTime.now()
        ), HttpStatus.OK);
    }

    @DeleteMapping("/sub-title/{subId}")
    @Operation(summary = "delete department")
    public ResponseEntity<ApiResponse<Void>> deleteSubTitleById(@PathVariable Long subId) {
        return new ResponseEntity<>(new ApiResponse<>(
                "delete sub-title by id successfully",
                mainTitleService.deleteSubTitleFromMainTitleById(subId),
                LocalDateTime.now()
        ), HttpStatus.OK);
    }


    @GetMapping("/sub-titles/{id}")
    @Operation(summary = "get all sub-titles by main-title's id")
    public ResponseEntity<ApiResponse<List<SubTitleDto>>> getAllSubtitleByMainTitleId(@PathVariable Long id) {
        return new ResponseEntity<>(new ApiResponse<>(
                "get all sub-titles by main-title id successfully",
                mainTitleService.getAllSubTitleByMainTitleId(id),
                LocalDateTime.now()
        ), HttpStatus.OK);
    }

    @PostMapping(value = "/file-upload/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> saveFile(@RequestParam(required = false) MultipartFile file,
                                      HttpServletRequest request, @PathVariable Long id) throws IOException {
        if (file != null) {
            FileUploadRequest fileUploadRequest = mainTitleService.saveFile(file, request, id);
            return ResponseEntity.status(200).body(fileUploadRequest);
        } else {
            throw new InternalServerErrorException("No File Upload");
        }
    }


    @PostMapping(value = "/uploadMultipleFiles", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "upload multiple file")
    public ResponseEntity<?> saveMultiFile(@RequestParam(required = false) List<MultipartFile> files,
                                           HttpServletRequest request, Long id) throws IOException {
        if (files != null) {
            return ResponseEntity.status(200).body(mainTitleService.saveMultipleFile(files, request, id));
        }
        throw new NotFoundExceptionClass("No filename to upload");
    }


    @GetMapping("/download/{fileName}")
    @Operation(summary = "download file")
    public ResponseEntity<?> downloadFile(@PathVariable String fileName) throws IOException {
        if (fileName.isBlank()) {
            throw new NullPointerException("No filename to download");
        }
        String filePath = "src/main/resources/storage/" + fileName;
        Path path = Paths.get(filePath);
        if (!Files.exists(path)) {
            throw new NotFoundExceptionClass("File Not Found");
        }
        byte[] file = mainTitleService.getFileContent(fileName);
        ByteArrayResource resource = new ByteArrayResource(file);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
        MediaType mediaType = MediaType.APPLICATION_OCTET_STREAM;
        headers.setContentType(mediaType);
        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
    }

    @DeleteMapping("/fileUploads/{fileId}")
    public ResponseEntity<ApiResponse<Void>> deleteFileUploadsByMainTitleId(@PathVariable Long fileId) {
        return new ResponseEntity<>(new ApiResponse<>(
                "delete files by main-title id successfully",
                mainTitleService.deleteFileUploadsByMainTitleId(fileId),
                LocalDateTime.now()
        ), HttpStatus.OK);
    }

    @GetMapping("/sub-title/{subId}")
    public ResponseEntity<ApiResponse<SubTitleDto>> getSubTitleById(@PathVariable Long subId) {
        return new ResponseEntity<>(new ApiResponse<>(
                "get sub-title by id successfully",
                mainTitleService.getSubTitleById(subId),
                LocalDateTime.now()
        ), HttpStatus.OK);


    }
}
