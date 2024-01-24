package com.example.docmenuservice.model.request;

import com.example.docmenuservice.model.entity.Department;
import com.example.docmenuservice.model.entity.Favorite;
import com.example.docmenuservice.model.entity.MainTitle;
import com.example.docmenuservice.model.entity.SubTitle;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteRequest {
    private Long subTitleId;
    private Long userId;
    public Favorite toEntity(){
        return new Favorite(null, this.subTitleId, this.userId);
    }

}
