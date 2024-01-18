package com.example.helpservice.repository;

import com.example.helpservice.enitity.Answer;
import com.example.helpservice.response.AnswerResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer,Long> {
    List<Answer> findAnswerByQuestionId(Long questionId);
}
