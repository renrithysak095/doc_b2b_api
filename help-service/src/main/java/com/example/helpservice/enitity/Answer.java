package com.example.helpservice.enitity;

import com.example.helpservice.response.AnswerResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "answers", schema = "public")
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long questionId;
    @Column(nullable = false)
    private String content;
    public AnswerResponse toDto(){
        return new AnswerResponse(questionId,content.trim());
    }
}
