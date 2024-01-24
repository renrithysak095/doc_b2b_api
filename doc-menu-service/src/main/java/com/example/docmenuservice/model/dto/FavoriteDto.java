package com.example.docmenuservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FavoriteDto {
    private Long id;
    private List<SubTitleDto> subTitles = new ArrayList<>();
}
