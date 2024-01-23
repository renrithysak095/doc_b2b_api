package com.example.docmenuservice.model.request;

import com.example.docmenuservice.model.entity.MainTitle;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MainTitleRequest {

    private String mainTitle;

    public MainTitle toEntity(){
        return  new MainTitle(null,mainTitle);
    }
}
