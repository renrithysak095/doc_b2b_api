package com.example.docmenuservice.model.request;

import com.example.docmenuservice.model.entity.MainTitle;
import com.example.docmenuservice.model.entity.SubTitle;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SubTitleRequest {

    private String subTitle;
    public SubTitle toEntity(MainTitle mainTitle){
        return new SubTitle(null, this.subTitle,mainTitle);
    }
}
