package com.fluffygram.newsfeed.domain.userImage.service;

import com.fluffygram.newsfeed.domain.user.entity.User;
import com.fluffygram.newsfeed.domain.userImage.entity.UserImage;
import com.fluffygram.newsfeed.global.tool.UploadUserImage;
import com.fluffygram.newsfeed.domain.userImage.repository.UserImageRepository;
import com.fluffygram.newsfeed.global.config.Const;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UserImageService {

    private final UserImageRepository userImageRepository;

    @Transactional
    public String saveUserImage(MultipartFile profileImage, User user) {

        String DBfileName = UploadUserImage.uploadUserImage(profileImage);

        UserImage userImage = new UserImage(user, profileImage.getOriginalFilename(), DBfileName, Const.IMAGE_STORAGE);

        userImageRepository.save(userImage);

        return DBfileName;
    }

    public Resource getUserImage(String imageUrl) {

        return userImageRepository.getImage(imageUrl);
    }


    public String updateUserImage(MultipartFile profileImage, User user) {
        String DBfileName = UploadUserImage.uploadUserImage(profileImage);

        UserImage userImage = userImageRepository.getUserImageByUser(user);

        userImage.updateFileName(profileImage.getOriginalFilename());
        userImage.updateDBFileName(DBfileName);
        userImage.updateFileUrl(Const.IMAGE_STORAGE);

        return DBfileName;
    }
}
