package com.example.docmenuservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SubTitleDto {

    private Long id;
    private String subTitle;
    private ContentDto contents;
}
