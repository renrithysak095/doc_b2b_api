package com.example.helpservice.service.question;

import com.example.helpservice.request.QuestionRequest;
import com.example.helpservice.response.QuestionResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface QuestionService {
    QuestionResponse create(QuestionRequest request);
    List<QuestionResponse> getAllQuestion();
    QuestionResponse getQuestionById(Long id);
    QuestionResponse updateQuestion(Long id, QuestionRequest request);
    Void removeQuestionById(Long id);
    QuestionResponse countViewQuestion(Long id);
}
