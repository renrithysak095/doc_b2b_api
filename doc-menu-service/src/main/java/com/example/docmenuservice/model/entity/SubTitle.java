package com.example.docmenuservice.model.entity;

import com.example.docmenuservice.model.dto.SubTitleDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "subtitle", schema = "public")
public class SubTitle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String subTitle;
    @ManyToOne
    @JoinColumn(name = "mainTitle_id")
    private MainTitle mainTitle;

    @OneToOne(mappedBy = "subTitle",cascade = CascadeType.ALL, orphanRemoval = true)
    private Content contents;

    public SubTitle(Long id, String subTitle, MainTitle mainTitle) {

        this.id = id;
        this.subTitle = subTitle;
        this.mainTitle = mainTitle;
    }

    public SubTitleDto toDto() {
        SubTitleDto dto = new SubTitleDto();
        dto.setId(this.getId());
        dto.setSubTitle(this.getSubTitle());

        if (this.contents != null) {
            dto.setContents(this.contents.toDto());
        }
        return dto;
    }

}
