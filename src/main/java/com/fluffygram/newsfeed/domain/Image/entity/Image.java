package com.fluffygram.newsfeed.domain.Image.entity;

import com.fluffygram.newsfeed.domain.base.enums.ImageStatus;
import com.fluffygram.newsfeed.domain.base.Entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "image_file")
public class Image extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "db_file_name")
    private String DBFileName;

    @Column(name = "file_url")
    private String fileUrl;

    @Enumerated(EnumType.STRING)
    private ImageStatus status;

    private Long statusId;

    public Image() {

    }

    public Image(String originalFilename, String dBfileName, String userImageStorage, Long statusId, ImageStatus status) {
        this.fileName = originalFilename;
        this.DBFileName = dBfileName;
        this.fileUrl = userImageStorage;
        this.statusId = statusId;
        this.status = status;
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
