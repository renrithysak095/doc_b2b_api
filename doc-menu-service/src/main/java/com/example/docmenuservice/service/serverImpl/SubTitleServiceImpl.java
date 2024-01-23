package com.example.docmenuservice.service.serverImpl;

import com.example.docmenuservice.exception.NotFoundExceptionClass;
import com.example.docmenuservice.exception.NullExceptionClass;
import com.example.docmenuservice.model.dto.ContentDto;
import com.example.docmenuservice.model.entity.Content;
import com.example.docmenuservice.model.entity.SubTitle;
import com.example.docmenuservice.model.request.ContentRequest;
import com.example.docmenuservice.repository.ContentRepository;
import com.example.docmenuservice.repository.SubTitleRepository;
import com.example.docmenuservice.service.interfaces.SubTitleService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SubTitleServiceImpl implements SubTitleService {

    private final SubTitleRepository subTitleRepository;
    private final ContentRepository contentRepository;

    public SubTitleServiceImpl(SubTitleRepository subTitleRepository, ContentRepository contentRepository) {
        this.subTitleRepository = subTitleRepository;
        this.contentRepository = contentRepository;
    }

    @Override
    public ContentDto addNewContentToSubtitle(ContentRequest contentRequest, Long id) {
        SubTitle subTitles=subTitleRepository.findById(id).orElseThrow(()->new NotFoundExceptionClass("Id not Found"));
        var contentEntity=contentRequest.toEntity(subTitles);
        return contentRepository.save(contentEntity).toDto();
    }

    @Override
    public ContentDto updateContentInSubtitle(ContentRequest contentRequest,  Long content_Id) {
        Content content = contentRepository.findById(content_Id).orElseThrow(() -> new NotFoundExceptionClass("Id not found"));
        if (contentRequest == null || contentRequest.getContent() == null || contentRequest.getContent().isEmpty() || contentRequest.getContent().isBlank()) {
            throw new NullExceptionClass("Content can not be null");
        } else {
            Optional<Content> contentOptional = contentRepository.findById(content_Id);
            if (contentOptional.isPresent()) {
                Content contents = contentOptional.get();
                contents.setContent(contentRequest.getContent());
                return contentRepository.save(contents).toDto();
            } else {
                return null;
            }
        }
    }

    @Override
    public ContentDto getContentById(Long content_Id) {
        Content content= contentRepository.findById(content_Id).orElseThrow(()->new NotFoundExceptionClass("Content Id not found"));
        return contentRepository.findById(content_Id).get().toDto();
    }
}
