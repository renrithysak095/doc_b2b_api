package com.example.helpservice.service.answer;

import com.example.commonservice.config.ValidationConfig;
import com.example.helpservice.enitity.Answer;
import com.example.helpservice.exception.NotFoundExceptionClass;
import com.example.helpservice.repository.AnswerRepository;
import com.example.helpservice.request.AnswerRequest;
import com.example.helpservice.response.AnswerResponse;
import com.example.helpservice.service.question.QuestionServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnswerServiceImpl implements AnswerService {

    private final AnswerRepository answerRepository;
    private final QuestionServiceImpl questionService;

    public AnswerServiceImpl(AnswerRepository answerRepository, QuestionServiceImpl questionService) {
        this.answerRepository = answerRepository;
        this.questionService = questionService;
    }

    @Override
    public AnswerResponse answerQuestionById(Long questionId ,AnswerRequest request) {
        questionService.findQuestionById(questionId);
        return answerRepository.save(request.toEntity(questionId)).toDto();
    }

    @Override
    public Void removeAnswerById(Long id) {
        findAnswerById(id);
        answerRepository.deleteById(id);
        return null;
    }

    @Override
    public AnswerResponse updateAnswerById(Long id, AnswerRequest request) {
        Answer answer = findAnswerById(id);
        answer.setContent(request.getContent());
        return answerRepository.save(answer).toDto();
    }

    @Override
    public List<AnswerResponse> getAllAnswerByQuestionId(Long questionId) {
        questionService.findQuestionById(questionId);
        return answerRepository.findAnswerByQuestionId(questionId).stream().map(Answer::toDto)
                .collect(Collectors.toList());
    }

    public Answer findAnswerById(Long id){
        return answerRepository.findById(id).orElseThrow(() -> new NotFoundExceptionClass(ValidationConfig.NOT_FOUND_QUESTION));
    }
}
