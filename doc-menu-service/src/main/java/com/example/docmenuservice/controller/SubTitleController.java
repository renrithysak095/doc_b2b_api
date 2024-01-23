package com.example.docmenuservice.controller;

import com.example.docmenuservice.model.dto.ContentDto;
import com.example.docmenuservice.model.request.ContentRequest;
import com.example.docmenuservice.model.response.ResponseBody;
import com.example.docmenuservice.service.interfaces.SubTitleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("api/v1/docs")
@Tag(name = "SubTitle- Service")
public class SubTitleController {

    private final SubTitleService subTitleService;

    public SubTitleController(SubTitleService subTitleService) {
        this.subTitleService = subTitleService;
    }

    @PostMapping("/{id}/content/")
    public ResponseBody<ContentDto> addNewContentToSubtitle(@PathVariable Long id, @RequestBody ContentRequest contentRequest){
        var payload= subTitleService.addNewContentToSubtitle(contentRequest,id);
        return ResponseBody.<ContentDto>builder()
                .status(200)
                .payload(payload)
                .time(LocalDateTime.now())
                .build();
    }

    @PutMapping("/{content_Id}/content")
    public ResponseBody<ContentDto> updateContentInSubtitle( @PathVariable("content_Id") Long content_Id, ContentRequest contentRequest){
        var payload= subTitleService.updateContentInSubtitle(contentRequest,content_Id);
        return ResponseBody.<ContentDto>builder()
                .status(200)
                .payload(payload)
                .time(LocalDateTime.now())
                .build();
    }

    @GetMapping("/{content_Id}/content")
    public ResponseBody<ContentDto> getContentById(@PathVariable("content_Id") Long content_Id){
        var payload= subTitleService.getContentById(content_Id);
        return ResponseBody.<ContentDto>builder()
                .status(200)
                .payload(payload)
                .time(LocalDateTime.now())
                .build();
    }
}
