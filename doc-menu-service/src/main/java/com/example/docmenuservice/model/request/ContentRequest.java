package com.example.docmenuservice.model.request;

import com.example.docmenuservice.model.entity.Content;
import com.example.docmenuservice.model.entity.SubTitle;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ContentRequest {

    private String content;

    public Content toEntity(SubTitle subTitle){
        return new Content(null, this.content,subTitle);
    }

}
