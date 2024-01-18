package com.example.helpservice.enitity;

import com.example.helpservice.response.QuestionResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "questions", schema = "public")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String content;
    private Long view;
    private Long answer;
    @Column(nullable = false)
    private String tag;
    @Column(nullable = false)
    private LocalDateTime cred_dt;
    @Column(nullable = false)
    private LocalDateTime last_md;
    @Column(nullable = false)
    private Long userId;

    public QuestionResponse toDto(List<String> tags){
        return new QuestionResponse(tag,content.trim(),view,answer,tags,cred_dt,last_md,userId);
    }

}
