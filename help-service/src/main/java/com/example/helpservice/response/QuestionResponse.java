package com.example.helpservice.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionResponse {
    private String title;
    private String content;
    private Long view;
    private Long answer;
    private List<String> tag;
    private LocalDateTime cred_dt;
    private LocalDateTime last_md;
    private Long userId;
}
