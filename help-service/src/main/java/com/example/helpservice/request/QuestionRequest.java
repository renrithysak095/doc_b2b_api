package com.example.helpservice.request;

import com.example.commonservice.config.ValidationConfig;
import com.example.helpservice.enitity.Question;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionRequest {
    @NotBlank(message = ValidationConfig.POST_TITLE_REQUIRE)
    @NotEmpty(message = ValidationConfig.POST_TITLE_EMPTY)
    private String title;
    @NotBlank(message = ValidationConfig.POST_TITLE_REQUIRE)
    @NotEmpty(message = ValidationConfig.POST_TITLE_EMPTY)
    private String content;
    @NotEmpty(message = ValidationConfig.TAG_NOT_EMPTY)
    private List<String> tag;
    private LocalDateTime cred_dt;
    private LocalDateTime last_md;
    private Long userId;

    public Question toEntity(){
        return new Question(null,title,content.trim(),0L,0L,tag.toString(),LocalDateTime.now(),LocalDateTime.now(),userId);
    }
}
