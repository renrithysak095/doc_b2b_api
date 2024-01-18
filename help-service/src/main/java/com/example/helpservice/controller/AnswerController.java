package com.example.helpservice.controller;

import com.example.commonservice.response.ApiResponse;
import com.example.helpservice.request.AnswerRequest;
import com.example.helpservice.response.AnswerResponse;
import com.example.helpservice.service.answer.AnswerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/v1/answers")
@Tag(name = "Answer")
@CrossOrigin
public class AnswerController {
    private final AnswerService answerService;

    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @GetMapping("/{questionId}")
    @Operation(summary = "list all answers by question id")
    public ResponseEntity<ApiResponse<List<AnswerResponse>>> listAllAnswer(@PathVariable Long questionId){
        return new ResponseEntity<>(new ApiResponse<>(
                "fetched all answer by question id successfully",
                answerService.getAllAnswerByQuestionId(questionId),
                LocalDateTime.now()
        ), HttpStatus.OK);
    }

    @PostMapping("/{questionId}")
    @Operation(summary = "answer question")
    public ResponseEntity<ApiResponse<AnswerResponse>> answerQuestionById(@PathVariable Long questionId,
                                                                          @RequestBody @Valid AnswerRequest request){
        return new ResponseEntity<>(new ApiResponse<>(
                "answer question by id successfully",
                answerService.answerQuestionById(questionId,request),
                LocalDateTime.now()
        ), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "update answer")
    public ResponseEntity<ApiResponse<AnswerResponse>> updateAnswerById(@PathVariable Long id,
                                                                          @RequestBody @Valid AnswerRequest request){
        return new ResponseEntity<>(new ApiResponse<>(
                "update answer by id successfully",
                answerService.updateAnswerById(id,request),
                LocalDateTime.now()
        ), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "delete answer by id")
    public ResponseEntity<ApiResponse<Void>> deleteAnswerById(@PathVariable Long id){
        return new ResponseEntity<>(new ApiResponse<>(
                "answer delete successfully",
                answerService.removeAnswerById(id),
                LocalDateTime.now()
        ), HttpStatus.OK);
    }

}
