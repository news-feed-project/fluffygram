package com.fluffygram.newsfeed.domain.like.entity;

import com.fluffygram.newsfeed.domain.base.Entity.BaseEntity;
import com.fluffygram.newsfeed.domain.base.enums.LikeStatus;
import com.fluffygram.newsfeed.domain.comment.entity.Comment;
import com.fluffygram.newsfeed.domain.user.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
@Table(name = "comment_like")
public class CommentLike extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //좋아요 ID

    //연관관계 - N:1
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;//사용자 id(외래키)

    //연관관계 - N:1
    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;//댓글 id(외래키)

    @Enumerated(EnumType.STRING)
    private LikeStatus likeStatus; //좋아요 상태

    public CommentLike() {
    }

    //댓글 좋아요
    @Builder
    public CommentLike(User user, Comment comment, LikeStatus likeStatus) {
        this.user = user;
        this.comment = comment;
        this.likeStatus = likeStatus;
    }

    //좋아요 상태 변경
    public void updateLikeStatus(LikeStatus likeStatus) {
        this.likeStatus = likeStatus;
    }


}
