package com.example.docmenuservice.controller;

import com.example.docmenuservice.exception.InternalServerErrorException;
import com.example.docmenuservice.exception.NotFoundExceptionClass;
import com.example.docmenuservice.model.dto.FileUploadDto;
import com.example.docmenuservice.model.dto.MainTitleDto;
import com.example.docmenuservice.model.dto.SubTitleDto;
import com.example.docmenuservice.model.request.FileUploadRequest;
import com.example.docmenuservice.model.request.MainTitleRequest;
import com.example.docmenuservice.model.request.SubTitleRequest;
import com.example.docmenuservice.model.response.ResponseBody;
import com.example.docmenuservice.service.interfaces.FileUploadService;
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
@RequestMapping("api/v1/docs")
@Tag(name = "MainTitle- Service")
@CrossOrigin
public class MainTitleController {

    private final MainTitleService mainTitleService;
    private final FileUploadService fileUploadService;


    public MainTitleController(MainTitleService mainTitleService, FileUploadService fileUploadService) {
        this.mainTitleService = mainTitleService;
        this.fileUploadService = fileUploadService;
    }

    @PostMapping("/saveMainTitle")
    public ResponseBody<MainTitleDto> saveMainTitle(@RequestBody MainTitleRequest mainTitleRequest) {
        var payload = mainTitleService.addNewMainTitle(mainTitleRequest);
        return ResponseBody.<MainTitleDto>builder()
                .status(200)
                .payload(payload)
                .time(LocalDateTime.now())
                .build();
    }

    @GetMapping("/allMainTitle")
    public ResponseEntity<List<MainTitleDto>> getAllMainTitle() {
        List<MainTitleDto> mainTitleDtos = mainTitleService.geAllMainTitle();
        return new ResponseEntity<>(mainTitleDtos, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseBody<MainTitleDto> deleteData(@PathVariable("id") Long id) {
        mainTitleService.deleteMainTitleById(id);
        return ResponseBody.<MainTitleDto>builder()
                .status(200)
                .payload(null)
                .time(LocalDateTime.now())
                .build();
    }

    @PutMapping("/{id}")
    public ResponseBody<MainTitleDto> updateData(@PathVariable("id") Long id, @RequestBody MainTitleRequest mainTitleRequest) {
        var payload = mainTitleService.updateMainTitleById(mainTitleRequest, id);
        return ResponseBody.<MainTitleDto>builder()
                .status(200)
                .payload(payload)
                .time(LocalDateTime.now())
                .build();
    }

    @GetMapping("/{id}")
    public ResponseBody<MainTitleDto> getMainTitleById(@PathVariable("id") Long id) {
        var paload = mainTitleService.getMainTitleById(id);
        return ResponseBody.<MainTitleDto>builder()
                .status(200)
                .payload(paload)
                .time(LocalDateTime.now())
                .build();
    }

    @PostMapping("/{id}/addSubtitleToMaintitle")
    public ResponseBody<SubTitleDto> addNewSubTitleToMainTitle(@RequestBody SubTitleRequest subTitleRequest, @PathVariable Long id) {

        var payload = mainTitleService.addNewSubTitleTOMainTitle(subTitleRequest, id);
        return ResponseBody.<SubTitleDto>builder()
                .status(200)
                .payload(payload)
                .time(LocalDateTime.now())
                .build();
    }

    @PutMapping("/{id}/Subtitle")
    public ResponseBody<SubTitleDto> updateSubTitleToMainTitle(@RequestBody SubTitleRequest subTitleRequest, @PathVariable Long id) {
        var payload = mainTitleService.updateSubTitleInMainTitleId(subTitleRequest, id);
        return ResponseBody.<SubTitleDto>builder()
                .status(200)
                .payload(payload)
                .time(LocalDateTime.now())
                .build();
    }

    @DeleteMapping("/{sub_Id}/SubTitle")
    public ResponseBody<SubTitleDto> deleteSubTitleById(@PathVariable Long sub_Id) {
        mainTitleService.deleteSubTitleFromMainTitleById(sub_Id);
        return ResponseBody.<SubTitleDto>builder()
                .status(200)
                .payload(null)
                .time(LocalDateTime.now())
                .build();
    }

    @GetMapping("{id}/allSubtitles")
    public ResponseEntity<List<SubTitleDto>> getAllSubtitleByMaintitle(@PathVariable("id") Long id) {
        List<SubTitleDto> subTitleDtos = mainTitleService.getAllSubTitleByMainTitleId(id);
        return new ResponseEntity<>(subTitleDtos, HttpStatus.OK);
    }

    @PostMapping(value = "/file_upload/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
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

    @DeleteMapping("/{file_Id}/fileUploads")
    public ResponseBody<FileUploadDto> deleteFileUploadsByMainTitleId(@PathVariable Long file_Id) {
        mainTitleService.deleteFileUploadsByMainTitleId(file_Id);
        return ResponseBody.<FileUploadDto>builder()
                .status(200)
                .payload(null)
                .time(LocalDateTime.now())
                .build();
    }

    @GetMapping("/{sub_Id}/SubTitle")
    public ResponseBody<SubTitleDto> getSubTitleById(@PathVariable("sub_Id") Long id) {
        var paload = mainTitleService.getSubTitleById(id);
        return ResponseBody.<SubTitleDto>builder()
                .status(200)
                .payload(paload)
                .time(LocalDateTime.now())
                .build();


    }
}
