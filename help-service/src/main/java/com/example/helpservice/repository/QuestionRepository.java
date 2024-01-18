package com.example.helpservice.repository;

import com.example.helpservice.enitity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question,Long> {
}
