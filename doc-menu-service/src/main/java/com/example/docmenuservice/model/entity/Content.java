package com.example.docmenuservice.model.entity;
import com.example.docmenuservice.model.dto.ContentDto;
import jakarta.persistence.*;
import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "contents", schema = "public")
public class Content {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String content;

    @OneToOne
    @JoinColumn(name = "subTitle_Id")
    private SubTitle subTitle;

    public ContentDto toDto(){
        return new ContentDto(this.id,this.content);
    }


}
