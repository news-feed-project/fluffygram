package com.fluffygram.newsfeed.domain.Image.entity;

import com.fluffygram.newsfeed.domain.base.Entity.BaseEntity;
import com.fluffygram.newsfeed.domain.board.entity.Board;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "board_image_file")
public class BoardImage extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "db_file_name")
    private String DBFileName;

    @Column(name = "file_url")
    private String fileUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    public BoardImage(Board board, String originalFilename, String dbFileName, String fileUrl) {
        this.board = board;
        this.fileName = originalFilename;
        this.DBFileName = dbFileName;
        this.fileUrl = fileUrl;
    }

    public BoardImage() {

    }

    public void updateFileName(String fileName) {
        this.fileName = fileName;
    }

    public void updateDBFileName(String DBFileName) {
        this.DBFileName = DBFileName;
    }

    public void updateFileUrl(String fileUrl){
        this.fileUrl = fileUrl;
    }
}
