package com.example.docmenuservice.controller;

import com.example.commonservice.response.ApiResponse;
import com.example.docmenuservice.model.dto.ContentDto;
import com.example.docmenuservice.model.request.ContentRequest;
import com.example.docmenuservice.service.interfaces.SubTitleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("api/v1/contents")
@Tag(name = "Content - Service")
public class SubTitleController {

    private final SubTitleService subTitleService;

    public SubTitleController(SubTitleService subTitleService) {
        this.subTitleService = subTitleService;
    }

    @PostMapping("/{id}")
    @Operation(summary = "added content to sub-title by id")
    public ResponseEntity<ApiResponse<ContentDto>> addNewContentToSubtitle(@PathVariable Long id,
                                                                           @RequestBody ContentRequest contentRequest){
        return new ResponseEntity<>(new ApiResponse<>(
                "added content to sub-title successfully",
                subTitleService.addNewContentToSubtitle(contentRequest,id),
                LocalDateTime.now()
        ), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "updated content by sub-title's id")
    public ResponseEntity<ApiResponse<ContentDto>> updateContentInSubtitle( @PathVariable Long id,
                                                                            @RequestBody @Valid ContentRequest contentRequest){
        return new ResponseEntity<>(new ApiResponse<>(
                "updated content by sub-title's id successfully",
                subTitleService.updateContentInSubtitle(contentRequest,id),
                LocalDateTime.now()
        ), HttpStatus.OK);
    }

    @GetMapping("/{contentId}")
    public ResponseEntity<ApiResponse<ContentDto>> getContentById(@PathVariable Long contentId){
        return new ResponseEntity<>(new ApiResponse<>(
                "get content by id successfully",
                subTitleService.getContentById(contentId),
                LocalDateTime.now()
        ), HttpStatus.OK);
    }
}
