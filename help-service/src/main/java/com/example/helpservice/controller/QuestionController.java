package com.example.helpservice.controller;

import com.example.commonservice.response.ApiResponse;
import com.example.helpservice.request.QuestionRequest;
import com.example.helpservice.response.QuestionResponse;
import com.example.helpservice.service.question.QuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/v1/questions")
@Tag(name = "Question")
@CrossOrigin
public class QuestionController {
    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping
    @Operation(summary = "create question")
    public ResponseEntity<ApiResponse<QuestionResponse>> create(@Valid @RequestBody QuestionRequest request){
        return new ResponseEntity<>(new ApiResponse<>(
                "question created successfully",
                questionService.create(request),
                LocalDateTime.now()
        ), HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "list all questions")
    public ResponseEntity<ApiResponse<List<QuestionResponse>>> questionList(){
        return new ResponseEntity<>(new ApiResponse<>(
                "fetched all question successfully",
                questionService.getAllQuestion(),
                LocalDateTime.now()
        ), HttpStatus.OK);
    }


    @GetMapping("/{id}")
    @Operation(summary = "get question by id")
    public ResponseEntity<ApiResponse<QuestionResponse>> getQuestionById(@PathVariable Long id){
        return new ResponseEntity<>(new ApiResponse<>(
                "fetched question by id successfully",
                questionService.getQuestionById(id),
                LocalDateTime.now()
        ), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(summary = "update question by id")
    public ResponseEntity<ApiResponse<QuestionResponse>> updateQuestionById(@PathVariable Long id,
                                                                            @RequestBody @Valid QuestionRequest request){
        return new ResponseEntity<>(new ApiResponse<>(
                "updated question successfully",
                questionService.updateQuestion(id,request),
                LocalDateTime.now()
        ), HttpStatus.OK);
    }

    @PutMapping("/count/{id}")
    @Operation(summary = "increase count view question by id")
    public ResponseEntity<ApiResponse<QuestionResponse>> countViewQuestionById(@PathVariable Long id){
        return new ResponseEntity<>(new ApiResponse<>(
                "increase count view question successfully",
                questionService.countViewQuestion(id),
                LocalDateTime.now()
        ), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "delete question by id")
    public ResponseEntity<ApiResponse<Void>> deleteQuestionById(@PathVariable Long id){
        return new ResponseEntity<>(new ApiResponse<>(
                "question delete successfully",
                questionService.removeQuestionById(id),
                LocalDateTime.now()
        ), HttpStatus.OK);
    }


}
