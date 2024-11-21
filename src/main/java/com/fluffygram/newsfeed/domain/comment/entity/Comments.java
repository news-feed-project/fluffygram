package com.fluffygram.newsfeed.domain.comment.entity;

import com.fluffygram.newsfeed.domain.base.Entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name= "comments")
public class Comments extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long boardId;
    private Long userId;
    private String comment;
}
