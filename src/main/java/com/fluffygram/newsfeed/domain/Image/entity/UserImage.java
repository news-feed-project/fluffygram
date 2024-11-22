package com.fluffygram.newsfeed.domain.Image.entity;

import com.fluffygram.newsfeed.domain.base.Entity.BaseEntity;
import com.fluffygram.newsfeed.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "user_image_file")
public class UserImage extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "db_file_name")
    private String DBFileName;

    @Column(name = "file_url")
    private String fileUrl;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    private User user;

    public UserImage(User user, String originalFilename, String dbFileName, String fileUrl) {
        this.user = user;
        this.fileName = originalFilename;
        this.DBFileName = dbFileName;
        this.fileUrl = fileUrl;
    }

    public UserImage() {

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
