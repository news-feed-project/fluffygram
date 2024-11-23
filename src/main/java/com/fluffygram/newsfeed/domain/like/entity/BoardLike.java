package com.fluffygram.newsfeed.domain.like.entity;

import com.fluffygram.newsfeed.domain.base.Entity.BaseEntity;
import com.fluffygram.newsfeed.domain.board.entity.Board;
import com.fluffygram.newsfeed.domain.user.entity.User;
import com.fluffygram.newsfeed.domain.user.entity.UserStatus;
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
import org.hibernate.annotations.ColumnDefault;

@Getter
@Entity
@Table(name = "board_like")
public class BoardLike extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //좋아요 ID

    //연관관계 - N:1
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;//사용자 id(외래키)

    //연관관계 - N:1
    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;//게시물 id(외래키)

//    @ColumnDefault("1")//default 값 1 : 활성화
    @Enumerated(EnumType.STRING)
    private LikeStatus likeStatus; //좋아요 상태


    public BoardLike() {
    }

    //게시물 좋아요
    @Builder
    public BoardLike(User user, Board board, LikeStatus likeStatus) {
        this.user = user;
        this.board = board;
        this.likeStatus = likeStatus;
    }

    //게시물 상태 변경
    public void updateLikeStatus(LikeStatus likeStatus) {
        this.likeStatus = likeStatus;
    }


}
