package com.example.docmenuservice.repository;

import com.example.docmenuservice.model.entity.MainTitle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MainTitleRepository extends JpaRepository<MainTitle,Long> {
}
