package com.example.helpservice.service.answer;

import com.example.helpservice.request.AnswerRequest;
import com.example.helpservice.response.AnswerResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AnswerService {
    AnswerResponse answerQuestionById(Long questionId, AnswerRequest request);
    Void removeAnswerById(Long id);
    AnswerResponse updateAnswerById(Long id, AnswerRequest request);
    List<AnswerResponse> getAllAnswerByQuestionId(Long questionId);
}
