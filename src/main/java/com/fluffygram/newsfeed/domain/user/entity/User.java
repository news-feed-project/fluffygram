package com.fluffygram.newsfeed.domain.user.entity;

import com.fluffygram.newsfeed.domain.base.Entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;


@Getter
@Entity
@Table(name = "user")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String userNickname;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String profileImage;

    public User() {

    }

    public User(String email, String encodedPassword, String userNickname, String phoneNumber, String profileImage) {
        this.email = email;
        this.password = encodedPassword;
        this.userNickname = userNickname;
        this.phoneNumber = phoneNumber;
        this.profileImage = profileImage;
    }


    public void updatePassword(String encodedPassword) {
        this.password = encodedPassword;
    }

    public void updateUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public void updatePhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void updateProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
}

