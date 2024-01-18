package com.example.docmenuservice.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "subtitles")
public class SubTitle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    @ManyToOne
    @JoinColumn(name = "main_title_id")
    private MainTitle mainTitle;

    @OneToMany(mappedBy = "subTitle")
    private List<Content> contents;
}
