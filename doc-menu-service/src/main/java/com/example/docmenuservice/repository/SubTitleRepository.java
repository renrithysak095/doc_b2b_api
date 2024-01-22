package com.example.docmenuservice.repository;

import com.example.docmenuservice.model.entity.SubTitle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubTitleRepository extends JpaRepository<SubTitle,Long> {
    List<SubTitle> findByMainTitleId(Long id);

}
