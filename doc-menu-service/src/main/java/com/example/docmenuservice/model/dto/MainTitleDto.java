package com.example.docmenuservice.model.dto;


import com.example.docmenuservice.model.entity.SubTitle;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MainTitleDto {

    private Long id;
    private String mainTitle;
    private List<SubTitleDto> subTitles = new ArrayList<>();
    private List<FileUploadDto>fileUploads=new ArrayList<>();

}
