package com.example.helpservice.request;

import com.example.helpservice.enitity.Answer;
import com.example.helpservice.enitity.Question;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerRequest {
    private String content;
    public Answer toEntity(Long questionId){
        return new Answer(null,questionId,content.trim());
    }
}
