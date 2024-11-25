package com.fluffygram.newsfeed.domain.board.entity;


import com.fluffygram.newsfeed.domain.base.Entity.BaseEntity;
import com.fluffygram.newsfeed.domain.comment.entity.Comment;
import com.fluffygram.newsfeed.domain.like.entity.BoardLike;
import com.fluffygram.newsfeed.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "board")
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;//게시물 id

    @Column(length = 20, nullable = false)
    private String title;//게시물 제목

    @Column(length = 1000)
    private String contents;//게시물 내용

    //연관관계 - N:1
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;//사용자 id(외래키)

    //양방향관계 - 1:N
    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();

    //양방향관계 - 1:N
    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<BoardLike> boardLikeList = new ArrayList<>();

    //게시글 생성
    public Board(String title, String contents, User user) {
        this.title = title;
        this.contents = contents;
        this.user = user;
    }

    public Board() {
    }

    //게시물 제목 및 내용 수정
    public Board updateBoard(String title, String contents) {
        if(title != null){
            this.title = title;
        }

        if(contents != null){
            this.contents = contents;
        }

        return this;
    }

    public void addBoardLike(BoardLike boardLike) {
        boardLikeList.add(boardLike);
    }
}
