package com.fluffygram.newsfeed.domain.user.entity;

import com.fluffygram.newsfeed.domain.Image.entity.UserImage;
import com.fluffygram.newsfeed.domain.base.Entity.BaseEntity;
import com.fluffygram.newsfeed.domain.user.enums.UserStatus;
import com.fluffygram.newsfeed.global.config.PasswordEncoder;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;


@Getter
@Entity
@Table(name = "user")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "email", unique = true)
    private String email;

    @NotBlank
    @Column(name = "password")
    private String password;

    @NotBlank
    @Column(name = "user_nickname")
    private String userNickname;

    @NotBlank
    @Column(name = "phone_number")
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_status")
    private UserStatus userStatus;

    @OneToOne
    @JoinColumn(name = "profile_image")
    private UserImage profileImage;


    public User() {

    }

    public User(String email, String encodedPassword, String userNickname, String phoneNumber, UserStatus userStatus) {
        this.email = email;
        this.password = encodedPassword;
        this.userNickname = userNickname;
        this.phoneNumber = phoneNumber;
        this.userStatus = userStatus;
    }

    public User updateUser(String password, String userNickname, String phoneNumber, UserImage profileImage) {
        // 비밀번호 업데이트
        if(userNickname != null && !password.isEmpty()){
            PasswordEncoder passwordEncoder = new PasswordEncoder();
            this.password = passwordEncoder.encode(password);
        }

        // 유저 닉네임 업데이트
        if(userNickname != null && !userNickname.isEmpty()){
            this.userNickname = userNickname;
        }

        // 유저 전화번호 업데이트
        if(phoneNumber != null && !phoneNumber.isEmpty()){
            this.phoneNumber = phoneNumber;
        }

        return this;
    }


    public void updateProfileImage(UserImage profileImage) {
        this.profileImage = profileImage;
    }

    public void updateUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }

    public void updateUserNickname() {
        this.userNickname = "탈퇴한 사용자";
    }
}



