package com.fluffygram.newsfeed.domain.board.entity;


import com.fluffygram.newsfeed.domain.base.Entity.BaseEntity;
import com.fluffygram.newsfeed.domain.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "board")
public class Board extends BaseEntity {
    public String getUserNickname;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;//게시물 id

    //연관관계 - N:1
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;//사용자 id(외래키)

    @Column(length = 20, nullable = false)
    private String title;//게시물 제목

    @Column(length = 1000, nullable = true)
    private String contents;//게시물 내용

    //게시글 생성
    public Board(Long id, String title, String contents, User user) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.user = user;
    }

    public Board() {
    }

    //게시물 제목 수정
    public void updateTitle(String title) {
        this.title = title;
    }
    //게시물 내용 수정
    public void updateContents(String contents) {
        this.contents = contents;
    }

}
