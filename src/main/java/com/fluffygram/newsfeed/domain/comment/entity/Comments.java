package com.fluffygram.newsfeed.domain.comment.entity;

import com.fluffygram.newsfeed.domain.base.Entity.BaseEntity;
import com.fluffygram.newsfeed.domain.board.entity.Board;
import com.fluffygram.newsfeed.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name= "comments")
public class Comments extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String comment;

    //연관관계 - N:1
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;  //사용자 id(외래키)

    //연관관계 - N:1
    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;    //게시물 id(외래키)

    public Comments(String comment, User user, Board board) {
        this.comment = comment;
        this.user = user;
        this.board = board;
    }

    public Comments() {

    }
}
