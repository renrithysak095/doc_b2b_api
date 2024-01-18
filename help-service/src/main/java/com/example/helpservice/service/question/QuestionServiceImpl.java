package com.example.helpservice.service.question;

import com.example.commonservice.config.ValidationConfig;
import com.example.commonservice.response.ApiResponse;
import com.example.commonservice.response.AuthResponse;
import com.example.helpservice.enitity.Question;
import com.example.helpservice.exception.NotFoundExceptionClass;
import com.example.helpservice.repository.QuestionRepository;
import com.example.helpservice.request.QuestionRequest;
import com.example.helpservice.response.QuestionResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;
    private final WebClient.Builder authClient;

    @Value("${baseURL}")
    private String baseURL;

    public QuestionServiceImpl(QuestionRepository questionRepository, WebClient.Builder authClient) {
        this.questionRepository = questionRepository;
        this.authClient = authClient;
    }

    @Override
    public QuestionResponse create(QuestionRequest request) {
        validateUser(request.getUserId());
        return questionRepository.save(request.toEntity()).toDto(request.getTag());
    }

    @Override
    public List<QuestionResponse> getAllQuestion() {
        List<QuestionResponse> questions = questionRepository.findAll().stream().map(m -> m.toDto(tags(m.getTag())))
                .collect(Collectors.toList());
        if(!questions.isEmpty()){
            return questions;
        }
        throw new NotFoundExceptionClass(ValidationConfig.EMPTY_QUESTION);
    }

    @Override
    public QuestionResponse getQuestionById(Long id) {
        return findQuestionById(id).toDto(tags(findQuestionById(id).getTag()));
    }

    @Override
    public QuestionResponse updateQuestion(Long id, QuestionRequest request) {
        Question question = findQuestionById(id);
        question.setTitle(request.getTitle());
        question.setContent(request.getContent());
        question.setTag(request.getTag().toString());
        question.setLast_md(LocalDateTime.now());
        return questionRepository.save(question).toDto(request.getTag());
    }

    @Override
    public Void removeQuestionById(Long id) {
        findQuestionById(id);
        questionRepository.deleteById(id);
        return null;
    }

    @Override
    public QuestionResponse countViewQuestion(Long id) {
        Question question = findQuestionById(id);
        question.setView(question.getView() + 1);
        return questionRepository.save(question).toDto(tags(question.getTag()));
    }

    public List<String> tags(String tag){
        List<String> rolesList = Arrays.asList(tag.replaceAll(ValidationConfig.REGEX_STRING, "").split(", "));
        return rolesList.stream()
                .map(String::toUpperCase)
                .collect(Collectors.toList());
    }
    public void validateUser(Long userId){
        ObjectMapper covertSpecificClass = new ObjectMapper();
        covertSpecificClass.registerModule(new JavaTimeModule());
        try{
            covertSpecificClass.convertValue(Objects.requireNonNull(authClient
                    .baseUrl(baseURL)
                    .build()
                    .get()
                    .uri("api/v1/users/{id}", userId)
                    .retrieve()
                    .bodyToMono(ApiResponse.class)
                    .block()), AuthResponse.class);
        }catch (Exception e){
            throw new NotFoundExceptionClass(ValidationConfig.NOTFOUND_USER);
        }
    }

    public Question findQuestionById(Long id){
        return questionRepository.findById(id)
                .orElseThrow(() -> new NotFoundExceptionClass(ValidationConfig.NOT_FOUND_QUESTION));
    }
}
