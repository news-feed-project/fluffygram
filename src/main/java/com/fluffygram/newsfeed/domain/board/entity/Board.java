package com.fluffygram.newsfeed.domain.board.entity;


import com.fluffygram.newsfeed.domain.base.Entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
@Entity
@Table(name = "board")
public class Board extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private Long id;//게시물 id

    @NotNull
    @Column(length = 20, nullable = false)
    private String title;//게시물 제목

    @Column(length = 1000, nullable = true)
    private String contents;//게시물 내용

}
