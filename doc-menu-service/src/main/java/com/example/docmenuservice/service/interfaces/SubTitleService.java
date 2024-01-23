package com.example.docmenuservice.service.interfaces;

import com.example.docmenuservice.model.dto.ContentDto;
import com.example.docmenuservice.model.request.ContentRequest;

public interface SubTitleService {

    ContentDto addNewContentToSubtitle(ContentRequest contentRequest, Long id);
    ContentDto updateContentInSubtitle(ContentRequest contentRequest,Long content_Id);

    ContentDto getContentById(Long content_Id);
}
